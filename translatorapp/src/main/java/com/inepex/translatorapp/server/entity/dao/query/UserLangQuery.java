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
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.User;
import com.inepex.translatorapp.server.entity.UserLang;
import com.inepex.translatorapp.server.entity.UserLang_;
import com.inepex.translatorapp.shared.assist.LangAssist;
import com.inepex.translatorapp.shared.assist.UserAssist;
import com.inepex.translatorapp.shared.kvo.UserLangConsts;
import com.inepex.translatorapp.shared.kvo.UserLangHandlerFactory;
import com.inepex.translatorapp.shared.kvo.UserLangHandlerFactory.UserLangSearchHandler;

public class UserLangQuery extends BaseQuery<UserLang>{

	private final UserLangHandlerFactory handlerFactory;
	
	@Inject
	public UserLangQuery(DescriptorStore descriptorStore) {
		this.handlerFactory= new UserLangHandlerFactory(descriptorStore);
	}
	
	@Override
	public Expression<Boolean> buildWhere(
		AbstractSearch action
		, CriteriaBuilder cb
		, Root<UserLang> from
		, Expression<Boolean> base){
			
		UserLangSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
		Long id = handler.getLong(UserLangConsts.s_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(UserLang_.id), id));
	return base;
	}
	
	@Override
	public Order getOrderExpression(
			AbstractSearch action
			, CriteriaBuilder cb
			, Root<UserLang> from
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
			
		if(idList.get(0).equals(UserLangConsts.k_lang)){
			if(idList.size()==1) {
				Join<UserLang, Lang> title = from.join(UserLang_.lang);
				orderExpr = title.get(LangAssist.getOrderKey());
			} else {
    			Join<UserLang, ?> orderPath =  from.join(idList.get(0));
    			for (int i = 1; i < idList.size()-1; i++ ){
    				orderPath = orderPath.join(idList.get(i));
    			}
    			orderExpr = orderPath.get(idList.get(idList.size()-1));
			}
		}
		else
		if(idList.get(0).equals(UserLangConsts.k_user)){
			if(idList.size()==1) {
				Join<UserLang, User> title = from.join(UserLang_.user);
				orderExpr = title.get(UserAssist.getOrderKey());
			} else {
    			Join<UserLang, ?> orderPath =  from.join(idList.get(0));
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
			, Path<UserLang> from
			, String value){
		Expression<Boolean> expr = null;
		return expr;	
	}
	
}