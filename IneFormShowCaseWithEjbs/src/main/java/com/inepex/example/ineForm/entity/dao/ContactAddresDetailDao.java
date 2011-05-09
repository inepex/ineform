package com.inepex.example.ineForm.entity.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Expression;

import com.inepex.example.ineForm.entity.ContactAddresDetail;
import com.inepex.example.ineForm.entity.dao.query.ContactAddresDetailQuery;
import com.inepex.example.ineForm.entity.kvo.ContactAddresDetailKVO;
import com.inepex.example.ineForm.entity.mapper.ContactAddresDetailMapper;
import com.inepex.ineForm.server.KVManipulatorDaoBase;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineForm.shared.dispatch.ObjectListResult;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineForm.shared.dispatch.RelationListResult;
import com.inepex.ineFrame.server.CriteriaSelector;
import com.inepex.ineom.shared.kvo.AssistedObject;

@Stateless
public class ContactAddresDetailDao extends KVManipulatorDaoBase {

	public static class ContactAddresDetailSelector<T> extends CriteriaSelector<T, ContactAddresDetail> {
		public ContactAddresDetailSelector(EntityManager em, Class<T> resultClass) {
			super(em, resultClass, ContactAddresDetail.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			cq.distinct(true);
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = ContactAddresDetailQuery.buildWhere(action, cb, root, expr);
				if (expr != null) 
					cq.where(expr);
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(ContactAddresDetailQuery.getOrderExpression(action, cb, root));
		}
	}

	public ContactAddresDetailMapper mapper = new ContactAddresDetailMapper();
	
	protected ContactAddresDetailSelector<ContactAddresDetail> sel = null;

	@PersistenceContext
	protected EntityManager em;
	
	public ContactAddresDetailDao(){
	}	
	
	public ContactAddresDetailDao(EntityManager em){
		this.em = em;
	}	
	
	/*hc:customConstructors*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new ContactAddresDetailSelector<ContactAddresDetail>(em, ContactAddresDetail.class);
	}
	
	public void persist(ContactAddresDetail entity){
		/*hc:beforepersist*//*hc*/

		em.persist(entity);
		/*hc:afterpersist*//*hc*/
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	public void merge(ContactAddresDetail entity) {
		/*hc:beforemerge*/
		/*hc*/
		em.merge(entity);
		/*hc:aftermerge*/
		/*hc*/	
	}
		
	public void remove(Long id) {
		em.remove(em.find(ContactAddresDetail.class, id));
	}
	
	public List<ContactAddresDetail> find(AbstractSearchAction action) {
		ContactAddresDetailSelector<ContactAddresDetail> selector 
			= new ContactAddresDetailSelector<ContactAddresDetail>(em, ContactAddresDetail.class);
			
		selector.buildDefaultQuery(action);
		selector.orderBy(action);
		
		List<ContactAddresDetail> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		ContactAddresDetailSelector<Long> selector 
			= new ContactAddresDetailSelector<Long>(em, Long.class);
		selector.buildDefaultQuery(action);
		selector.cq.select(selector.getCountExpression());
		
		Long res = selector.getTypedQuery().getSingleResult();
		return res;
	}
	
	public ContactAddresDetail findById(Long id){
		ContactAddresDetail o = em.find(ContactAddresDetail.class, id);
		return o;
	}
	
	public ObjectManipulationResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationResult result = new ObjectManipulationResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			ContactAddresDetailKVO newState = doCreateOrEdit(action.getObject());
			result.setObjectsNewState(newState);
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
	
	protected ContactAddresDetailKVO doCreateOrEdit(AssistedObject kvo) {
		ContactAddresDetail entity = null;
		
		if(kvo.isNew())
			entity = new ContactAddresDetail();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new ContactAddresDetailKVO(kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
		return mapper.entityToKvo(entity);
	}
	
	
	public ContactAddresDetailKVO findKvoById(Long id) {
		ContactAddresDetail entity = findById(id);
		return mapper.entityToKvo(entity);
	}
	
	public ObjectListResult search(AbstractSearchAction action){
		ObjectListResult res = new ObjectListResult();
		if (action.isQueryResultCount()){
			res.setAllResultCount(count(action));
		}
		if(action.getNumMaxResult() > 0)
			res.setList(mapper.entityListToKvoList(find(action)));		
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
