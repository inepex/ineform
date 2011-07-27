package com.inepex.example.ContactManager.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.inepex.example.ContactManager.entity.PhoneNumber;
import com.inepex.example.ContactManager.entity.dao.query.PhoneNumberQuery;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberKVO;
import com.inepex.example.ContactManager.entity.mapper.PhoneNumberMapper;
import com.inepex.ineForm.server.KVManipulatorDaoBase;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineForm.shared.dispatch.RelationListResult;
import com.inepex.ineFrame.server.CriteriaSelector;
import com.inepex.ineFrame.server.SelectorCustomizer;
import com.inepex.ineom.shared.dispatch.AbstractSearchAction;
import com.inepex.ineom.shared.dispatch.ObjectListResult;
import com.inepex.ineom.shared.kvo.KeyValueObject;

@Singleton
public class PhoneNumberDao extends KVManipulatorDaoBase {

	public static class PhoneNumberSelector<T> extends CriteriaSelector<T, PhoneNumber> {
		public PhoneNumberSelector(Provider<EntityManager> em, Class<T> resultClass) {
			super(em, resultClass, PhoneNumber.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = PhoneNumberQuery.buildWhere(action, cb, root, expr);
				if (expr != null) {
					Predicate exisitngRestriction = cq.getRestriction();
					if (exisitngRestriction != null)
						expr = cb.and(expr, exisitngRestriction);
									
					cq.where(expr);
				}
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(PhoneNumberQuery.getOrderExpression(action, cb, root));
		}
	}

	public static interface PhoneNumberSelectorCustomizer extends SelectorCustomizer<PhoneNumberSelector<?>> {
	}
	
	public PhoneNumberMapper mapper = new PhoneNumberMapper();
	
	protected PhoneNumberSelector<PhoneNumber> sel = null;

	protected PhoneNumberSelector<Long> cSel = null;

	
	protected final Provider<EntityManager> em;
	

	@Inject
	public PhoneNumberDao(Provider<EntityManager> em){
		this.em=em;
			}
	
	/*hc:customFields*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new PhoneNumberSelector<PhoneNumber>(em, PhoneNumber.class);
	}
	
	protected void createDefaultCountSelector() {
		cSel = new PhoneNumberSelector<Long>(em, Long.class);
	}
	
	@Transactional
	public void persistTrans(PhoneNumber entity){
		persist(entity);
	}
	
	protected void persist(PhoneNumber entity){
		/*hc:beforepersist*/
		/*hc*/
		em.get().persist(entity);
		/*hc:afterpersist*/
		/*hc*/
	}
	
	@Transactional
	public void mergeTrans(PhoneNumber entity){
		merge(entity);
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	protected void merge(PhoneNumber entity) {
		/*hc:beforemerge*/
		/*hc*/
		em.get().merge(entity);
		/*hc:aftermerge*/
		/*hc*/	
	}
		
	@Transactional
	public void removeTrans(Long id) {
		remove(id);
	}
	
	protected void remove(Long id) {
		/*hc:beforeremove*/
		/*hc*/
		em.get().remove(em.get().find(PhoneNumber.class, id));
		/*hc:afterremove*/
		/*hc*/
	}
	
	public List<PhoneNumber> find(AbstractSearchAction action) {
		return find(action, null, true, true);
	}
	
	public List<PhoneNumber> find(
				AbstractSearchAction action
				, PhoneNumberSelectorCustomizer customizer
				, boolean useDefaultQuery
				, boolean useDefaultOrder) {
		PhoneNumberSelector<PhoneNumber> selector 
			= new PhoneNumberSelector<PhoneNumber>(em, PhoneNumber.class);
			
		if (customizer != null)
			customizer.customizeSelect(selector);
			
		if(useDefaultQuery)
			selector.buildDefaultQuery(action);

		selector.setDistinctIfNotForcedFalse();
		
		if (useDefaultOrder)
			selector.orderBy(action);
		
		List<PhoneNumber> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		return count(action, null, true);
	}
	
	public Long count(AbstractSearchAction action, PhoneNumberSelectorCustomizer customizer, boolean useDefaultQuery){
		PhoneNumberSelector<Long> selector 
			= new PhoneNumberSelector<Long>(em, Long.class);
		
		if (customizer != null)
			customizer.customizeSelect(selector);
			
		if(useDefaultQuery)
			selector.buildDefaultQuery(action);
		else 
			selector.cq.distinct(true);
			
		selector.cq.select(selector.getCountExpression());
		
		Long res = selector.getTypedQuery().getSingleResult();
		return res;
	}
	
	public PhoneNumber findById(Long id){
		PhoneNumber o = em.get().find(PhoneNumber.class, id);
		return o;
	}
	
	@Transactional
	public ObjectManipulationResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationResult result = new ObjectManipulationResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			PhoneNumber newState = doCreateOrEdit((KeyValueObject)action.getObject());
			PhoneNumberKVO kvo = mapper.entityToKvo(newState);
			result.setObjectsNewState(kvo);
			break;
		case DELETE:
			remove(action.getObject().getId());
			break;
		case REFRESH:
			result.setObjectsNewState(findKvoById(action.getObject().getId()));
			break;
		default:
			throw new Exception("Invalid manipulation type");
		}
		
		return result;
	}
	
	protected PhoneNumber doCreateOrEdit(KeyValueObject kvo) {
		PhoneNumber entity = null;
		
		if(kvo.isNew())
			entity = new PhoneNumber();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new PhoneNumberKVO((KeyValueObject)kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
			
		return entity;
	}
	
	
	public PhoneNumberKVO findKvoById(Long id) {
		PhoneNumber entity = findById(id);
		return mapper.entityToKvo(entity);
	}
	
	public PhoneNumberKVO mergeWithDbState(KeyValueObject difference){
		if (difference.isNew())
			return new PhoneNumberKVO(difference);
		PhoneNumberKVO dbState = findKvoById(difference.getId());
		difference.copyValuesTo(dbState);
		return dbState;
	}
	
	public ObjectListResult search(AbstractSearchAction action){
		return search(action, true, true, null);
	}
	
	public ObjectListResult search(
					AbstractSearchAction action
					, boolean useDefaultQuery
					, boolean useDefaultOrder
					, PhoneNumberSelectorCustomizer customizer){
		ObjectListResult res = new ObjectListResult();
		if (action.isQueryResultCount()){
			res.setAllResultCount(count(action, customizer, useDefaultQuery));
		}
		if(action.getNumMaxResult() > 0)
			res.setList(mapper.entityListToKvoList(find(action, customizer, useDefaultQuery, useDefaultOrder)));		
		return res;
	}

	public RelationListResult searchAsRelation(AbstractSearchAction action){
		RelationListResult res = new RelationListResult();
		if (action.isQueryResultCount()){
			res.setAllResultCount(count(action));
		}
		res.setList(mapper.toRelationList(find(action)));		
		return res;
	}
}
