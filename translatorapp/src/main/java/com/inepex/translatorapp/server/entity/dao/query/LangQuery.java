package com.inepex.translatorapp.server.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;
import com.inepex.ineom.shared.util.SharedUtil;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.Lang_;
import com.inepex.translatorapp.shared.kvo.LangConsts;
import com.inepex.translatorapp.shared.kvo.LangHandlerFactory;
import com.inepex.translatorapp.shared.kvo.LangHandlerFactory.LangSearchHandler;

public class LangQuery extends BaseQuery<Lang>{

	private final LangHandlerFactory handlerFactory;
	
	@Inject
	public LangQuery(DescriptorStore descriptorStore) {
		this.handlerFactory= new LangHandlerFactory(descriptorStore);
	}
	
	@Override
	public Expression<Boolean> buildWhere(
		AbstractSearch action
		, CriteriaBuilder cb
		, Root<Lang> from
		, Expression<Boolean> base){
			
		LangSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
		Long id = handler.getLong(LangConsts.s_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Lang_.id), id));
		String isoName = handler.getString(LangConsts.s_isoName);
		if (isoName!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Lang_.isoName)),
	    	isoName.toUpperCase() + "%"));
    			
	return base;
	}
	
	@Override
	public Order getOrderExpression(
			AbstractSearch action
			, CriteriaBuilder cb
			, Root<Lang> from
			){
		Order o;
		String orderKey = action == null ? null : action.getOrderKey();
		if (orderKey == null) {
			//default default order
			orderKey = IFConsts.KEY_ID;
			//default order specified:
		}
		Expression<?> orderExpr = null;
		List<String> idList = SharedUtil.listFromDotSeparated(orderKey);
			
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
			, Path<Lang> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(Lang_.isoName)), value.toUpperCase() + "%"));
		return expr;	
	}
	
}