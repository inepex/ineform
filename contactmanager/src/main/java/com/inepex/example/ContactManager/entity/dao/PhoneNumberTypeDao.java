package com.inepex.example.ContactManager.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.dao.query.PhoneNumberTypeQuery;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberTypeKVO;
import com.inepex.example.ContactManager.entity.mapper.PhoneNumberTypeMapper;
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
public class PhoneNumberTypeDao extends KVManipulatorDaoBase {

	public static class PhoneNumberTypeSelector<T> extends CriteriaSelector<T, PhoneNumberType> {
		public PhoneNumberTypeSelector(Provider<EntityManager> em, Class<T> resultClass) {
			super(em, resultClass, PhoneNumberType.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = PhoneNumberTypeQuery.buildWhere(action, cb, root, expr);
				if (expr != null) {
					Predicate exisitngRestriction = cq.getRestriction();
					if (exisitngRestriction != null)
						expr = cb.and(expr, exisitngRestriction);
									
					cq.where(expr);
				}
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(PhoneNumberTypeQuery.getOrderExpression(action, cb, root));
		}
	}

	public static interface PhoneNumberTypeSelectorCustomizer extends SelectorCustomizer<PhoneNumberTypeSelector<?>> {
	}
	
	public PhoneNumberTypeMapper mapper = new PhoneNumberTypeMapper();
	
	protected PhoneNumberTypeSelector<PhoneNumberType> sel = null;

	protected PhoneNumberTypeSelector<Long> cSel = null;

	
	protected final Provider<EntityManager> em;
	

	@Inject
	public PhoneNumberTypeDao(Provider<EntityManager> em){
		this.em=em;
			}
	
	/*hc:customFields*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new PhoneNumberTypeSelector<PhoneNumberType>(em, PhoneNumberType.class);
	}
	
	protected void createDefaultCountSelector() {
		cSel = new PhoneNumberTypeSelector<Long>(em, Long.class);
	}
	
	@Transactional
	public void persistTrans(PhoneNumberType entity){
		persist(entity);
	}
	
	protected void persist(PhoneNumberType entity){
		/*hc:beforepersist*/
		/*hc*/
		em.get().persist(entity);
		/*hc:afterpersist*/
		/*hc*/
	}
	
	@Transactional
	public void mergeTrans(PhoneNumberType entity){
		merge(entity);
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	protected void merge(PhoneNumberType entity) {
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
		em.get().remove(em.get().find(PhoneNumberType.class, id));
		/*hc:afterremove*/
		/*hc*/
	}
	
	public List<PhoneNumberType> find(AbstractSearchAction action) {
		return find(action, null, true, true);
	}
	
	public List<PhoneNumberType> find(
				AbstractSearchAction action
				, PhoneNumberTypeSelectorCustomizer customizer
				, boolean useDefaultQuery
				, boolean useDefaultOrder) {
		PhoneNumberTypeSelector<PhoneNumberType> selector 
			= new PhoneNumberTypeSelector<PhoneNumberType>(em, PhoneNumberType.class);
			
		if (customizer != null)
			customizer.customizeSelect(selector);
			
		if(useDefaultQuery)
			selector.buildDefaultQuery(action);

		selector.setDistinctIfNotForcedFalse();
		
		if (useDefaultOrder)
			selector.orderBy(action);
		
		List<PhoneNumberType> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		return count(action, null, true);
	}
	
	public Long count(AbstractSearchAction action, PhoneNumberTypeSelectorCustomizer customizer, boolean useDefaultQuery){
		PhoneNumberTypeSelector<Long> selector 
			= new PhoneNumberTypeSelector<Long>(em, Long.class);
		
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
	
	public PhoneNumberType findById(Long id){
		PhoneNumberType o = em.get().find(PhoneNumberType.class, id);
		return o;
	}
	
	@Transactional
	public ObjectManipulationActionResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationActionResult result = new ObjectManipulationActionResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			PhoneNumberType newState = doCreateOrEdit((KeyValueObject)action.getObject());
			PhoneNumberTypeKVO kvo = mapper.entityToKvo(newState);
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
	
	protected PhoneNumberType doCreateOrEdit(KeyValueObject kvo) {
		PhoneNumberType entity = null;
		
		if(kvo.isNew())
			entity = new PhoneNumberType();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new PhoneNumberTypeKVO((KeyValueObject)kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
			
		return entity;
	}
	
	
	public PhoneNumberTypeKVO findKvoById(Long id) {
		PhoneNumberType entity = findById(id);
		return mapper.entityToKvo(entity);
	}
	
	public PhoneNumberTypeKVO mergeWithDbState(KeyValueObject difference){
		if (difference.isNew())
			return new PhoneNumberTypeKVO(difference);
		PhoneNumberTypeKVO dbState = findKvoById(difference.getId());
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
					, PhoneNumberTypeSelectorCustomizer customizer){
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