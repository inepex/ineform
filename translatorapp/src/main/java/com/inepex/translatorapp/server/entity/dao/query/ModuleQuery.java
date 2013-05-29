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
import com.inepex.translatorapp.server.entity.Module;
import com.inepex.translatorapp.server.entity.Module_;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;
import com.inepex.translatorapp.shared.kvo.ModuleHandlerFactory;
import com.inepex.translatorapp.shared.kvo.ModuleHandlerFactory.ModuleSearchHandler;

public class ModuleQuery extends BaseQuery<Module>{

	private final ModuleHandlerFactory handlerFactory;
	
	@Inject
	public ModuleQuery(DescriptorStore descriptorStore) {
		this.handlerFactory= new ModuleHandlerFactory(descriptorStore);
	}
	
	@Override
	public Expression<Boolean> buildWhere(
		AbstractSearch action
		, CriteriaBuilder cb
		, Root<Module> from
		, Expression<Boolean> base){
			
		ModuleSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
		Long id = handler.getLong(ModuleConsts.s_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Module_.id), id));
		String name = handler.getString(ModuleConsts.s_name);
		if (name!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Module_.name)),
	    	name.toUpperCase() + "%"));
    			
	return base;
	}
	
	@Override
	public Order getOrderExpression(
			AbstractSearch action
			, CriteriaBuilder cb
			, Root<Module> from
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
			, Path<Module> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(Module_.name)), value.toUpperCase() + "%"));
		return expr;	
	}
	
}