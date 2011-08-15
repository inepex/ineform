package com.inepex.example.ContactManager.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.inepex.example.ContactManager.entity.Company;
import com.inepex.example.ContactManager.entity.Company_;
import com.inepex.example.ContactManager.entity.Contact;
import com.inepex.example.ContactManager.entity.Contact_;
import com.inepex.example.ContactManager.entity.assist.CompanyAssist;
import com.inepex.example.ContactManager.entity.kvo.ContactKVO;
import com.inepex.example.ContactManager.entity.kvo.search.ContactSearchKVO;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactQuery {

	
	public static Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<Contact> from
		, Expression<Boolean> base){
		Long id = action.getSearchParameters().getLong(ContactSearchKVO.k_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Contact_.id), id));
		String name = action.getSearchParameters().getString(ContactSearchKVO.k_name);
		if (name!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Contact_.name)), name.toUpperCase() + "%"));
		Relation company = action.getSearchParameters().getRelation(ContactSearchKVO.k_company);
		if (company!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Contact_.company).get(Company_.id), company.getId()));
	return base;
	}
	
	
	public static Order getOrderExpression(
			AbstractSearchAction action
			, CriteriaBuilder cb
			, Root<Contact> from
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
			
		if(idList.get(0).equals(ContactKVO.k_company)){
			if(idList.size()==1) {
				Join<Contact, Company> title = from.join(Contact_.company);
				orderExpr = title.get(CompanyAssist.getOrderKey());
			} else {
    			Join<Contact, ?> orderPath =  from.join(idList.get(0));
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
			, Path<Contact> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(Contact_.name)), value.toUpperCase() + "%"));
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