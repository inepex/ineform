package com.inepex.example.ineForm.entity.dao.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.kvo.ContactTypeKVO;
import com.inepex.example.ineForm.entity.kvo.search.ContactTypeSearchKVO;
import com.inepex.example.ineForm.entity.metaentity.ContactType_;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineom.shared.kvo.IFConsts;

public class ContactTypeQuery {

	
	public static Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<ContactType> from
		, Expression<Boolean> base){
		String typeName = action.getSearchParameters().getString(ContactTypeSearchKVO.k_typeName);
		if (typeName!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(ContactType_.typeName)), typeName.toUpperCase() + "%"));
	return base;
	}
	
	
	public static Order getOrderExpression(
			AbstractSearchAction action
			, CriteriaBuilder cb
			, Root<ContactType> from
			){
		Order o;
		String orderKey = action == null ? null : action.getOrderKey();
		if (orderKey == null) {
			//default default order
			orderKey = IFConsts.KEY_ID;
			//default order specified:
			orderKey = ContactTypeKVO.k_typeName;		
		}
		Expression<?> orderExpr = null;
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
			, Path<ContactType> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(ContactType_.typeName)), value.toUpperCase() + "%"));
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
