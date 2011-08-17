package com.inepex.example.ContactManager.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.inepex.example.ContactManager.entity.Company;
import com.inepex.example.ContactManager.entity.Company_;
import com.inepex.example.ContactManager.entity.kvo.search.CompanySearchKVO;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;
import com.inepex.ineom.shared.kvo.IFConsts;

public class CompanyQuery extends BaseQuery<Company>{

	
	public Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<Company> from
		, Expression<Boolean> base){
		Long id = action.getSearchParameters().getLong(CompanySearchKVO.k_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Company_.id), id));
		String name = action.getSearchParameters().getString(CompanySearchKVO.k_name);
		if (name!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Company_.name)), name.toUpperCase() + "%"));
		String phone = action.getSearchParameters().getString(CompanySearchKVO.k_phone);
		if (phone!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Company_.phone)), phone.toUpperCase() + "%"));
		String email = action.getSearchParameters().getString(CompanySearchKVO.k_email);
		if (email!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Company_.email)), email.toUpperCase() + "%"));
		String webPage = action.getSearchParameters().getString(CompanySearchKVO.k_webPage);
		if (webPage!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Company_.webPage)), webPage.toUpperCase() + "%"));
	return base;
	}
	
	
	public Order getOrderExpression(
			AbstractSearchAction action
			, CriteriaBuilder cb
			, Root<Company> from
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
			, Path<Company> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(Company_.name)), value.toUpperCase() + "%"));
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(Company_.phone)), value.toUpperCase() + "%"));
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(Company_.email)), value.toUpperCase() + "%"));
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(Company_.webPage)), value.toUpperCase() + "%"));
		return expr;	
	}
	
}