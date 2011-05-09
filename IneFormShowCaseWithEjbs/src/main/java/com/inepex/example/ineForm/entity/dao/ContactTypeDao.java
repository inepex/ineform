package com.inepex.example.ineForm.entity.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Expression;

import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.dao.query.ContactTypeQuery;
import com.inepex.example.ineForm.entity.kvo.ContactTypeKVO;
import com.inepex.example.ineForm.entity.mapper.ContactTypeMapper;
import com.inepex.ineForm.server.KVManipulatorDaoBase;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineForm.shared.dispatch.ObjectListResult;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineForm.shared.dispatch.RelationListResult;
import com.inepex.ineFrame.server.CriteriaSelector;
import com.inepex.ineom.shared.kvo.AssistedObject;

@Stateless
public class ContactTypeDao extends KVManipulatorDaoBase {

	public static class ContactTypeSelector<T> extends CriteriaSelector<T, ContactType> {
		public ContactTypeSelector(EntityManager em, Class<T> resultClass) {
			super(em, resultClass, ContactType.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			cq.distinct(true);
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = ContactTypeQuery.buildWhere(action, cb, root, expr);
				if (expr != null) 
					cq.where(expr);
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(ContactTypeQuery.getOrderExpression(action, cb, root));
		}
	}

	public ContactTypeMapper mapper = new ContactTypeMapper();
	
	protected ContactTypeSelector<ContactType> sel = null;

	@PersistenceContext
	protected EntityManager em;
	
	public ContactTypeDao(){
	}	
	
	public ContactTypeDao(EntityManager em){
		this.em = em;
	}	
	
	/*hc:customConstructors*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new ContactTypeSelector<ContactType>(em, ContactType.class);
	}
	
	public void persist(ContactType entity){
		/*hc:beforepersist*//*hc*/

		em.persist(entity);
		/*hc:afterpersist*//*hc*/
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	public void merge(ContactType entity) {
		/*hc:beforemerge*/
		/*hc*/
		em.merge(entity);
		/*hc:aftermerge*/
		/*hc*/	
	}
		
	public void remove(Long id) {
		em.remove(em.find(ContactType.class, id));
	}
	
	public List<ContactType> find(AbstractSearchAction action) {
		ContactTypeSelector<ContactType> selector 
			= new ContactTypeSelector<ContactType>(em, ContactType.class);
			
		selector.buildDefaultQuery(action);
		selector.orderBy(action);
		
		List<ContactType> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		ContactTypeSelector<Long> selector 
			= new ContactTypeSelector<Long>(em, Long.class);
		selector.buildDefaultQuery(action);
		selector.cq.select(selector.getCountExpression());
		
		Long res = selector.getTypedQuery().getSingleResult();
		return res;
	}
	
	public ContactType findById(Long id){
		ContactType o = em.find(ContactType.class, id);
		return o;
	}
	
	public ObjectManipulationResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationResult result = new ObjectManipulationResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			ContactTypeKVO newState = doCreateOrEdit(action.getObject());
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
	
	protected ContactTypeKVO doCreateOrEdit(AssistedObject kvo) {
		ContactType entity = null;
		
		if(kvo.isNew())
			entity = new ContactType();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new ContactTypeKVO(kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
		return mapper.entityToKvo(entity);
	}
	
	
	public ContactTypeKVO findKvoById(Long id) {
		ContactType entity = findById(id);
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
