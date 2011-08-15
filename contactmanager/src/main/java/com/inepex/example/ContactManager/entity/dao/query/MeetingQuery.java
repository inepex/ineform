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
import com.inepex.example.ContactManager.entity.Meeting;
import com.inepex.example.ContactManager.entity.Meeting_;
import com.inepex.example.ContactManager.entity.User;
import com.inepex.example.ContactManager.entity.User_;
import com.inepex.example.ContactManager.entity.assist.CompanyAssist;
import com.inepex.example.ContactManager.entity.assist.ContactAssist;
import com.inepex.example.ContactManager.entity.assist.UserAssist;
import com.inepex.example.ContactManager.entity.kvo.MeetingKVO;
import com.inepex.example.ContactManager.entity.kvo.search.MeetingSearchKVO;
import com.inepex.example.ContactManager.shared.MeetingType;
import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.kvo.Relation;

public class MeetingQuery {

	
	public static Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<Meeting> from
		, Expression<Boolean> base){
		Long id = action.getSearchParameters().getLong(MeetingSearchKVO.k_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Meeting_.id), id));
		Long meetingTimestamp = action.getSearchParameters().getLong(MeetingSearchKVO.k_meetingTimestamp);
		if (meetingTimestamp!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Meeting_.meetingTimestamp), meetingTimestamp));
		Relation user = action.getSearchParameters().getRelation(MeetingSearchKVO.k_user);
		if (user!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Meeting_.user).get(User_.id), user.getId()));
		Relation company = action.getSearchParameters().getRelation(MeetingSearchKVO.k_company);
		if (company!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Meeting_.company).get(Company_.id), company.getId()));
		Relation contact = action.getSearchParameters().getRelation(MeetingSearchKVO.k_contact);
		if (contact!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Meeting_.contact).get(Contact_.id), contact.getId()));
		Long meetingType = action.getSearchParameters().getLong(MeetingSearchKVO.k_meetingType);
		if (meetingType!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Meeting_.meetingType), MeetingType.values()[meetingType.intValue()]));
	return base;
	}
	
	
	public static Order getOrderExpression(
			AbstractSearchAction action
			, CriteriaBuilder cb
			, Root<Meeting> from
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
			
		if(idList.get(0).equals(MeetingKVO.k_user)){
			if(idList.size()==1) {
				Join<Meeting, User> title = from.join(Meeting_.user);
				orderExpr = title.get(UserAssist.getOrderKey());
			} else {
    			Join<Meeting, ?> orderPath =  from.join(idList.get(0));
    			for (int i = 1; i < idList.size()-1; i++ ){
    				orderPath = orderPath.join(idList.get(i));
    			}
    			orderExpr = orderPath.get(idList.get(idList.size()-1));
			}
		}
		else
		if(idList.get(0).equals(MeetingKVO.k_company)){
			if(idList.size()==1) {
				Join<Meeting, Company> title = from.join(Meeting_.company);
				orderExpr = title.get(CompanyAssist.getOrderKey());
			} else {
    			Join<Meeting, ?> orderPath =  from.join(idList.get(0));
    			for (int i = 1; i < idList.size()-1; i++ ){
    				orderPath = orderPath.join(idList.get(i));
    			}
    			orderExpr = orderPath.get(idList.get(idList.size()-1));
			}
		}
		else
		if(idList.get(0).equals(MeetingKVO.k_contact)){
			if(idList.size()==1) {
				Join<Meeting, Contact> title = from.join(Meeting_.contact);
				orderExpr = title.get(ContactAssist.getOrderKey());
			} else {
    			Join<Meeting, ?> orderPath =  from.join(idList.get(0));
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
			, Path<Meeting> from
			, String value){
		Expression<Boolean> expr = null;
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