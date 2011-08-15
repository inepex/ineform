package com.inepex.example.ineForm.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.inepex.example.ineForm.entity.ContactAddresDetail;
import com.inepex.example.ineForm.entity.dao.query.ContactAddresDetailQuery;
import com.inepex.example.ineForm.entity.kvo.ContactAddresDetailKVO;
import com.inepex.example.ineForm.entity.mapper.ContactAddresDetailMapper;
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
public class ContactAddresDetailDao extends KVManipulatorDaoBase {

	public static class ContactAddresDetailSelector<T> extends CriteriaSelector<T, ContactAddresDetail> {
		public ContactAddresDetailSelector(Provider<EntityManager> em, Class<T> resultClass) {
			super(em, resultClass, ContactAddresDetail.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = ContactAddresDetailQuery.buildWhere(action, cb, root, expr);
				if (expr != null) {
					Predicate exisitngRestriction = cq.getRestriction();
					if (exisitngRestriction != null)
						expr = cb.and(expr, exisitngRestriction);
									
					cq.where(expr);
				}
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(ContactAddresDetailQuery.getOrderExpression(action, cb, root));
		}
	}

	public static interface ContactAddresDetailSelectorCustomizer extends SelectorCustomizer<ContactAddresDetailSelector<?>> {
	}
	
	public ContactAddresDetailMapper mapper = new ContactAddresDetailMapper();
	
	protected ContactAddresDetailSelector<ContactAddresDetail> sel = null;

	protected ContactAddresDetailSelector<Long> cSel = null;

	@Inject
	Provider<EntityManager> em;
	

	@Inject
	ContactAddresDetailDao(){}

	public ContactAddresDetailDao(Provider<EntityManager> em){
		this.em=em;
	}
	
	/*hc:customFields*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new ContactAddresDetailSelector<ContactAddresDetail>(em, ContactAddresDetail.class);
	}
	
	protected void createDefaultCountSelector() {
		cSel = new ContactAddresDetailSelector<Long>(em, Long.class);
	}
	
	public void persist(ContactAddresDetail entity){
		/*hc:beforepersist*//*hc*/
		em.get().persist(entity);
		/*hc:afterpersist*//*hc*/
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	public void merge(ContactAddresDetail entity) {
		/*hc:beforemerge*/
		/*hc*/
		em.get().merge(entity);
		/*hc:aftermerge*/
		/*hc*/	
	}
		
	public void remove(Long id) {
		/*hc:beforeremove*/
		/*hc*/
		em.get().remove(em.get().find(ContactAddresDetail.class, id));
		/*hc:afterremove*/
		/*hc*/
	}
	
	public List<ContactAddresDetail> find(AbstractSearchAction action) {
		return find(action, null, true, true);
	}
	
	public List<ContactAddresDetail> find(
				AbstractSearchAction action
				, ContactAddresDetailSelectorCustomizer customizer
				, boolean useDefaultQuery
				, boolean useDefaultOrder) {
		ContactAddresDetailSelector<ContactAddresDetail> selector 
			= new ContactAddresDetailSelector<ContactAddresDetail>(em, ContactAddresDetail.class);
			
		if (customizer != null)
			customizer.customizeSelect(selector);
			
		if(useDefaultQuery)
			selector.buildDefaultQuery(action);

		selector.setDistinctIfNotForcedFalse();
		
		if (useDefaultOrder)
			selector.orderBy(action);
		
		List<ContactAddresDetail> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		return count(action, null, true);
	}
	
	public Long count(AbstractSearchAction action, ContactAddresDetailSelectorCustomizer customizer, boolean useDefaultQuery){
		ContactAddresDetailSelector<Long> selector 
			= new ContactAddresDetailSelector<Long>(em, Long.class);
		
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
	
	public ContactAddresDetail findById(Long id){
		ContactAddresDetail o = em.get().find(ContactAddresDetail.class, id);
		return o;
	}
	
	@Transactional
	public ObjectManipulationActionResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationActionResult result = new ObjectManipulationActionResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			ContactAddresDetail newState = doCreateOrEdit((KeyValueObject)action.getObject());
			ContactAddresDetailKVO kvo = mapper.entityToKvo(newState);
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
	
	protected ContactAddresDetail doCreateOrEdit(KeyValueObject kvo) {
		ContactAddresDetail entity = null;
		
		if(kvo.isNew())
			entity = new ContactAddresDetail();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new ContactAddresDetailKVO((KeyValueObject)kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
			
		return entity;
	}
	
	
	public ContactAddresDetailKVO findKvoById(Long id) {
		ContactAddresDetail entity = findById(id);
		return mapper.entityToKvo(entity);
	}
	
	public ContactAddresDetailKVO mergeWithDbState(KeyValueObject difference){
		if (difference.isNew())
			return new ContactAddresDetailKVO(difference);
		ContactAddresDetailKVO dbState = findKvoById(difference.getId());
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
					, ContactAddresDetailSelectorCustomizer customizer){
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
