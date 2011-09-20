package com.inepex.example.ineForm.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.Contact_ContactState;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateHandlerFactory.Contact_ContactStateSearchHandler;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;

public class Contact_ContactStateQuery extends BaseQuery<Contact_ContactState>{

	private final Contact_ContactStateHandlerFactory handlerFactory;
	
	@Inject
	public Contact_ContactStateQuery(DescriptorStore descriptorStore) {
		this.handlerFactory= new Contact_ContactStateHandlerFactory(descriptorStore);
	}
	
	public Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<Contact_ContactState> from
		, Expression<Boolean> base){
			
		Contact_ContactStateSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
	return base;
	}
	
	
	public Order getOrderExpression(
			AbstractSearchAction action
			, CriteriaBuilder cb
			, Root<Contact_ContactState> from
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
			, Path<Contact_ContactState> from
			, String value){
		Expression<Boolean> expr = null;
		return expr;	
	}
	
}