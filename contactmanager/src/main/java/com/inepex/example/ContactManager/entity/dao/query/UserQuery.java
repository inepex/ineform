package com.inepex.example.ContactManager.entity.dao.query;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.inepex.example.ContactManager.entity.User;
import com.inepex.example.ContactManager.entity.User_;
import com.inepex.example.ContactManager.entity.kvo.search.UserSearchKVO;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.dispatch.AbstractSearchAction;
import com.inepex.ineom.shared.kvo.IFConsts;

public class UserQuery {

	
	public static Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<User> from
		, Expression<Boolean> base){
		Long id = action.getSearchParameters().getLong(UserSearchKVO.k_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(User_.id), id));
		String firstName = action.getSearchParameters().getString(UserSearchKVO.k_firstName);
		if (firstName!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(User_.firstName)), firstName.toUpperCase() + "%"));
		String lastName = action.getSearchParameters().getString(UserSearchKVO.k_lastName);
		if (lastName!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(User_.lastName)), lastName.toUpperCase() + "%"));
		String email = action.getSearchParameters().getString(UserSearchKVO.k_email);
		if (email!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(User_.email)), email.toUpperCase() + "%"));
	return base;
	}
	
	
	public static Order getOrderExpression(
			AbstractSearchAction action
			, CriteriaBuilder cb
			, Root<User> from
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
			, Path<User> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(User_.firstName)), value.toUpperCase() + "%"));
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(User_.lastName)), value.toUpperCase() + "%"));
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(User_.email)), value.toUpperCase() + "%"));
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