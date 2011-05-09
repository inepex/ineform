package com.inepex.example.ineForm.entity.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Expression;

import com.inepex.example.ineForm.entity.Nationality;
import com.inepex.example.ineForm.entity.dao.query.NationalityQuery;
import com.inepex.example.ineForm.entity.kvo.NationalityKVO;
import com.inepex.example.ineForm.entity.mapper.NationalityMapper;
import com.inepex.ineForm.server.KVManipulatorDaoBase;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineForm.shared.dispatch.ObjectListResult;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineForm.shared.dispatch.RelationListResult;
import com.inepex.ineFrame.server.CriteriaSelector;
import com.inepex.ineom.shared.kvo.AssistedObject;

@Stateless
public class NationalityDao extends KVManipulatorDaoBase {

	public static class NationalitySelector<T> extends CriteriaSelector<T, Nationality> {
		public NationalitySelector(EntityManager em, Class<T> resultClass) {
			super(em, resultClass, Nationality.class);
		}
		
		public void buildDefaultQuery(AbstractSearchAction action) {
			cq.distinct(true);
			if (action.getSearchParameters() != null) {
				Expression<Boolean> expr = null;
				expr = NationalityQuery.buildWhere(action, cb, root, expr);
				if (expr != null) 
					cq.where(expr);
			}
		}
		
		public void orderBy(AbstractSearchAction action) {
			cq.orderBy(NationalityQuery.getOrderExpression(action, cb, root));
		}
	}

	public NationalityMapper mapper = new NationalityMapper();
	
	protected NationalitySelector<Nationality> sel = null;

	@PersistenceContext
	protected EntityManager em;
	
	public NationalityDao(){
	}	
	
	public NationalityDao(EntityManager em){
		this.em = em;
	}	
	
	/*hc:customConstructors*/
	/*hc*/
	
	protected void createDefaultSelector() {
		sel = new NationalitySelector<Nationality>(em, Nationality.class);
	}
	
	public void persist(Nationality entity){
		/*hc:beforepersist*//*hc*/

		em.persist(entity);
		/*hc:afterpersist*//*hc*/
	}
	
	/** Should be called after modifying!
	 * @param entity
	 */	
	public void merge(Nationality entity) {
		/*hc:beforemerge*/
		/*hc*/
		em.merge(entity);
		/*hc:aftermerge*/
		/*hc*/	
	}
		
	public void remove(Long id) {
		em.remove(em.find(Nationality.class, id));
	}
	
	public List<Nationality> find(AbstractSearchAction action) {
		NationalitySelector<Nationality> selector 
			= new NationalitySelector<Nationality>(em, Nationality.class);
			
		selector.buildDefaultQuery(action);
		selector.orderBy(action);
		
		List<Nationality> res = selector.executeRangeSelect(action.getFirstResult()
											    , action.getNumMaxResult());
		return res;
	}
	
	public Long count(AbstractSearchAction action){
		NationalitySelector<Long> selector 
			= new NationalitySelector<Long>(em, Long.class);
		selector.buildDefaultQuery(action);
		selector.cq.select(selector.getCountExpression());
		
		Long res = selector.getTypedQuery().getSingleResult();
		return res;
	}
	
	public Nationality findById(Long id){
		Nationality o = em.find(Nationality.class, id);
		return o;
	}
	
	public ObjectManipulationResult manipulate(ObjectManipulationAction action) throws Exception {
		ObjectManipulationResult result = new ObjectManipulationResult();
		switch (action.getManipulationType()){
		case CREATE_OR_EDIT_REQUEST:
			NationalityKVO newState = doCreateOrEdit(action.getObject());
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
	
	protected NationalityKVO doCreateOrEdit(AssistedObject kvo) {
		Nationality entity = null;
		
		if(kvo.isNew())
			entity = new Nationality();
		else
			entity = findById(kvo.getId());

		mapper.kvoToEntity(new NationalityKVO(kvo), entity);
		if(kvo.isNew())
			persist(entity);
		else
			merge(entity);
		return mapper.entityToKvo(entity);
	}
	
	
	public NationalityKVO findKvoById(Long id) {
		Nationality entity = findById(id);
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
