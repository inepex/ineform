package com.inepex.example.ineForm.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.inepex.example.ineForm.entity.Contact;
import com.inepex.example.ineForm.entity.ContactCTypeRel;
import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.assist.ContactTypeAssist;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelKVO;
import com.inepex.example.ineForm.entity.metaentity.ContactCTypeRel_;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.kvo.IFConsts;

public class ContactCTypeRelQuery {

	public static Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<ContactCTypeRel> from
		, Expression<Boolean> base){
	return base;
	}
	
	
	public static Order getOrderExpression(
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
			orderKey = ContactCTypeRelKVO.k_contactType;		
		}
		Expression<?> orderExpr = null;
		if (orderKey.equals(ContactCTypeRelKVO.k_contactType)){
			Join<ContactCTypeRel, ContactType> title = from.join(ContactCTypeRel_.contactType);
			orderExpr = title.get(ContactTypeAssist.getOrderKey());
		} else if (orderKey.contains(Node.ID_PART_SEPARATOR)){
			List<String> idList = Node.idToIdList(orderKey);
			Join<Contact, ?> orderPath =  from.join(idList.get(0));
			for (int i = 1; i < idList.size()-1; i++ ){
				orderPath = orderPath.join(idList.get(i));
			}
			orderExpr = orderPath.get(idList.get(idList.size()-1));
		} 
		else
		{
			orderExpr = from.get(orderKey);
		}
		if (action.isDescending())
			o = cb.desc(orderExpr);
		else
			o = cb.asc(orderExpr);
		return o;
	}
	
	public static Expression<Boolean> getSearchExpression(
			CriteriaBuilder cb
			, Path<ContactCTypeRel> from
			, String value){
		Expression<Boolean> expr = null;
		return expr;	
	}

	public static Expression<Boolean> addAndExpression(CriteriaBuilder cb, Expression<Boolean> base, Expression<Boolean> toAdd){
		if (base == null) base = toAdd;
		else base = cb.and(base, toAdd);
		return base;
	}
	
	public static Expression<Boolean> addOrExpression(CriteriaBuilder cb, Expression<Boolean> base, Expression<Boolean> toAdd){
		if (base == null) base = toAdd;
		else base = cb.or(base, toAdd);
		return base;
	}	
}
