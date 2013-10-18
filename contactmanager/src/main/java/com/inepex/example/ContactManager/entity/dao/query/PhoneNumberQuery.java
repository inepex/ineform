package com.inepex.example.ContactManager.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.PhoneNumber;
import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.PhoneNumberType_;
import com.inepex.example.ContactManager.entity.PhoneNumber_;
import com.inepex.example.ContactManager.entity.assist.PhoneNumberTypeAssist;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberConsts;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberHandlerFactory.PhoneNumberSearchHandler;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;
import com.inepex.ineom.shared.util.SharedUtil;

public class PhoneNumberQuery extends BaseQuery<PhoneNumber>{

	private final PhoneNumberHandlerFactory handlerFactory;
	
	@Inject
	public PhoneNumberQuery(DescriptorStore descriptorStore) {
		this.handlerFactory= new PhoneNumberHandlerFactory(descriptorStore);
	}
	
	@Override
	public Expression<Boolean> buildWhere(
		AbstractSearch action
		, CriteriaBuilder cb
		, Root<PhoneNumber> from
		, Expression<Boolean> base){
			
		PhoneNumberSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
		Long id = handler.getLong(PhoneNumberConsts.s_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(PhoneNumber_.id), id));
		String number = handler.getString(PhoneNumberConsts.s_number);
		if (number!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(PhoneNumber_.number)), number.toUpperCase() + "%"));
		Relation type = handler.getRelation(PhoneNumberConsts.s_type);
		if (type!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(PhoneNumber_.type).get(PhoneNumberType_.id), type.getId()));
		return base;
	}
	
	
	@Override
	public Order getOrderExpression(
			AbstractSearch action
			, CriteriaBuilder cb
			, Root<PhoneNumber> from
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
			
		if(idList.get(0).equals(PhoneNumberConsts.k_type)){
			if(idList.size()==1) {
				Join<PhoneNumber, PhoneNumberType> title = from.join(PhoneNumber_.type);
				orderExpr = title.get(PhoneNumberTypeAssist.getOrderKey());
			} else {
    			Join<PhoneNumber, ?> orderPath =  from.join(idList.get(0));
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
			, Path<PhoneNumber> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(PhoneNumber_.number)), value.toUpperCase() + "%"));
		return expr;	
	}
}