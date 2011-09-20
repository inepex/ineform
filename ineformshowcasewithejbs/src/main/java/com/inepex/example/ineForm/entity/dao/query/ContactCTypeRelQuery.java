package com.inepex.example.ineForm.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.ContactCTypeRel;
import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.assist.ContactTypeAssist;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelConsts;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelHandlerFactory.ContactCTypeRelSearchHandler;
import com.inepex.example.ineForm.entity.metaentity.ContactCTypeRel_;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;

public class ContactCTypeRelQuery extends BaseQuery<ContactCTypeRel>{

	private final ContactCTypeRelHandlerFactory handlerFactory;
	
	@Inject
	public ContactCTypeRelQuery(DescriptorStore descriptorStore) {
		this.handlerFactory= new ContactCTypeRelHandlerFactory(descriptorStore);
	}
	
	public Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<ContactCTypeRel> from
		, Expression<Boolean> base){
			
		ContactCTypeRelSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
	return base;
	}
	
	
	public Order getOrderExpression(
			AbstractSearchAction action
			, CriteriaBuilder cb
			, Root<ContactCTypeRel> from
			){
		Order o;
		String orderKey = action == null ? null : action.getOrderKey();
		if (orderKey == null) {
			//default default order
			orderKey = IFConsts.KEY_ID;
			//default order specified:
			orderKey = ContactCTypeRelConsts.k_contactType;		
		}
		Expression<?> orderExpr = null;
		List<String> idList = Node.idToIdList(orderKey);
			
		if(idList.get(0).equals(ContactCTypeRelConsts.k_contactType)){
			if(idList.size()==1) {
				Join<ContactCTypeRel, ContactType> title = from.join(ContactCTypeRel_.contactType);
				orderExpr = title.get(ContactTypeAssist.getOrderKey());
			} else {
    			Join<ContactCTypeRel, ?> orderPath =  from.join(idList.get(0));
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
			, Path<ContactCTypeRel> from
			, String value){
		Expression<Boolean> expr = null;
		return expr;	
	}
	
}