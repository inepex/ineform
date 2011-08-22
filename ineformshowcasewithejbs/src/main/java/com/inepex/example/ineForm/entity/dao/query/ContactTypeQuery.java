package com.inepex.example.ineForm.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.kvo.ContactTypeKVO;
import com.inepex.example.ineForm.entity.kvo.search.ContactTypeSearchKVO;
import com.inepex.example.ineForm.entity.metaentity.ContactType_;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;
import com.inepex.ineom.shared.kvo.IFConsts;

public class ContactTypeQuery extends BaseQuery<ContactType>{

	
	public Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<ContactType> from
		, Expression<Boolean> base){
		String typeName = action.getSearchParameters().getString(ContactTypeSearchKVO.k_typeName);
		if (typeName!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(ContactType_.typeName)), typeName.toUpperCase() + "%"));
	return base;
	}
	
	
	public Order getOrderExpression(
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
		List<String> idList = Node.idToIdList(orderKey);
			
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
			, Path<ContactType> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(ContactType_.typeName)), value.toUpperCase() + "%"));
		return expr;	
	}
	
}