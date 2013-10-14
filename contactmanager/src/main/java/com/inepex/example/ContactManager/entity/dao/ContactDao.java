package com.inepex.example.ContactManager.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.Contact;
import com.inepex.example.ContactManager.entity.dao.query.ContactQuery;
import com.inepex.example.ContactManager.entity.mapper.ContactMapper;
import com.inepex.ineForm.server.BaseDao;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineForm.server.CriteriaSelector;
import com.inepex.ineForm.server.prop.mongo.PropDao;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineForm.shared.dispatch.ManipulationObjectFactory;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E) or remove(E). (Don't 
 * forget to call super.persist, super.merge ...)
 * 
 */
@Singleton
public class ContactDao extends BaseDao<Contact> {
	
	private final DescriptorStore descStore;
	
	@Inject
	public ContactDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory
		, AssistedObjectHandlerFactory handlerFactory
		, DescriptorStore descStore,
		PropDao propDao){
		super(em, objectFactory, handlerFactory);
		this.descStore=descStore;
		setMongoDao(propDao);
	}

	@Override
	public BaseQuery<Contact> getQuery() {
		return new ContactQuery(descStore);
	}

	@Override
	public BaseMapper<Contact> getMapper() {
		return new ContactMapper(descStore);
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