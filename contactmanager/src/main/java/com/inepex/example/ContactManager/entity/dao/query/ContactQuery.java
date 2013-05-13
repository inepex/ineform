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
import com.inepex.example.ContactManager.entity.assist.CompanyAssist;
import com.inepex.example.ContactManager.entity.kvo.ContactConsts;
import com.inepex.example.ContactManager.entity.kvo.ContactHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.ContactHandlerFactory.ContactSearchHandler;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;

public class ContactQuery extends BaseQuery<Contact>{

	private final ContactHandlerFactory handlerFactory;
	
	@Inject
	public ContactQuery(DescriptorStore descriptorStore) {
		this.handlerFactory= new ContactHandlerFactory(descriptorStore);
	}
	
	@Override
	public Expression<Boolean> buildWhere(
		AbstractSearch action
		, CriteriaBuilder cb
		, Root<Contact> from
		, Expression<Boolean> base){
			
		ContactSearchHandler handler = handlerFactory.createSearchHandler(action.getSearchParameters());
		Long id = handler.getLong(ContactConsts.s_id);
		if (id!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Contact_.id), id));
		String name = handler.getString(ContactConsts.s_name);
		if (name!=null)
			base = addAndExpression(cb, base, cb.like(cb.upper(from.get(Contact_.name)), name.toUpperCase() + "%"));
		Relation company = handler.getRelation(ContactConsts.s_company);
		if (company!=null)
			base = addAndExpression(cb, base, cb.equal(from.get(Contact_.company).get(Company_.id), company.getId()));
	return base;
	}
	
	
	@Override
	public Order getOrderExpression(
			AbstractSearch action
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
			
		if(idList.get(0).equals(ContactConsts.k_company)){
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
			, Path<Contact> from
			, String value){
		Expression<Boolean> expr = null;
		expr = addOrExpression(cb, expr, 
				cb.like(cb.upper(from.get(Contact_.name)), value.toUpperCase() + "%"));
		return expr;	
	}
	
}