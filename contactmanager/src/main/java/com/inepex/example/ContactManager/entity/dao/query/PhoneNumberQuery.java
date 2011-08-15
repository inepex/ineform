package com.inepex.example.ContactManager.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.inepex.example.ContactManager.entity.PhoneNumber;
import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.PhoneNumberType_;
import com.inepex.example.ContactManager.entity.PhoneNumber_;
import com.inepex.example.ContactManager.entity.assist.PhoneNumberTypeAssist;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberKVO;
import com.inepex.example.ContactManager.entity.kvo.search.PhoneNumberSearchKVO;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.kvo.Relation;

public class PhoneNumberQuery {

	
	public static Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<PhoneNumber> from
		, Expression<Boolean> base){
		Long id = action.getSearchParameters().getLong(PhoneNumberSearchKVO.k_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(PhoneNumber_.id), id));
		String number = action.getSearchParameters().getString(PhoneNumberSearchKVO.k_number);
		if (number!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(PhoneNumber_.number)), number.toUpperCase() + "%"));
		Relation type = action.getSearchParameters().getRelation(PhoneNumberSearchKVO.k_type);
		if (type!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(PhoneNumber_.type).get(PhoneNumberType_.id), type.getId()));
	return base;
	}
	
	
	public static Order getOrderExpression(
			AbstractSearchAction action
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
		List<String> idList = Node.idToIdList(orderKey);
			
		if(idList.get(0).equals(PhoneNumberKVO.k_type)){
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
		if (action.isDescending())
			o = cb.desc(orderExpr);
		else
			o = cb.asc(orderExpr);
		return o;
	}
	
	public static Expression<Boolean> getSearchExpression(
			CriteriaBuilder cb
			, Path<PhoneNumber> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(PhoneNumber_.number)), value.toUpperCase() + "%"));
		return expr;	
	}

	public static Expression<Boolean> addAndExpression(CriteriaBuilder cb, Expression<Boolean> base, Expression<Boolean> toAdd){
		if (base == null) base = toAdd;
		else base = cb.and(base, toAdd);
		return base;
	}
	
	public static Expression<Boolean> addOrExpression(CriteriaBuilder cb, Expression<Boolean> base, Expression<Boolean> toAdd){
		if (base == null) base = toAdd;
		else base = cb.or(base, toAdd);
		return base;
	}	
}