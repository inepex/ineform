package com.inepex.example.ContactManager.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.PhoneNumber;
import com.inepex.example.ContactManager.entity.dao.query.PhoneNumberQuery;
import com.inepex.example.ContactManager.entity.mapper.PhoneNumberMapper;
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
public class PhoneNumberDao extends BaseDao<PhoneNumber> {

	public static interface PhoneNumberSelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,PhoneNumber>> {
	}
	
	@Inject
	public PhoneNumberDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory){
		super(em, objectFactory);
	}

	@Override
	public BaseQuery<PhoneNumber> getQuery() {
		return new PhoneNumberQuery();
	}

	@Override
	public BaseMapper<PhoneNumber> getMapper() {
		return new PhoneNumberMapper();
	}

	@Override
	public CriteriaSelector<PhoneNumber, PhoneNumber> getSelector() {
		return new CriteriaSelector<PhoneNumber, PhoneNumber>(em, getQuery(), PhoneNumber.class, PhoneNumber.class);
	}

	@Override
	public CriteriaSelector<Long, PhoneNumber> getCountSelector() {
		return new CriteriaSelector<Long, PhoneNumber>(em, getQuery(), Long.class, PhoneNumber.class);
	}

	@Override
	public Class<PhoneNumber> getClazz() {
		return PhoneNumber.class;
	}

	@Override
	public PhoneNumber newInstance() {
		return new PhoneNumber();
	}
		
}