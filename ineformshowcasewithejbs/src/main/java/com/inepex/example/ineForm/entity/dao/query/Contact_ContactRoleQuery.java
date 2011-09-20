package com.inepex.example.ineForm.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.Contact_ContactRole;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleHandlerFactory.Contact_ContactRoleSearchHandler;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;

public class Contact_ContactRoleQuery extends BaseQuery<Contact_ContactRole>{

	private final Contact_ContactRoleHandlerFactory handlerFactory;
	
	@Inject
	public Contact_ContactRoleQuery(DescriptorStore descriptorStore) {
		this.handlerFactory= new Contact_ContactRoleHandlerFactory(descriptorStore);
	}
	
	public Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<Contact_ContactRole> from
		, Expression<Boolean> base){
			
		Contact_ContactRoleSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
	return base;
	}
	
	
	public Order getOrderExpression(
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
			, Path<Contact_ContactRole> from
			, String value){
		Expression<Boolean> expr = null;
		return expr;	
	}
	
}