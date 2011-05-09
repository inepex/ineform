package com.inepex.example.ineForm.entity.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Expression;

import com.inepex.example.ineForm.entity.ContactNatRel;
import com.inepex.example.ineForm.entity.dao.query.ContactNatRelQuery;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelKVO;
import com.inepex.example.ineForm.entity.mapper.ContactNatRelMapper;
import com.inepex.ineForm.server.KVManipulatorDaoBase;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineForm.shared.dispatch.ObjectListResult;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineForm.shared.dispatch.RelationListResult;
import com.inepex.ineFrame.server.CriteriaSelector;
import com.inepex.ineom.shared.kvo.AssistedObject;

@Stateless
public class ContactNatRelDao extends KVManipulatorDaoBase {

	public static class ContactNatRelSelector<T> extends CriteriaSelector<T, ContactNatRel> {
		public ContactNatRelSelector(EntityManager em, Class<T> resultClass) {
			super(em, resultClass, ContactNatRel.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			cq.distinct(true);
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = ContactNatRelQuery.buildWhere(action, cb, root, expr);
				if (expr != null) 
					cq.where(expr);
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(ContactNatRelQuery.getOrderExpression(action, cb, root));
		}
	}

	public ContactNatRelMapper mapper = new ContactNatRelMapper();
	
	protected ContactNatRelSelector<ContactNatRel> sel = null;

	@PersistenceContext
	protected EntityManager em;
	
	public ContactNatRelDao(){
	}	
	
	public ContactNatRelDao(EntityManager em){
		this.em = em;
	}	
	
	/*hc:customConstructors*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new ContactNatRelSelector<ContactNatRel>(em, ContactNatRel.class);
	}
	
	public void persist(ContactNatRel entity){
		/*hc:beforepersist*//*hc*/

		em.persist(entity);
		/*hc:afterpersist*//*hc*/
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	public void merge(ContactNatRel entity) {
		/*hc:beforemerge*/
		/*hc*/
		em.merge(entity);
		/*hc:aftermerge*/
		/*hc*/	
	}
		
	public void remove(Long id) {
		em.remove(em.find(ContactNatRel.class, id));
	}
	
	public List<ContactNatRel> find(AbstractSearchAction action) {
		ContactNatRelSelector<ContactNatRel> selector 
			= new ContactNatRelSelector<ContactNatRel>(em, ContactNatRel.class);
			
		selector.buildDefaultQuery(action);
		selector.orderBy(action);
		
		List<ContactNatRel> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		ContactNatRelSelector<Long> selector 
			= new ContactNatRelSelector<Long>(em, Long.class);
		selector.buildDefaultQuery(action);
		selector.cq.select(selector.getCountExpression());
		
		Long res = selector.getTypedQuery().getSingleResult();
		return res;
	}
	
	public ContactNatRel findById(Long id){
		ContactNatRel o = em.find(ContactNatRel.class, id);
		return o;
	}
	
	public ObjectManipulationResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationResult result = new ObjectManipulationResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			ContactNatRelKVO newState = doCreateOrEdit(action.getObject());
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
	
	protected ContactNatRelKVO doCreateOrEdit(AssistedObject kvo) {
		ContactNatRel entity = null;
		
		if(kvo.isNew())
			entity = new ContactNatRel();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new ContactNatRelKVO(kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
		return mapper.entityToKvo(entity);
	}
	
	
	public ContactNatRelKVO findKvoById(Long id) {
		ContactNatRel entity = findById(id);
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
