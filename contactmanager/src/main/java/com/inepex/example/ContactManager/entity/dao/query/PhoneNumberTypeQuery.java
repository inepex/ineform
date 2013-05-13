package com.inepex.example.ContactManager.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.PhoneNumberType_;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberTypeConsts;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberTypeHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberTypeHandlerFactory.PhoneNumberTypeSearchHandler;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;

public class PhoneNumberTypeQuery extends BaseQuery<PhoneNumberType>{

	private final PhoneNumberTypeHandlerFactory handlerFactory;
	
	@Inject
	public PhoneNumberTypeQuery(DescriptorStore descriptorStore) {
		this.handlerFactory= new PhoneNumberTypeHandlerFactory(descriptorStore);
	}
	
	@Override
	public Expression<Boolean> buildWhere(
		AbstractSearch action
		, CriteriaBuilder cb
		, Root<PhoneNumberType> from
		, Expression<Boolean> base){
			
		PhoneNumberTypeSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
		Long id = handler.getLong(PhoneNumberTypeConsts.s_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(PhoneNumberType_.id), id));
		String name = handler.getString(PhoneNumberTypeConsts.s_name);
		if (name!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(PhoneNumberType_.name)), name.toUpperCase() + "%"));
		
		return base;
	}
	
	
	@Override
	public Order getOrderExpression(
			AbstractSearch action
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
		if (action.isDescending() == null)
			//default order
			o = cb.asc(orderExpr);
		else if (action.isDescending())
			o = cb.desc(orderExpr);
		else
			o = cb.asc(orderExpr);
		return o;
	}
	
	@Override
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