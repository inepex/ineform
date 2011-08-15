package com.inepex.example.ineForm.entity.dao.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.inepex.example.ineForm.entity.Contact;
import com.inepex.example.ineForm.entity.ContactAddresDetail;
import com.inepex.example.ineForm.entity.assist.ContactAddresDetailAssist;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelKVO;
import com.inepex.example.ineForm.entity.kvo.ContactKVO;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelKVO;
import com.inepex.example.ineForm.entity.kvo.search.ContactSearchKVO;
import com.inepex.example.ineForm.entity.metaentity.ContactCTypeRel_;
import com.inepex.example.ineForm.entity.metaentity.ContactNatRel_;
import com.inepex.example.ineForm.entity.metaentity.Contact_;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.kvo.IneList;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactQuery {

	
	public static Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<Contact> from
		, Expression<Boolean> base){
		String firstName = action.getSearchParameters().getString(ContactSearchKVO.k_firstName);
		if (firstName!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Contact_.firstName)), firstName.toUpperCase() + "%"));
		String lastName = action.getSearchParameters().getString(ContactSearchKVO.k_lastName);
		if (lastName!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Contact_.lastName)), lastName.toUpperCase() + "%"));
		IneList contactTypes = action.getSearchParameters().getList(ContactKVO.k_contactTypes);
		if (contactTypes != null && contactTypes.getRelationList().size() > 0){
			List<Long> relationIds = new ArrayList<Long>();
			for (Relation r : contactTypes.getRelationList()){
				if (r.getKvo().getRelation(ContactCTypeRelKVO.k_contactType) != null){
					relationIds.add(r.getKvo().getRelation(ContactCTypeRelKVO.k_contactType).getId());
				}
			}
			if (relationIds.size()>0){
				base = addAndExpression(cb, base, (from.join(Contact_.contactTypes).get(ContactCTypeRel_.contactType).get("id")).in(relationIds));
			}
		}
		IneList nationalities = action.getSearchParameters().getList(ContactKVO.k_nationalities);
		if (nationalities != null && nationalities.getRelationList().size() > 0){
			List<Long> relationIds = new ArrayList<Long>();
			for (Relation r : nationalities.getRelationList()){
				if (r.getKvo().getRelation(ContactNatRelKVO.k_nationality) != null){
					relationIds.add(r.getKvo().getRelation(ContactNatRelKVO.k_nationality).getId());
				}
			}
			if (relationIds.size()>0){
				base = addAndExpression(cb, base, (from.join(Contact_.nationalities).get(ContactNatRel_.nationality).get("id")).in(relationIds));
			}
		}
		Boolean happy = action.getSearchParameters().getBoolean(ContactSearchKVO.k_happy);
		if (happy!=false)
			base = addAndExpression(cb, base, cb.equal(from.get(Contact_.happy), happy));
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
			orderKey = ContactKVO.k_firstName;		
		}
		Expression<?> orderExpr = null;
		List<String> idList = Node.idToIdList(orderKey);
			
		if(idList.get(0).equals(ContactKVO.k_addressDetail)){
			if(idList.size()==1) {
				Join<Contact, ContactAddresDetail> title = from.join(Contact_.addressDetail);
				orderExpr = title.get(ContactAddresDetailAssist.getOrderKey());
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
				cb.like(cb.upper(from.get(Contact_.firstName)), value.toUpperCase() + "%"));
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(Contact_.lastName)), value.toUpperCase() + "%"));
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
