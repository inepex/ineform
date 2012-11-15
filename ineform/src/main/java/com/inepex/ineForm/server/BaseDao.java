package com.inepex.ineForm.server;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.eclipse.persistence.exceptions.DatabaseException;

import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineForm.shared.ObjectManipulationException;
import com.inepex.ineForm.shared.ObjectManipulationException.Reason;
import com.inepex.ineForm.shared.dispatch.ManipulationObjectFactory;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;
import com.inepex.ineom.shared.dispatch.interfaces.RelationListResult;

public abstract class BaseDao<E> implements KVManipulatorDaoBase {
	
	public abstract class SelectorCustomizer {
		public abstract void customizeSelect(CriteriaSelector<?,E> sel);
	}
	
	protected final AssistedObjectHandlerFactory handlerFactory;
	protected final Provider<EntityManager> em;
	
	protected ManipulationObjectFactory objectFactory;

	public BaseDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory, AssistedObjectHandlerFactory handlerFactory) {
		this.em = em;
		this.objectFactory = objectFactory;
		this.handlerFactory=handlerFactory;
	}

	public abstract BaseQuery<E> getQuery();

	public abstract BaseMapper<E> getMapper();

	public abstract CriteriaSelector<E, E> getSelector();

	public abstract CriteriaSelector<Long, E> getCountSelector();

	public abstract Class<E> getClazz();

	public abstract E newInstance();

	@Transactional
	public void persistTrans(E entity) {
		persist(entity);
	}

	/**
	 * Override to implement extra behaviour before or after persist
	 */
	public void persist(E entity) {
		em.get().persist(entity);
	}

	@Transactional
	public void mergeTrans(E entity) {
		merge(entity);
	}

	/**
	 * Should be called after modifying! Override to implement extra behaviour
	 * before or after merge
	 * 
	 * @param entity
	 */
	public void merge(E entity) {
		em.get().merge(entity);
	}

	@Transactional
	public void removeTrans(Long id) {
		remove(id);
	}

	/**
	 * Override to implement extra behaviour before or after remove
	 */
	public void remove(Long id) {
		em.get().remove(em.get().find(getClazz(), id));
	}

	public List<E> find(AbstractSearch action) {
		return find(action, null, true, true);
	}

	public List<E> find(
			AbstractSearch action,
			SelectorCustomizer customizer,
			boolean useDefaultQuery,
			boolean useDefaultOrder) {
		CriteriaSelector<E, E> selector = getSelector();

		if (customizer != null)
			customizer.customizeSelect(selector);

		if (useDefaultQuery)
			selector.buildDefaultQuery(action);

		selector.setDistinctIfNotForcedFalse();

		if (useDefaultOrder)
			selector.orderBy(action);

		List<E> res = selector.executeRangeSelect(action.getFirstResult(), action.getNumMaxResult());
		return res;
	}

	public Long count(AbstractSearch action) {
		return count(action, null, true);
	}

	public Long count(AbstractSearch action,
			SelectorCustomizer customizer, boolean useDefaultQuery) {
		CriteriaSelector<Long, E> selector = getCountSelector();

		if (customizer != null)
			customizer.customizeSelect(selector);

		if (useDefaultQuery)
			selector.buildDefaultQuery(action);
		else
			selector.cq.distinct(true);

		selector.cq.select(selector.getCountExpression());

		Long res = selector.getTypedQuery().getSingleResult();
		return res;
	}

	public E findById(Long id) {
		E o = em.get().find(getClazz(), id);
		return o;
	}

	/**
	 * 
	 * Throws {@link ObjectManipulationException} when create or edit request failed by constrain violation!
	 * 
	 */
	@Override
	@Transactional
	public ObjectManipulationResult manipulate(ObjectManipulation action) throws Exception {
		ObjectManipulationResult result = objectFactory.getNewObjectManipulationResult();
		switch (action.getManipulationType()) {
		case CREATE_OR_EDIT_REQUEST:
			try {
				E newState = doCreateOrEdit(action.getObject(), action.getCustomObjectDescritors());
				AssistedObject kvo = getMapper().entityToKvo(newState);
				result.setObjectsNewState(kvo);
				break;
			} catch (PersistenceException e) {
				if(e.getCause()!=null 
					&& e.getCause() instanceof DatabaseException 
					&& e.getCause().getCause()!=null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
						//device can not be manipulated because devicetype and nativeid is not unique
						throw new ObjectManipulationException(Reason.ConstraintViolationFailed); 
				} else
					throw e;
			}
		case DELETE:
			remove(action.getObject().getId());
			break;
		case REFRESH:
			result.setObjectsNewState(findKvoById(getIdFromAction(action)));
			break;
		default:
			throw new Exception("Invalid manipulation type");
		}

		return result;
	}

	protected Long getIdFromAction(ObjectManipulation action) {
		if(action.getIdToRefresh()!=null)
			return action.getIdToRefresh();
		
		return action.getObject().getId();
	}

	public E doCreateOrEdit(AssistedObject kvo, CustomKVOObjectDesc... custOds) throws Exception {
		E entity = null;

		if (kvo.isNew())
			entity = newInstance();
		else
			entity = findById(kvo.getId());

		getMapper().kvoToEntity(kvo, entity, custOds);
		if (kvo.isNew())
			persist(entity);
		else
			merge(entity);

		em.get().flush();
		
		return entity;
	}

	public AssistedObject findKvoById(Long id) {
		E entity = findById(id);
		return getMapper().entityToKvo(entity);
	}

	public AssistedObject mergeWithDbState(AssistedObject difference) {
		if (difference.isNew())
			return difference;
		
		AssistedObject dbState = findKvoById(difference.getId());
		handlerFactory.createHandler(difference).copyValuesTo(dbState);
		return dbState;
	}

	@Override
	public ObjectListResult search(AbstractSearch action) {
		return search(action, true, true, null);
	}

	public ObjectListResult search(
			AbstractSearch action,
			boolean useDefaultQuery,
			boolean useDefaultOrder,
			SelectorCustomizer customizer) {
		ObjectListResult res = objectFactory.getNewObjectListResult();
		if (action.isQueryResultCount()) {
			res.setAllResultCount(count(action, customizer, useDefaultQuery));
		}
		if (action.getNumMaxResult() > 0)
			res.setList(getMapper().entityListToKvoList(find(action, customizer, useDefaultQuery, useDefaultOrder)));
		return res;
	}

	@Override
	public RelationListResult searchAsRelation(AbstractSearch action) {
		RelationListResult res = objectFactory.getNewRelationListResult();
		if (action.isQueryResultCount()) {
			res.setAllResultCount(count(action));
		}
		res.setList(getMapper().toRelationList(find(action)));
		return res;
	}
	
	@Override
	public void manipulateAsync(ObjectManipulation action, 
			IneformAsyncCallback<ObjectManipulationResult> callback) throws Exception {
		callback.onResponse(manipulate(action));
	}
	
	@Override
	public void searchAsync(AbstractSearch action, IneformAsyncCallback<ObjectListResult> callback){
		callback.onResponse(search(action));
	}
	
	@Override
	public void searchAsRelationAsync(AbstractSearch action, IneformAsyncCallback<RelationListResult> callback){
		callback.onResponse(searchAsRelation(action));
	}
	

}
