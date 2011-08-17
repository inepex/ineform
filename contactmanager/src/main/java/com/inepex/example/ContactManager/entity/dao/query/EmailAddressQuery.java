package com.inepex.example.ContactManager.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.inepex.example.ContactManager.entity.EmailAddress;
import com.inepex.example.ContactManager.entity.EmailAddress_;
import com.inepex.example.ContactManager.entity.kvo.search.EmailAddressSearchKVO;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;
import com.inepex.ineom.shared.kvo.IFConsts;

public class EmailAddressQuery extends BaseQuery<EmailAddress>{

	
	public Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<EmailAddress> from
		, Expression<Boolean> base){
		Long id = action.getSearchParameters().getLong(EmailAddressSearchKVO.k_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(EmailAddress_.id), id));
		String email = action.getSearchParameters().getString(EmailAddressSearchKVO.k_email);
		if (email!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(EmailAddress_.email)), email.toUpperCase() + "%"));
	return base;
	}
	
	
	public Order getOrderExpression(
			AbstractSearchAction action
			, CriteriaBuilder cb
			, Root<EmailAddress> from
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
			, Path<EmailAddress> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(EmailAddress_.email)), value.toUpperCase() + "%"));
		return expr;	
	}
	
}