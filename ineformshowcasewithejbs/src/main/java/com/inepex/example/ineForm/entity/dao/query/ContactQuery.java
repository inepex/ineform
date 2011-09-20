package com.inepex.example.ineForm.entity.dao.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.Contact;
import com.inepex.example.ineForm.entity.ContactAddresDetail;
import com.inepex.example.ineForm.entity.assist.ContactAddresDetailAssist;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelConsts;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelHandlerFactory.ContactCTypeRelHandler;
import com.inepex.example.ineForm.entity.kvo.ContactConsts;
import com.inepex.example.ineForm.entity.kvo.ContactHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.ContactHandlerFactory.ContactSearchHandler;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelConsts;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelHandlerFactory.ContactNatRelHandler;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleConsts;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleHandlerFactory.Contact_ContactRoleHandler;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateConsts;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateHandlerFactory.Contact_ContactStateHandler;
import com.inepex.example.ineForm.entity.metaentity.ContactCTypeRel_;
import com.inepex.example.ineForm.entity.metaentity.ContactNatRel_;
import com.inepex.example.ineForm.entity.metaentity.Contact_;
import com.inepex.example.ineForm.entity.metaentity.Contact_ContactRole_;
import com.inepex.example.ineForm.entity.metaentity.Contact_ContactState_;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;

public class ContactQuery extends BaseQuery<Contact>{

	private final ContactHandlerFactory handlerFactory;
	
	private final DescriptorStore descriptorStore;
	
	@Inject
	public ContactQuery(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
		this.handlerFactory= new ContactHandlerFactory(descriptorStore);
	}
	
	public Expression<Boolean> buildWhere(
		AbstractSearchAction action
		, CriteriaBuilder cb
		, Root<Contact> from
		, Expression<Boolean> base){
			
		ContactSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
		String firstName = handler.getString(ContactConsts.s_firstName);
		if (firstName!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Contact_.firstName)), firstName.toUpperCase() + "%"));
		String lastName = handler.getString(ContactConsts.s_lastName);
		if (lastName!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Contact_.lastName)), lastName.toUpperCase() + "%"));
		IneList contactTypes = handler.getList(ContactConsts.s_contactTypes);
		if (contactTypes != null && contactTypes.getRelationList().size() > 0){
			List<Long> relationIds = new ArrayList<Long>();
			for (Relation r : contactTypes.getRelationList()){
				ContactCTypeRelHandler relKvoHandler = new ContactCTypeRelHandlerFactory(descriptorStore).createHandler(r.getKvo());
				if (relKvoHandler.getRelation(ContactCTypeRelConsts.s_contactType) != null){
					relationIds.add(relKvoHandler.getRelation(ContactCTypeRelConsts.s_contactType).getId());
				}
			}
			if (relationIds.size()>0){
				base = addAndExpression(cb, base, (from.join(Contact_.contactTypes).get(ContactCTypeRel_.contactType).get("id")).in(relationIds));
			}
		}
		IneList nationalities = handler.getList(ContactConsts.s_nationalities);
		if (nationalities != null && nationalities.getRelationList().size() > 0){
			List<Long> relationIds = new ArrayList<Long>();
			for (Relation r : nationalities.getRelationList()){
				ContactNatRelHandler relKvoHandler = new ContactNatRelHandlerFactory(descriptorStore).createHandler(r.getKvo());
				if (relKvoHandler.getRelation(ContactNatRelConsts.s_nationality) != null){
					relationIds.add(relKvoHandler.getRelation(ContactNatRelConsts.s_nationality).getId());
				}
			}
			if (relationIds.size()>0){
				base = addAndExpression(cb, base, (from.join(Contact_.nationalities).get(ContactNatRel_.nationality).get("id")).in(relationIds));
			}
		}
		Boolean happy = handler.getBoolean(ContactConsts.s_happy);
		if (Boolean.TRUE.equals(happy))
			base = addAndExpression(cb, base, cb.equal(from.get(Contact_.happy), happy));
		IneList roles = handler.getList(ContactConsts.s_roles);
		if (roles != null && roles.getRelationList().size() > 0){
			List<Long> relationIds = new ArrayList<Long>();
			for (Relation r : roles.getRelationList()){
				Contact_ContactRoleHandler relKvoHandler = new Contact_ContactRoleHandlerFactory(descriptorStore).createHandler(r.getKvo());
				if (relKvoHandler.getRelation(Contact_ContactRoleConsts.s_role) != null){
					relationIds.add(relKvoHandler.getRelation(Contact_ContactRoleConsts.s_role).getId());
				}
			}
			if (relationIds.size()>0){
				base = addAndExpression(cb, base, (from.join(Contact_.roles).get(Contact_ContactRole_.role).get("id")).in(relationIds));
			}
		}
		IneList states = handler.getList(ContactConsts.s_states);
		if (states != null && states.getRelationList().size() > 0){
			List<Long> relationIds = new ArrayList<Long>();
			for (Relation r : states.getRelationList()){
				Contact_ContactStateHandler relKvoHandler = new Contact_ContactStateHandlerFactory(descriptorStore).createHandler(r.getKvo());
				if (relKvoHandler.getRelation(Contact_ContactStateConsts.s_state) != null){
					relationIds.add(relKvoHandler.getRelation(Contact_ContactStateConsts.s_state).getId());
				}
			}
			if (relationIds.size()>0){
				base = addAndExpression(cb, base, (from.join(Contact_.states).get(Contact_ContactState_.state).get("id")).in(relationIds));
			}
		}
	return base;
	}
	
	
	public Order getOrderExpression(
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
			orderKey = ContactConsts.k_firstName;		
		}
		Expression<?> orderExpr = null;
		List<String> idList = Node.idToIdList(orderKey);
			
		if(idList.get(0).equals(ContactConsts.k_addressDetail)){
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
		if (action.isDescending() == null)
			//default order
			o = cb.asc(orderExpr);
		else if (action.isDescending())
			o = cb.desc(orderExpr);
		else
			o = cb.asc(orderExpr);
		return o;
	}
	
	public Expression<Boolean> getSearchExpression(
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
	
}