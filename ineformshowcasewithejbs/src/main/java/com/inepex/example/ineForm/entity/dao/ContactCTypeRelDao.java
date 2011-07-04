package com.inepex.example.ineForm.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.inepex.example.ineForm.entity.ContactCTypeRel;
import com.inepex.example.ineForm.entity.dao.query.ContactCTypeRelQuery;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelKVO;
import com.inepex.example.ineForm.entity.mapper.ContactCTypeRelMapper;
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
public class ContactCTypeRelDao extends KVManipulatorDaoBase {

	public static class ContactCTypeRelSelector<T> extends CriteriaSelector<T, ContactCTypeRel> {
		public ContactCTypeRelSelector(Provider<EntityManager> em, Class<T> resultClass) {
			super(em, resultClass, ContactCTypeRel.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = ContactCTypeRelQuery.buildWhere(action, cb, root, expr);
				if (expr != null) {
					Predicate exisitngRestriction = cq.getRestriction();
					if (exisitngRestriction != null)
						expr = cb.and(expr, exisitngRestriction);
									
					cq.where(expr);
				}
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(ContactCTypeRelQuery.getOrderExpression(action, cb, root));
		}
	}

	public static interface ContactCTypeRelSelectorCustomizer extends SelectorCustomizer<ContactCTypeRelSelector<?>> {
	}
	
	public ContactCTypeRelMapper mapper = new ContactCTypeRelMapper();
	
	protected ContactCTypeRelSelector<ContactCTypeRel> sel = null;

	protected ContactCTypeRelSelector<Long> cSel = null;

	@Inject
	Provider<EntityManager> em;
	

	@Inject
	ContactCTypeRelDao(){}

	public ContactCTypeRelDao(Provider<EntityManager> em){
		this.em=em;
	}
	
	/*hc:customFields*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new ContactCTypeRelSelector<ContactCTypeRel>(em, ContactCTypeRel.class);
	}
	
	protected void createDefaultCountSelector() {
		cSel = new ContactCTypeRelSelector<Long>(em, Long.class);
	}
	
	public void persist(ContactCTypeRel entity){
		/*hc:beforepersist*//*hc*/
		em.get().persist(entity);
		/*hc:afterpersist*//*hc*/
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	public void merge(ContactCTypeRel entity) {
		/*hc:beforemerge*/
		/*hc*/
		em.get().merge(entity);
		/*hc:aftermerge*/
		/*hc*/	
	}
		
	public void remove(Long id) {
		/*hc:beforeremove*/
		/*hc*/
		em.get().remove(em.get().find(ContactCTypeRel.class, id));
		/*hc:afterremove*/
		/*hc*/
	}
	
	public List<ContactCTypeRel> find(AbstractSearchAction action) {
		return find(action, null, true, true);
	}
	
	public List<ContactCTypeRel> find(
				AbstractSearchAction action
				, ContactCTypeRelSelectorCustomizer customizer
				, boolean useDefaultQuery
				, boolean useDefaultOrder) {
		ContactCTypeRelSelector<ContactCTypeRel> selector 
			= new ContactCTypeRelSelector<ContactCTypeRel>(em, ContactCTypeRel.class);
			
		if (customizer != null)
			customizer.customizeSelect(selector);
			
		if(useDefaultQuery)
			selector.buildDefaultQuery(action);

		selector.setDistinctIfNotForcedFalse();
		
		if (useDefaultOrder)
			selector.orderBy(action);
		
		List<ContactCTypeRel> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		return count(action, null, true);
	}
	
	public Long count(AbstractSearchAction action, ContactCTypeRelSelectorCustomizer customizer, boolean useDefaultQuery){
		ContactCTypeRelSelector<Long> selector 
			= new ContactCTypeRelSelector<Long>(em, Long.class);
		
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
	
	public ContactCTypeRel findById(Long id){
		ContactCTypeRel o = em.get().find(ContactCTypeRel.class, id);
		return o;
	}
	
	@Transactional
	public ObjectManipulationResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationResult result = new ObjectManipulationResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			ContactCTypeRel newState = doCreateOrEdit((KeyValueObject)action.getObject());
			ContactCTypeRelKVO kvo = mapper.entityToKvo(newState);
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
	
	protected ContactCTypeRel doCreateOrEdit(KeyValueObject kvo) {
		ContactCTypeRel entity = null;
		
		if(kvo.isNew())
			entity = new ContactCTypeRel();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new ContactCTypeRelKVO((KeyValueObject)kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
			
		return entity;
	}
	
	
	public ContactCTypeRelKVO findKvoById(Long id) {
		ContactCTypeRel entity = findById(id);
		return mapper.entityToKvo(entity);
	}
	
	public ContactCTypeRelKVO mergeWithDbState(KeyValueObject difference){
		if (difference.isNew())
			return new ContactCTypeRelKVO(difference);
		ContactCTypeRelKVO dbState = findKvoById(difference.getId());
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
					, ContactCTypeRelSelectorCustomizer customizer){
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
