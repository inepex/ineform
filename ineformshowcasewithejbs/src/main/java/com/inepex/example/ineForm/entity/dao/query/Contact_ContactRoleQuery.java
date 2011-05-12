package com.inepex.example.ineForm.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.inepex.example.ineForm.entity.Contact_ContactRole;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.kvo.IFConsts;

public class Contact_ContactRoleQuery {

	
	public static Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<Contact_ContactRole> from
		, Expression<Boolean> base){
	return base;
	}
	
	
	public static Order getOrderExpression(
			AbstractSearchAction action
			, CriteriaBuilder cb
			, Root<Contact_ContactRole> from
			){
		Order o;
		String orderKey = action == null ? null : action.getOrderKey();
		if (orderKey == null) {
			//default default order
			orderKey = IFConsts.KEY_ID;
			//default order specified:
		}
		Expression<?> orderExpr = null;
		List<String> idList = Node.idToIdList(orderKey);
			
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
			, Path<Contact_ContactRole> from
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
