package com.inepex.example.ineForm.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.dao.query.ContactTypeQuery;
import com.inepex.example.ineForm.entity.kvo.ContactTypeKVO;
import com.inepex.example.ineForm.entity.mapper.ContactTypeMapper;
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
public class ContactTypeDao extends KVManipulatorDaoBase {

	public static class ContactTypeSelector<T> extends CriteriaSelector<T, ContactType> {
		public ContactTypeSelector(Provider<EntityManager> em, Class<T> resultClass) {
			super(em, resultClass, ContactType.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = ContactTypeQuery.buildWhere(action, cb, root, expr);
				if (expr != null) {
					Predicate exisitngRestriction = cq.getRestriction();
					if (exisitngRestriction != null)
						expr = cb.and(expr, exisitngRestriction);
									
					cq.where(expr);
				}
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(ContactTypeQuery.getOrderExpression(action, cb, root));
		}
	}

	public static interface ContactTypeSelectorCustomizer extends SelectorCustomizer<ContactTypeSelector<?>> {
	}
	
	public ContactTypeMapper mapper = new ContactTypeMapper();
	
	protected ContactTypeSelector<ContactType> sel = null;

	protected ContactTypeSelector<Long> cSel = null;

	@Inject
	Provider<EntityManager> em;
	

	@Inject
	ContactTypeDao(){}

	public ContactTypeDao(Provider<EntityManager> em){
		this.em=em;
	}
	
	/*hc:customFields*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new ContactTypeSelector<ContactType>(em, ContactType.class);
	}
	
	protected void createDefaultCountSelector() {
		cSel = new ContactTypeSelector<Long>(em, Long.class);
	}
	
	public void persist(ContactType entity){
		/*hc:beforepersist*//*hc*/
		em.get().persist(entity);
		/*hc:afterpersist*//*hc*/
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	public void merge(ContactType entity) {
		/*hc:beforemerge*/
		/*hc*/
		em.get().merge(entity);
		/*hc:aftermerge*/
		/*hc*/	
	}
		
	public void remove(Long id) {
		/*hc:beforeremove*/
		/*hc*/
		em.get().remove(em.get().find(ContactType.class, id));
		/*hc:afterremove*/
		/*hc*/
	}
	
	public List<ContactType> find(AbstractSearchAction action) {
		return find(action, null, true, true);
	}
	
	public List<ContactType> find(
				AbstractSearchAction action
				, ContactTypeSelectorCustomizer customizer
				, boolean useDefaultQuery
				, boolean useDefaultOrder) {
		ContactTypeSelector<ContactType> selector 
			= new ContactTypeSelector<ContactType>(em, ContactType.class);
			
		if (customizer != null)
			customizer.customizeSelect(selector);
			
		if(useDefaultQuery)
			selector.buildDefaultQuery(action);

		selector.setDistinctIfNotForcedFalse();
		
		if (useDefaultOrder)
			selector.orderBy(action);
		
		List<ContactType> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		return count(action, null, true);
	}
	
	public Long count(AbstractSearchAction action, ContactTypeSelectorCustomizer customizer, boolean useDefaultQuery){
		ContactTypeSelector<Long> selector 
			= new ContactTypeSelector<Long>(em, Long.class);
		
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
	
	public ContactType findById(Long id){
		ContactType o = em.get().find(ContactType.class, id);
		return o;
	}
	
	@Transactional
	public ObjectManipulationResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationResult result = new ObjectManipulationResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			ContactType newState = doCreateOrEdit((KeyValueObject)action.getObject());
			ContactTypeKVO kvo = mapper.entityToKvo(newState);
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
	
	protected ContactType doCreateOrEdit(KeyValueObject kvo) {
		ContactType entity = null;
		
		if(kvo.isNew())
			entity = new ContactType();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new ContactTypeKVO((KeyValueObject)kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
			
		return entity;
	}
	
	
	public ContactTypeKVO findKvoById(Long id) {
		ContactType entity = findById(id);
		return mapper.entityToKvo(entity);
	}
	
	public ContactTypeKVO mergeWithDbState(KeyValueObject difference){
		if (difference.isNew())
			return new ContactTypeKVO(difference);
		ContactTypeKVO dbState = findKvoById(difference.getId());
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
					, ContactTypeSelectorCustomizer customizer){
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
