package com.inepex.example.ContactManager.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
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
import com.inepex.example.ContactManager.entity.kvo.MeetingConsts;
import com.inepex.example.ContactManager.entity.kvo.MeetingHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.MeetingHandlerFactory.MeetingSearchHandler;
import com.inepex.example.ContactManager.shared.MeetingType;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;
import com.inepex.ineom.shared.util.SharedUtil;

public class MeetingQuery extends BaseQuery<Meeting>{

	private final MeetingHandlerFactory handlerFactory;
	
	@Inject
	public MeetingQuery(DescriptorStore descriptorStore) {
		this.handlerFactory= new MeetingHandlerFactory(descriptorStore);
	}
	
	@Override
	public Expression<Boolean> buildWhere(
		AbstractSearch action
		, CriteriaBuilder cb
		, Root<Meeting> from
		, Expression<Boolean> base){
			
		MeetingSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
		Long id = handler.getLong(MeetingConsts.s_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Meeting_.id), id));
		Long meetingTimestamp = handler.getLong(MeetingConsts.s_meetingTimestamp);
		if (meetingTimestamp!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Meeting_.meetingTimestamp), meetingTimestamp));
		Relation user = handler.getRelation(MeetingConsts.s_user);
		if (user!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Meeting_.user).get(User_.id), user.getId()));
		Relation company = handler.getRelation(MeetingConsts.s_company);
		if (company!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Meeting_.company).get(Company_.id), company.getId()));
		Relation contact = handler.getRelation(MeetingConsts.s_contact);
		if (contact!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Meeting_.contact).get(Contact_.id), contact.getId()));
		Long meetingType = handler.getLong(MeetingConsts.s_meetingType);
		if (meetingType!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Meeting_.meetingType), MeetingType.values()[meetingType.intValue()]));
		
		return base;
	}
	
	
	@Override
	public Order getOrderExpression(
			AbstractSearch action
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
		List<String> idList = SharedUtil.listFromDotSeparated(orderKey);
			
		if(idList.get(0).equals(MeetingConsts.k_user)){
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
		if(idList.get(0).equals(MeetingConsts.k_company)){
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
		if(idList.get(0).equals(MeetingConsts.k_contact)){
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
			, Path<Meeting> from
			, String value){
		Expression<Boolean> expr = null;
		return expr;	
	}
	
}