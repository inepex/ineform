package com.inepex.translatorapp.server.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;
import com.inepex.ineom.shared.util.SharedUtil;
import com.inepex.translatorapp.server.entity.Module;
import com.inepex.translatorapp.server.entity.ModuleRow;
import com.inepex.translatorapp.server.entity.ModuleRow_;
import com.inepex.translatorapp.server.entity.Module_;
import com.inepex.translatorapp.shared.assist.ModuleAssist;
import com.inepex.translatorapp.shared.kvo.ModuleRowConsts;
import com.inepex.translatorapp.shared.kvo.ModuleRowHandlerFactory;
import com.inepex.translatorapp.shared.kvo.ModuleRowHandlerFactory.ModuleRowSearchHandler;

public class ModuleRowQuery extends BaseQuery<ModuleRow>{

	private final ModuleRowHandlerFactory handlerFactory;
	
	private final DescriptorStore descriptorStore;
	
	@Inject
	public ModuleRowQuery(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
		this.handlerFactory= new ModuleRowHandlerFactory(descriptorStore);
	}
	
	@Override
	public Expression<Boolean> buildWhere(
		AbstractSearch action
		, CriteriaBuilder cb
		, Root<ModuleRow> from
		, Expression<Boolean> base){
			
		ModuleRowSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
		Long id = handler.getLong(ModuleRowConsts.s_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(ModuleRow_.id), id));
		String key = handler.getString(ModuleRowConsts.s_key);
		if (key!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(ModuleRow_.key)),
	    	key.toUpperCase() + "%"));
    			
		Relation module = handler.getRelation(ModuleRowConsts.s_module);
		if (module!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(ModuleRow_.module).get(Module_.id), module.getId()));
	return base;
	}
	
	@Override
	public Order getOrderExpression(
			AbstractSearch action
			, CriteriaBuilder cb
			, Root<ModuleRow> from
			){
		Order o;
		String orderKey = action == null ? null : action.getOrderKey();
		if (orderKey == null) {
			//default default order
			orderKey = IFConsts.KEY_ID;
			//default order specified:
			orderKey = ModuleRowConsts.k_key;		
		}
		Expression<?> orderExpr = null;
		List<String> idList = SharedUtil.listFromDotSeparated(orderKey);
			
		if(idList.get(0).equals(ModuleRowConsts.k_module)){
			if(idList.size()==1) {
				Join<ModuleRow, Module> title = from.join(ModuleRow_.module);
				orderExpr = title.get(ModuleAssist.getOrderKey());
			} else {
    			Join<ModuleRow, ?> orderPath =  from.join(idList.get(0));
    			for (int i = 1; i < idList.size()-1; i++ ){
    				orderPath = orderPath.join(idList.get(i));
    			}
    			orderExpr = orderPath.get(idList.get(idList.size()-1));
			}
		}
		else
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
			, Path<ModuleRow> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(ModuleRow_.key)), value.toUpperCase() + "%"));
		return expr;	
	}
	
}