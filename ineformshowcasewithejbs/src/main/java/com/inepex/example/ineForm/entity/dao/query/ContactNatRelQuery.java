package com.inepex.example.ineForm.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.ContactNatRel;
import com.inepex.example.ineForm.entity.Nationality;
import com.inepex.example.ineForm.entity.assist.NationalityAssist;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelConsts;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelHandlerFactory.ContactNatRelSearchHandler;
import com.inepex.example.ineForm.entity.metaentity.ContactNatRel_;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;

public class ContactNatRelQuery extends BaseQuery<ContactNatRel>{

	private final ContactNatRelHandlerFactory handlerFactory;
	
	@Inject
	public ContactNatRelQuery(DescriptorStore descriptorStore) {
		this.handlerFactory= new ContactNatRelHandlerFactory(descriptorStore);
	}
	
	public Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<ContactNatRel> from
		, Expression<Boolean> base){
			
		ContactNatRelSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
	return base;
	}
	
	
	public Order getOrderExpression(
			AbstractSearchAction action
			, CriteriaBuilder cb
			, Root<ContactNatRel> from
			){
		Order o;
		String orderKey = action == null ? null : action.getOrderKey();
		if (orderKey == null) {
			//default default order
			orderKey = IFConsts.KEY_ID;
			//default order specified:
			orderKey = ContactNatRelConsts.k_nationality;		
		}
		Expression<?> orderExpr = null;
		List<String> idList = Node.idToIdList(orderKey);
			
		if(idList.get(0).equals(ContactNatRelConsts.k_nationality)){
			if(idList.size()==1) {
				Join<ContactNatRel, Nationality> title = from.join(ContactNatRel_.nationality);
				orderExpr = title.get(NationalityAssist.getOrderKey());
			} else {
    			Join<ContactNatRel, ?> orderPath =  from.join(idList.get(0));
    			for (int i = 1; i < idList.size()-1; i++ ){
    				orderPath = orderPath.join(idList.get(i));
    			}
    			orderExpr = orderPath.get(idList.get(idList.size()-1));
			}
		}
		else
		{
			orderExpr = from.get(orderKey);
		}
		if (action.isDescending() == null)
			//default order
			o = cb.asc(orderExpr);
		else if (action.isDescending())
			o = cb.desc(orderExpr);
		else
			o = cb.asc(orderExpr);
		return o;
	}
	
	public Expression<Boolean> getSearchExpression(
			CriteriaBuilder cb
			, Path<ContactNatRel> from
			, String value){
		Expression<Boolean> expr = null;
		return expr;	
	}
	
}