package com.inepex.example.ineForm.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.dao.query.ContactTypeQuery;
import com.inepex.example.ineForm.entity.mapper.ContactTypeMapper;
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
public class ContactTypeDao extends BaseDao<ContactType> {

	public static interface ContactTypeSelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,ContactType>> {
	}
	
	@Inject
	public ContactTypeDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory){
		super(em, objectFactory);
	}

	@Override
	public BaseQuery<ContactType> getQuery() {
		return new ContactTypeQuery();
	}

	@Override
	public BaseMapper<ContactType> getMapper() {
		return new ContactTypeMapper();
	}

	@Override
	public CriteriaSelector<ContactType, ContactType> getSelector() {
		return new CriteriaSelector<ContactType, ContactType>(em, getQuery(), ContactType.class, ContactType.class);
	}

	@Override
	public CriteriaSelector<Long, ContactType> getCountSelector() {
		return new CriteriaSelector<Long, ContactType>(em, getQuery(), Long.class, ContactType.class);
	}

	@Override
	public Class<ContactType> getClazz() {
		return ContactType.class;
	}

	@Override
	public ContactType newInstance() {
		return new ContactType();
	}
		
}