package com.inepex.example.ineForm.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.inepex.example.ineForm.entity.Contact_ContactRole;
import com.inepex.example.ineForm.entity.dao.query.Contact_ContactRoleQuery;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleKVO;
import com.inepex.example.ineForm.entity.mapper.Contact_ContactRoleMapper;
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
public class Contact_ContactRoleDao extends KVManipulatorDaoBase {

	public static class Contact_ContactRoleSelector<T> extends CriteriaSelector<T, Contact_ContactRole> {
		public Contact_ContactRoleSelector(Provider<EntityManager> em, Class<T> resultClass) {
			super(em, resultClass, Contact_ContactRole.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = Contact_ContactRoleQuery.buildWhere(action, cb, root, expr);
				if (expr != null) {
					Predicate exisitngRestriction = cq.getRestriction();
					if (exisitngRestriction != null)
						expr = cb.and(expr, exisitngRestriction);
									
					cq.where(expr);
				}
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(Contact_ContactRoleQuery.getOrderExpression(action, cb, root));
		}
	}

	public static interface Contact_ContactRoleSelectorCustomizer extends SelectorCustomizer<Contact_ContactRoleSelector<?>> {
	}
	
	public Contact_ContactRoleMapper mapper = new Contact_ContactRoleMapper();
	
	protected Contact_ContactRoleSelector<Contact_ContactRole> sel = null;

	protected Contact_ContactRoleSelector<Long> cSel = null;

	@Inject
	Provider<EntityManager> em;
	

	@Inject
	Contact_ContactRoleDao(){}

	public Contact_ContactRoleDao(Provider<EntityManager> em){
		this.em=em;
	}
	
	/*hc:customFields*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new Contact_ContactRoleSelector<Contact_ContactRole>(em, Contact_ContactRole.class);
	}
	
	protected void createDefaultCountSelector() {
		cSel = new Contact_ContactRoleSelector<Long>(em, Long.class);
	}
	
	public void persist(Contact_ContactRole entity){
		/*hc:beforepersist*/
		/*hc*/
		em.get().persist(entity);
		/*hc:afterpersist*/
		/*hc*/
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	public void merge(Contact_ContactRole entity) {
		/*hc:beforemerge*/
		/*hc*/
		em.get().merge(entity);
		/*hc:aftermerge*/
		/*hc*/	
	}
		
	public void remove(Long id) {
		/*hc:beforeremove*/
		/*hc*/
		em.get().remove(em.get().find(Contact_ContactRole.class, id));
		/*hc:afterremove*/
		/*hc*/
	}
	
	public List<Contact_ContactRole> find(AbstractSearchAction action) {
		return find(action, null, true, true);
	}
	
	public List<Contact_ContactRole> find(
				AbstractSearchAction action
				, Contact_ContactRoleSelectorCustomizer customizer
				, boolean useDefaultQuery
				, boolean useDefaultOrder) {
		Contact_ContactRoleSelector<Contact_ContactRole> selector 
			= new Contact_ContactRoleSelector<Contact_ContactRole>(em, Contact_ContactRole.class);
			
		if (customizer != null)
			customizer.customizeSelect(selector);
			
		if(useDefaultQuery)
			selector.buildDefaultQuery(action);

		selector.setDistinctIfNotForcedFalse();
		
		if (useDefaultOrder)
			selector.orderBy(action);
		
		List<Contact_ContactRole> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		return count(action, null, true);
	}
	
	public Long count(AbstractSearchAction action, Contact_ContactRoleSelectorCustomizer customizer, boolean useDefaultQuery){
		Contact_ContactRoleSelector<Long> selector 
			= new Contact_ContactRoleSelector<Long>(em, Long.class);
		
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
	
	public Contact_ContactRole findById(Long id){
		Contact_ContactRole o = em.get().find(Contact_ContactRole.class, id);
		return o;
	}
	
	@Transactional
	public ObjectManipulationActionResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationActionResult result = new ObjectManipulationActionResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			Contact_ContactRole newState = doCreateOrEdit((KeyValueObject)action.getObject());
			Contact_ContactRoleKVO kvo = mapper.entityToKvo(newState);
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
	
	protected Contact_ContactRole doCreateOrEdit(KeyValueObject kvo) {
		Contact_ContactRole entity = null;
		
		if(kvo.isNew())
			entity = new Contact_ContactRole();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new Contact_ContactRoleKVO((KeyValueObject)kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
			
		return entity;
	}
	
	
	public Contact_ContactRoleKVO findKvoById(Long id) {
		Contact_ContactRole entity = findById(id);
		return mapper.entityToKvo(entity);
	}
	
	public Contact_ContactRoleKVO mergeWithDbState(KeyValueObject difference){
		if (difference.isNew())
			return new Contact_ContactRoleKVO(difference);
		Contact_ContactRoleKVO dbState = findKvoById(difference.getId());
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
					, Contact_ContactRoleSelectorCustomizer customizer){
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
