package com.inepex.example.ContactManager.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.inepex.example.ContactManager.entity.User;
import com.inepex.example.ContactManager.entity.dao.query.UserQuery;
import com.inepex.example.ContactManager.entity.kvo.UserKVO;
import com.inepex.example.ContactManager.entity.mapper.UserMapper;
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
public class UserDao extends KVManipulatorDaoBase {

	public static class UserSelector<T> extends CriteriaSelector<T, User> {
		public UserSelector(Provider<EntityManager> em, Class<T> resultClass) {
			super(em, resultClass, User.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = UserQuery.buildWhere(action, cb, root, expr);
				if (expr != null) {
					Predicate exisitngRestriction = cq.getRestriction();
					if (exisitngRestriction != null)
						expr = cb.and(expr, exisitngRestriction);
									
					cq.where(expr);
				}
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(UserQuery.getOrderExpression(action, cb, root));
		}
	}

	public static interface UserSelectorCustomizer extends SelectorCustomizer<UserSelector<?>> {
	}
	
	public UserMapper mapper = new UserMapper();
	
	protected UserSelector<User> sel = null;

	protected UserSelector<Long> cSel = null;

	
	protected final Provider<EntityManager> em;
	

	@Inject
	public UserDao(Provider<EntityManager> em){
		this.em=em;
			}
	
	/*hc:customFields*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new UserSelector<User>(em, User.class);
	}
	
	protected void createDefaultCountSelector() {
		cSel = new UserSelector<Long>(em, Long.class);
	}
	
	@Transactional
	public void persistTrans(User entity){
		persist(entity);
	}
	
	protected void persist(User entity){
		/*hc:beforepersist*/
		/*hc*/
		em.get().persist(entity);
		/*hc:afterpersist*/
		/*hc*/
	}
	
	@Transactional
	public void mergeTrans(User entity){
		merge(entity);
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	protected void merge(User entity) {
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
		em.get().remove(em.get().find(User.class, id));
		/*hc:afterremove*/
		/*hc*/
	}
	
	public List<User> find(AbstractSearchAction action) {
		return find(action, null, true, true);
	}
	
	public List<User> find(
				AbstractSearchAction action
				, UserSelectorCustomizer customizer
				, boolean useDefaultQuery
				, boolean useDefaultOrder) {
		UserSelector<User> selector 
			= new UserSelector<User>(em, User.class);
			
		if (customizer != null)
			customizer.customizeSelect(selector);
			
		if(useDefaultQuery)
			selector.buildDefaultQuery(action);

		selector.setDistinctIfNotForcedFalse();
		
		if (useDefaultOrder)
			selector.orderBy(action);
		
		List<User> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		return count(action, null, true);
	}
	
	public Long count(AbstractSearchAction action, UserSelectorCustomizer customizer, boolean useDefaultQuery){
		UserSelector<Long> selector 
			= new UserSelector<Long>(em, Long.class);
		
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
	
	public User findById(Long id){
		User o = em.get().find(User.class, id);
		return o;
	}
	
	@Transactional
	public ObjectManipulationActionResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationActionResult result = new ObjectManipulationActionResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			User newState = doCreateOrEdit((KeyValueObject)action.getObject());
			UserKVO kvo = mapper.entityToKvo(newState);
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
	
	protected User doCreateOrEdit(KeyValueObject kvo) {
		User entity = null;
		
		if(kvo.isNew())
			entity = new User();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new UserKVO((KeyValueObject)kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
			
		return entity;
	}
	
	
	public UserKVO findKvoById(Long id) {
		User entity = findById(id);
		return mapper.entityToKvo(entity);
	}
	
	public UserKVO mergeWithDbState(KeyValueObject difference){
		if (difference.isNew())
			return new UserKVO(difference);
		UserKVO dbState = findKvoById(difference.getId());
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
					, UserSelectorCustomizer customizer){
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