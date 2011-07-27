package com.inepex.example.ContactManager.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.inepex.example.ContactManager.entity.Meeting;
import com.inepex.example.ContactManager.entity.dao.query.MeetingQuery;
import com.inepex.example.ContactManager.entity.kvo.MeetingKVO;
import com.inepex.example.ContactManager.entity.mapper.MeetingMapper;
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
public class MeetingDao extends KVManipulatorDaoBase {

	public static class MeetingSelector<T> extends CriteriaSelector<T, Meeting> {
		public MeetingSelector(Provider<EntityManager> em, Class<T> resultClass) {
			super(em, resultClass, Meeting.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = MeetingQuery.buildWhere(action, cb, root, expr);
				if (expr != null) {
					Predicate exisitngRestriction = cq.getRestriction();
					if (exisitngRestriction != null)
						expr = cb.and(expr, exisitngRestriction);
									
					cq.where(expr);
				}
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(MeetingQuery.getOrderExpression(action, cb, root));
		}
	}

	public static interface MeetingSelectorCustomizer extends SelectorCustomizer<MeetingSelector<?>> {
	}
	
	public MeetingMapper mapper = new MeetingMapper();
	
	protected MeetingSelector<Meeting> sel = null;

	protected MeetingSelector<Long> cSel = null;

	
	protected final Provider<EntityManager> em;
	

	@Inject
	public MeetingDao(Provider<EntityManager> em){
		this.em=em;
			}
	
	/*hc:customFields*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new MeetingSelector<Meeting>(em, Meeting.class);
	}
	
	protected void createDefaultCountSelector() {
		cSel = new MeetingSelector<Long>(em, Long.class);
	}
	
	@Transactional
	public void persistTrans(Meeting entity){
		persist(entity);
	}
	
	protected void persist(Meeting entity){
		/*hc:beforepersist*/
		/*hc*/
		em.get().persist(entity);
		/*hc:afterpersist*/
		/*hc*/
	}
	
	@Transactional
	public void mergeTrans(Meeting entity){
		merge(entity);
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	protected void merge(Meeting entity) {
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
		em.get().remove(em.get().find(Meeting.class, id));
		/*hc:afterremove*/
		/*hc*/
	}
	
	public List<Meeting> find(AbstractSearchAction action) {
		return find(action, null, true, true);
	}
	
	public List<Meeting> find(
				AbstractSearchAction action
				, MeetingSelectorCustomizer customizer
				, boolean useDefaultQuery
				, boolean useDefaultOrder) {
		MeetingSelector<Meeting> selector 
			= new MeetingSelector<Meeting>(em, Meeting.class);
			
		if (customizer != null)
			customizer.customizeSelect(selector);
			
		if(useDefaultQuery)
			selector.buildDefaultQuery(action);

		selector.setDistinctIfNotForcedFalse();
		
		if (useDefaultOrder)
			selector.orderBy(action);
		
		List<Meeting> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		return count(action, null, true);
	}
	
	public Long count(AbstractSearchAction action, MeetingSelectorCustomizer customizer, boolean useDefaultQuery){
		MeetingSelector<Long> selector 
			= new MeetingSelector<Long>(em, Long.class);
		
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
	
	public Meeting findById(Long id){
		Meeting o = em.get().find(Meeting.class, id);
		return o;
	}
	
	@Transactional
	public ObjectManipulationResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationResult result = new ObjectManipulationResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			Meeting newState = doCreateOrEdit((KeyValueObject)action.getObject());
			MeetingKVO kvo = mapper.entityToKvo(newState);
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
	
	protected Meeting doCreateOrEdit(KeyValueObject kvo) {
		Meeting entity = null;
		
		if(kvo.isNew())
			entity = new Meeting();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new MeetingKVO((KeyValueObject)kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
			
		return entity;
	}
	
	
	public MeetingKVO findKvoById(Long id) {
		Meeting entity = findById(id);
		return mapper.entityToKvo(entity);
	}
	
	public MeetingKVO mergeWithDbState(KeyValueObject difference){
		if (difference.isNew())
			return new MeetingKVO(difference);
		MeetingKVO dbState = findKvoById(difference.getId());
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
					, MeetingSelectorCustomizer customizer){
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
