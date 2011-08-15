package com.inepex.example.ineForm.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.inepex.example.ineForm.entity.Contact_ContactState;
import com.inepex.example.ineForm.entity.dao.query.Contact_ContactStateQuery;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateKVO;
import com.inepex.example.ineForm.entity.mapper.Contact_ContactStateMapper;
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
public class Contact_ContactStateDao extends KVManipulatorDaoBase {

	public static class Contact_ContactStateSelector<T> extends CriteriaSelector<T, Contact_ContactState> {
		public Contact_ContactStateSelector(Provider<EntityManager> em, Class<T> resultClass) {
			super(em, resultClass, Contact_ContactState.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = Contact_ContactStateQuery.buildWhere(action, cb, root, expr);
				if (expr != null) {
					Predicate exisitngRestriction = cq.getRestriction();
					if (exisitngRestriction != null)
						expr = cb.and(expr, exisitngRestriction);
									
					cq.where(expr);
				}
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(Contact_ContactStateQuery.getOrderExpression(action, cb, root));
		}
	}

	public static interface Contact_ContactStateSelectorCustomizer extends SelectorCustomizer<Contact_ContactStateSelector<?>> {
	}
	
	public Contact_ContactStateMapper mapper = new Contact_ContactStateMapper();
	
	protected Contact_ContactStateSelector<Contact_ContactState> sel = null;

	protected Contact_ContactStateSelector<Long> cSel = null;

	@Inject
	Provider<EntityManager> em;
	

	@Inject
	Contact_ContactStateDao(){}

	public Contact_ContactStateDao(Provider<EntityManager> em){
		this.em=em;
	}
	
	/*hc:customFields*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new Contact_ContactStateSelector<Contact_ContactState>(em, Contact_ContactState.class);
	}
	
	protected void createDefaultCountSelector() {
		cSel = new Contact_ContactStateSelector<Long>(em, Long.class);
	}
	
	public void persist(Contact_ContactState entity){
		/*hc:beforepersist*/
		/*hc*/
		em.get().persist(entity);
		/*hc:afterpersist*/
		/*hc*/
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	public void merge(Contact_ContactState entity) {
		/*hc:beforemerge*/
		/*hc*/
		em.get().merge(entity);
		/*hc:aftermerge*/
		/*hc*/	
	}
		
	public void remove(Long id) {
		/*hc:beforeremove*/
		/*hc*/
		em.get().remove(em.get().find(Contact_ContactState.class, id));
		/*hc:afterremove*/
		/*hc*/
	}
	
	public List<Contact_ContactState> find(AbstractSearchAction action) {
		return find(action, null, true, true);
	}
	
	public List<Contact_ContactState> find(
				AbstractSearchAction action
				, Contact_ContactStateSelectorCustomizer customizer
				, boolean useDefaultQuery
				, boolean useDefaultOrder) {
		Contact_ContactStateSelector<Contact_ContactState> selector 
			= new Contact_ContactStateSelector<Contact_ContactState>(em, Contact_ContactState.class);
			
		if (customizer != null)
			customizer.customizeSelect(selector);
			
		if(useDefaultQuery)
			selector.buildDefaultQuery(action);

		selector.setDistinctIfNotForcedFalse();
		
		if (useDefaultOrder)
			selector.orderBy(action);
		
		List<Contact_ContactState> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		return count(action, null, true);
	}
	
	public Long count(AbstractSearchAction action, Contact_ContactStateSelectorCustomizer customizer, boolean useDefaultQuery){
		Contact_ContactStateSelector<Long> selector 
			= new Contact_ContactStateSelector<Long>(em, Long.class);
		
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
	
	public Contact_ContactState findById(Long id){
		Contact_ContactState o = em.get().find(Contact_ContactState.class, id);
		return o;
	}
	
	@Transactional
	public ObjectManipulationActionResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationActionResult result = new ObjectManipulationActionResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			Contact_ContactState newState = doCreateOrEdit((KeyValueObject)action.getObject());
			Contact_ContactStateKVO kvo = mapper.entityToKvo(newState);
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
	
	protected Contact_ContactState doCreateOrEdit(KeyValueObject kvo) {
		Contact_ContactState entity = null;
		
		if(kvo.isNew())
			entity = new Contact_ContactState();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new Contact_ContactStateKVO((KeyValueObject)kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
			
		return entity;
	}
	
	
	public Contact_ContactStateKVO findKvoById(Long id) {
		Contact_ContactState entity = findById(id);
		return mapper.entityToKvo(entity);
	}
	
	public Contact_ContactStateKVO mergeWithDbState(KeyValueObject difference){
		if (difference.isNew())
			return new Contact_ContactStateKVO(difference);
		Contact_ContactStateKVO dbState = findKvoById(difference.getId());
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
					, Contact_ContactStateSelectorCustomizer customizer){
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
