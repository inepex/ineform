package com.inepex.example.ContactManager.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.EmailAddress;
import com.inepex.example.ContactManager.entity.dao.query.EmailAddressQuery;
import com.inepex.example.ContactManager.entity.mapper.EmailAddressMapper;
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
public class EmailAddressDao extends BaseDao<EmailAddress> {

	public static interface EmailAddressSelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,EmailAddress>> {
	}
	
	@Inject
	public EmailAddressDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory){
		super(em, objectFactory);
	}

	@Override
	public BaseQuery<EmailAddress> getQuery() {
		return new EmailAddressQuery();
	}

	@Override
	public BaseMapper<EmailAddress> getMapper() {
		return new EmailAddressMapper();
	}

	@Override
	public CriteriaSelector<EmailAddress, EmailAddress> getSelector() {
		return new CriteriaSelector<EmailAddress, EmailAddress>(em, getQuery(), EmailAddress.class, EmailAddress.class);
	}

	@Override
	public CriteriaSelector<Long, EmailAddress> getCountSelector() {
		return new CriteriaSelector<Long, EmailAddress>(em, getQuery(), Long.class, EmailAddress.class);
	}

	@Override
	public Class<EmailAddress> getClazz() {
		return EmailAddress.class;
	}

	@Override
	public EmailAddress newInstance() {
		return new EmailAddress();
	}
		
}