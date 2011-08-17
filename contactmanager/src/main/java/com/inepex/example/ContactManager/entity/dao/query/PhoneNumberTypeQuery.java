package com.inepex.example.ContactManager.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.PhoneNumberType_;
import com.inepex.example.ContactManager.entity.kvo.search.PhoneNumberTypeSearchKVO;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;
import com.inepex.ineom.shared.kvo.IFConsts;

public class PhoneNumberTypeQuery extends BaseQuery<PhoneNumberType>{

	
	public Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<PhoneNumberType> from
		, Expression<Boolean> base){
		Long id = action.getSearchParameters().getLong(PhoneNumberTypeSearchKVO.k_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(PhoneNumberType_.id), id));
		String name = action.getSearchParameters().getString(PhoneNumberTypeSearchKVO.k_name);
		if (name!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(PhoneNumberType_.name)), name.toUpperCase() + "%"));
	return base;
	}
	
	
	public Order getOrderExpression(
			AbstractSearchAction action
			, CriteriaBuilder cb
			, Root<PhoneNumberType> from
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
	
	public Expression<Boolean> getSearchExpression(
			CriteriaBuilder cb
			, Path<PhoneNumberType> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(PhoneNumberType_.name)), value.toUpperCase() + "%"));
		return expr;	
	}
	
}