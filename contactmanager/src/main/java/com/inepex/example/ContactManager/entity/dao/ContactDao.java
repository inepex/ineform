package com.inepex.example.ContactManager.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.Contact;
import com.inepex.example.ContactManager.entity.dao.query.ContactQuery;
import com.inepex.example.ContactManager.entity.mapper.ContactMapper;
import com.inepex.ineForm.server.BaseDao;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineForm.server.CriteriaSelector;
import com.inepex.ineForm.server.SelectorCustomizer;
import com.inepex.ineForm.shared.dispatch.ManipulationObjectFactory;


@Singleton
/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E) or remove(E). (Don't 
 * forget to call super.persist, super.merge ...)
 * 
 */
public class ContactDao extends BaseDao<Contact> {

	public static interface ContactSelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,Contact>> {
	}
	
	@Inject
	public ContactDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory){
		super(em, objectFactory);
	}

	@Override
	public BaseQuery<Contact> getQuery() {
		return new ContactQuery();
	}

	@Override
	public BaseMapper<Contact> getMapper() {
		return new ContactMapper();
	}

	@Override
	public CriteriaSelector<Contact, Contact> getSelector() {
		return new CriteriaSelector<Contact, Contact>(em, getQuery(), Contact.class, Contact.class);
	}

	@Override
	public CriteriaSelector<Long, Contact> getCountSelector() {
		return new CriteriaSelector<Long, Contact>(em, getQuery(), Long.class, Contact.class);
	}

	@Override
	public Class<Contact> getClazz() {
		return Contact.class;
	}

	@Override
	public Contact newInstance() {
		return new Contact();
	}
		
}