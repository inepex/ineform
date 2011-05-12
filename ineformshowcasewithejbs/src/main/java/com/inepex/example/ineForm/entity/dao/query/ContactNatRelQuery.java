package com.inepex.example.ineForm.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.inepex.example.ineForm.entity.Contact;
import com.inepex.example.ineForm.entity.ContactNatRel;
import com.inepex.example.ineForm.entity.Nationality;
import com.inepex.example.ineForm.entity.assist.NationalityAssist;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelKVO;
import com.inepex.example.ineForm.entity.metaentity.ContactNatRel_;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.kvo.IFConsts;

public class ContactNatRelQuery {

	
	public static Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<ContactNatRel> from
		, Expression<Boolean> base){
	return base;
	}
	
	
	public static Order getOrderExpression(
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
			orderKey = ContactNatRelKVO.k_nationality;		
		}
		Expression<?> orderExpr = null;
		if (orderKey.equals(ContactNatRelKVO.k_nationality)){
			Join<ContactNatRel, Nationality> title = from.join(ContactNatRel_.nationality);
			orderExpr = title.get(NationalityAssist.getOrderKey());
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
			, Path<ContactNatRel> from
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
