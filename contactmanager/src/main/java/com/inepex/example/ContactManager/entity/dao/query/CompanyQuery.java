package com.inepex.example.ContactManager.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.Company;
import com.inepex.example.ContactManager.entity.Company_;
import com.inepex.example.ContactManager.entity.kvo.CompanyConsts;
import com.inepex.example.ContactManager.entity.kvo.CompanyHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.CompanyHandlerFactory.CompanySearchHandler;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;

public class CompanyQuery extends BaseQuery<Company>{

	private final CompanyHandlerFactory handlerFactory;
	
	@Inject
	public CompanyQuery(DescriptorStore descriptorStore) {
		this.handlerFactory= new CompanyHandlerFactory(descriptorStore);
	}
	
	public Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<Company> from
		, Expression<Boolean> base){
			
		CompanySearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
		Long id = handler.getLong(CompanyConsts.s_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Company_.id), id));
		String name = handler.getString(CompanyConsts.s_name);
		if (name!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Company_.name)), name.toUpperCase() + "%"));
		String phone = handler.getString(CompanyConsts.s_phone);
		if (phone!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Company_.phone)), phone.toUpperCase() + "%"));
		String email = handler.getString(CompanyConsts.s_email);
		if (email!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Company_.email)), email.toUpperCase() + "%"));
		String webPage = handler.getString(CompanyConsts.s_webPage);
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