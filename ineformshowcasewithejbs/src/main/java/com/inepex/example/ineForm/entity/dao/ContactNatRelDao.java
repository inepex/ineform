package com.inepex.example.ineForm.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.inepex.example.ineForm.entity.ContactNatRel;
import com.inepex.example.ineForm.entity.dao.query.ContactNatRelQuery;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelKVO;
import com.inepex.example.ineForm.entity.mapper.ContactNatRelMapper;
import com.inepex.ineForm.server.KVManipulatorDaoBase;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineForm.shared.dispatch.ObjectListActionResult;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationActionResult;
import com.inepex.ineForm.shared.dispatch.RelationListActionResult;
import com.inepex.ineFrame.server.CriteriaSelector;
import com.inepex.ineFrame.server.SelectorCustomizer;
import com.inepex.ineom.shared.kvo.KeyValueObject;

@Singleton
public class ContactNatRelDao extends KVManipulatorDaoBase {

	public static class ContactNatRelSelector<T> extends CriteriaSelector<T, ContactNatRel> {
		public ContactNatRelSelector(Provider<EntityManager> em, Class<T> resultClass) {
			super(em, resultClass, ContactNatRel.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = ContactNatRelQuery.buildWhere(action, cb, root, expr);
				if (expr != null) {
					Predicate exisitngRestriction = cq.getRestriction();
					if (exisitngRestriction != null)
						expr = cb.and(expr, exisitngRestriction);
									
					cq.where(expr);
				}
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(ContactNatRelQuery.getOrderExpression(action, cb, root));
		}
	}

	public static interface ContactNatRelSelectorCustomizer extends SelectorCustomizer<ContactNatRelSelector<?>> {
	}
	
	public ContactNatRelMapper mapper = new ContactNatRelMapper();
	
	protected ContactNatRelSelector<ContactNatRel> sel = null;

	protected ContactNatRelSelector<Long> cSel = null;

	@Inject
	Provider<EntityManager> em;
	

	@Inject
	ContactNatRelDao(){}

	public ContactNatRelDao(Provider<EntityManager> em){
		this.em=em;
	}
	
	/*hc:customFields*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new ContactNatRelSelector<ContactNatRel>(em, ContactNatRel.class);
	}
	
	protected void createDefaultCountSelector() {
		cSel = new ContactNatRelSelector<Long>(em, Long.class);
	}
	
	public void persist(ContactNatRel entity){
		/*hc:beforepersist*//*hc*/
		em.get().persist(entity);
		/*hc:afterpersist*//*hc*/
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	public void merge(ContactNatRel entity) {
		/*hc:beforemerge*/
		/*hc*/
		em.get().merge(entity);
		/*hc:aftermerge*/
		/*hc*/	
	}
		
	public void remove(Long id) {
		/*hc:beforeremove*/
		/*hc*/
		em.get().remove(em.get().find(ContactNatRel.class, id));
		/*hc:afterremove*/
		/*hc*/
	}
	
	public List<ContactNatRel> find(AbstractSearchAction action) {
		return find(action, null, true, true);
	}
	
	public List<ContactNatRel> find(
				AbstractSearchAction action
				, ContactNatRelSelectorCustomizer customizer
				, boolean useDefaultQuery
				, boolean useDefaultOrder) {
		ContactNatRelSelector<ContactNatRel> selector 
			= new ContactNatRelSelector<ContactNatRel>(em, ContactNatRel.class);
			
		if (customizer != null)
			customizer.customizeSelect(selector);
			
		if(useDefaultQuery)
			selector.buildDefaultQuery(action);

		selector.setDistinctIfNotForcedFalse();
		
		if (useDefaultOrder)
			selector.orderBy(action);
		
		List<ContactNatRel> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		return count(action, null, true);
	}
	
	public Long count(AbstractSearchAction action, ContactNatRelSelectorCustomizer customizer, boolean useDefaultQuery){
		ContactNatRelSelector<Long> selector 
			= new ContactNatRelSelector<Long>(em, Long.class);
		
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
	
	public ContactNatRel findById(Long id){
		ContactNatRel o = em.get().find(ContactNatRel.class, id);
		return o;
	}
	
	@Transactional
	public ObjectManipulationActionResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationActionResult result = new ObjectManipulationActionResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			ContactNatRel newState = doCreateOrEdit((KeyValueObject)action.getObject());
			ContactNatRelKVO kvo = mapper.entityToKvo(newState);
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
	
	protected ContactNatRel doCreateOrEdit(KeyValueObject kvo) {
		ContactNatRel entity = null;
		
		if(kvo.isNew())
			entity = new ContactNatRel();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new ContactNatRelKVO((KeyValueObject)kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
			
		return entity;
	}
	
	
	public ContactNatRelKVO findKvoById(Long id) {
		ContactNatRel entity = findById(id);
		return mapper.entityToKvo(entity);
	}
	
	public ContactNatRelKVO mergeWithDbState(KeyValueObject difference){
		if (difference.isNew())
			return new ContactNatRelKVO(difference);
		ContactNatRelKVO dbState = findKvoById(difference.getId());
		difference.copyValuesTo(dbState);
		return dbState;
	}
	
	public ObjectListActionResult search(AbstractSearchAction action){
		return search(action, true, true, null);
	}
	
	public ObjectListActionResult search(
					AbstractSearchAction action
					, boolean useDefaultQuery
					, boolean useDefaultOrder
					, ContactNatRelSelectorCustomizer customizer){
		ObjectListActionResult res = new ObjectListActionResult();
		if (action.isQueryResultCount()){
			res.setAllResultCount(count(action, customizer, useDefaultQuery));
		}
		if(action.getNumMaxResult() > 0)
			res.setList(mapper.entityListToKvoList(find(action, customizer, useDefaultQuery, useDefaultOrder)));		
		return res;
	}

	public RelationListActionResult searchAsRelation(AbstractSearchAction action){
		RelationListActionResult res = new RelationListActionResult();
		if (action.isQueryResultCount()){
			res.setAllResultCount(count(action));
		}
		res.setList(mapper.toRelationList(find(action)));		
		return res;
	}
}
