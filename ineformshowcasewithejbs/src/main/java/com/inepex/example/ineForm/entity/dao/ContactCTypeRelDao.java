package com.inepex.example.ineForm.entity.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Expression;

import com.inepex.example.ineForm.entity.ContactCTypeRel;
import com.inepex.example.ineForm.entity.dao.query.ContactCTypeRelQuery;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelKVO;
import com.inepex.example.ineForm.entity.mapper.ContactCTypeRelMapper;
import com.inepex.ineForm.server.KVManipulatorDaoBase;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineForm.shared.dispatch.ObjectListResult;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineForm.shared.dispatch.RelationListResult;
import com.inepex.ineFrame.server.CriteriaSelector;
import com.inepex.ineom.shared.kvo.AssistedObject;

@Stateless
public class ContactCTypeRelDao extends KVManipulatorDaoBase {

	public static class ContactCTypeRelSelector<T> extends CriteriaSelector<T, ContactCTypeRel> {
		public ContactCTypeRelSelector(EntityManager em, Class<T> resultClass) {
			super(em, resultClass, ContactCTypeRel.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			cq.distinct(true);
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = ContactCTypeRelQuery.buildWhere(action, cb, root, expr);
				if (expr != null) 
					cq.where(expr);
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(ContactCTypeRelQuery.getOrderExpression(action, cb, root));
		}
	}

	public ContactCTypeRelMapper mapper = new ContactCTypeRelMapper();
	
	protected ContactCTypeRelSelector<ContactCTypeRel> sel = null;

	@PersistenceContext
	protected EntityManager em;
	
	public ContactCTypeRelDao(){
	}	
	
	public ContactCTypeRelDao(EntityManager em){
		this.em = em;
	}	
	
	/*hc:customConstructors*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new ContactCTypeRelSelector<ContactCTypeRel>(em, ContactCTypeRel.class);
	}
	
	public void persist(ContactCTypeRel entity){
		/*hc:beforepersist*//*hc*/

		em.persist(entity);
		/*hc:afterpersist*//*hc*/
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	public void merge(ContactCTypeRel entity) {
		/*hc:beforemerge*/
		/*hc*/
		em.merge(entity);
		/*hc:aftermerge*/
		/*hc*/	
	}
		
	public void remove(Long id) {
		em.remove(em.find(ContactCTypeRel.class, id));
	}
	
	public List<ContactCTypeRel> find(AbstractSearchAction action) {
		ContactCTypeRelSelector<ContactCTypeRel> selector 
			= new ContactCTypeRelSelector<ContactCTypeRel>(em, ContactCTypeRel.class);
			
		selector.buildDefaultQuery(action);
		selector.orderBy(action);
		
		List<ContactCTypeRel> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		ContactCTypeRelSelector<Long> selector 
			= new ContactCTypeRelSelector<Long>(em, Long.class);
		selector.buildDefaultQuery(action);
		selector.cq.select(selector.getCountExpression());
		
		Long res = selector.getTypedQuery().getSingleResult();
		return res;
	}
	
	public ContactCTypeRel findById(Long id){
		ContactCTypeRel o = em.find(ContactCTypeRel.class, id);
		return o;
	}
	
	public ObjectManipulationResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationResult result = new ObjectManipulationResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			ContactCTypeRelKVO newState = doCreateOrEdit(action.getObject());
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
	
	protected ContactCTypeRelKVO doCreateOrEdit(AssistedObject kvo) {
		ContactCTypeRel entity = null;
		
		if(kvo.isNew())
			entity = new ContactCTypeRel();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new ContactCTypeRelKVO(kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
		return mapper.entityToKvo(entity);
	}
	
	
	public ContactCTypeRelKVO findKvoById(Long id) {
		ContactCTypeRel entity = findById(id);
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
