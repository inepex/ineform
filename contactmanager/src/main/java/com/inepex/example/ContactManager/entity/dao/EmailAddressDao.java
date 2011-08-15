package com.inepex.example.ContactManager.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.inepex.example.ContactManager.entity.EmailAddress;
import com.inepex.example.ContactManager.entity.dao.query.EmailAddressQuery;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressKVO;
import com.inepex.example.ContactManager.entity.mapper.EmailAddressMapper;
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
public class EmailAddressDao extends KVManipulatorDaoBase {

	public static class EmailAddressSelector<T> extends CriteriaSelector<T, EmailAddress> {
		public EmailAddressSelector(Provider<EntityManager> em, Class<T> resultClass) {
			super(em, resultClass, EmailAddress.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = EmailAddressQuery.buildWhere(action, cb, root, expr);
				if (expr != null) {
					Predicate exisitngRestriction = cq.getRestriction();
					if (exisitngRestriction != null)
						expr = cb.and(expr, exisitngRestriction);
									
					cq.where(expr);
				}
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(EmailAddressQuery.getOrderExpression(action, cb, root));
		}
	}

	public static interface EmailAddressSelectorCustomizer extends SelectorCustomizer<EmailAddressSelector<?>> {
	}
	
	public EmailAddressMapper mapper = new EmailAddressMapper();
	
	protected EmailAddressSelector<EmailAddress> sel = null;

	protected EmailAddressSelector<Long> cSel = null;

	
	protected final Provider<EntityManager> em;
	

	@Inject
	public EmailAddressDao(Provider<EntityManager> em){
		this.em=em;
			}
	
	/*hc:customFields*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new EmailAddressSelector<EmailAddress>(em, EmailAddress.class);
	}
	
	protected void createDefaultCountSelector() {
		cSel = new EmailAddressSelector<Long>(em, Long.class);
	}
	
	@Transactional
	public void persistTrans(EmailAddress entity){
		persist(entity);
	}
	
	protected void persist(EmailAddress entity){
		/*hc:beforepersist*/
		/*hc*/
		em.get().persist(entity);
		/*hc:afterpersist*/
		/*hc*/
	}
	
	@Transactional
	public void mergeTrans(EmailAddress entity){
		merge(entity);
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	protected void merge(EmailAddress entity) {
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
		em.get().remove(em.get().find(EmailAddress.class, id));
		/*hc:afterremove*/
		/*hc*/
	}
	
	public List<EmailAddress> find(AbstractSearchAction action) {
		return find(action, null, true, true);
	}
	
	public List<EmailAddress> find(
				AbstractSearchAction action
				, EmailAddressSelectorCustomizer customizer
				, boolean useDefaultQuery
				, boolean useDefaultOrder) {
		EmailAddressSelector<EmailAddress> selector 
			= new EmailAddressSelector<EmailAddress>(em, EmailAddress.class);
			
		if (customizer != null)
			customizer.customizeSelect(selector);
			
		if(useDefaultQuery)
			selector.buildDefaultQuery(action);

		selector.setDistinctIfNotForcedFalse();
		
		if (useDefaultOrder)
			selector.orderBy(action);
		
		List<EmailAddress> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		return count(action, null, true);
	}
	
	public Long count(AbstractSearchAction action, EmailAddressSelectorCustomizer customizer, boolean useDefaultQuery){
		EmailAddressSelector<Long> selector 
			= new EmailAddressSelector<Long>(em, Long.class);
		
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
	
	public EmailAddress findById(Long id){
		EmailAddress o = em.get().find(EmailAddress.class, id);
		return o;
	}
	
	@Transactional
	public ObjectManipulationActionResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationActionResult result = new ObjectManipulationActionResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			EmailAddress newState = doCreateOrEdit((KeyValueObject)action.getObject());
			EmailAddressKVO kvo = mapper.entityToKvo(newState);
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
	
	protected EmailAddress doCreateOrEdit(KeyValueObject kvo) {
		EmailAddress entity = null;
		
		if(kvo.isNew())
			entity = new EmailAddress();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new EmailAddressKVO((KeyValueObject)kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
			
		return entity;
	}
	
	
	public EmailAddressKVO findKvoById(Long id) {
		EmailAddress entity = findById(id);
		return mapper.entityToKvo(entity);
	}
	
	public EmailAddressKVO mergeWithDbState(KeyValueObject difference){
		if (difference.isNew())
			return new EmailAddressKVO(difference);
		EmailAddressKVO dbState = findKvoById(difference.getId());
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
					, EmailAddressSelectorCustomizer customizer){
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