package com.inepex.example.ineForm.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ineForm.entity.ContactCTypeRel;
import com.inepex.example.ineForm.entity.dao.query.ContactCTypeRelQuery;
import com.inepex.example.ineForm.entity.mapper.ContactCTypeRelMapper;
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
public class ContactCTypeRelDao extends BaseDao<ContactCTypeRel> {

	public static interface ContactCTypeRelSelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,ContactCTypeRel>> {
	}
	
	@Inject
	public ContactCTypeRelDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory){
		super(em, objectFactory);
	}

	@Override
	public BaseQuery<ContactCTypeRel> getQuery() {
		return new ContactCTypeRelQuery();
	}

	@Override
	public BaseMapper<ContactCTypeRel> getMapper() {
		return new ContactCTypeRelMapper();
	}

	@Override
	public CriteriaSelector<ContactCTypeRel, ContactCTypeRel> getSelector() {
		return new CriteriaSelector<ContactCTypeRel, ContactCTypeRel>(em, getQuery(), ContactCTypeRel.class, ContactCTypeRel.class);
	}

	@Override
	public CriteriaSelector<Long, ContactCTypeRel> getCountSelector() {
		return new CriteriaSelector<Long, ContactCTypeRel>(em, getQuery(), Long.class, ContactCTypeRel.class);
	}

	@Override
	public Class<ContactCTypeRel> getClazz() {
		return ContactCTypeRel.class;
	}

	@Override
	public ContactCTypeRel newInstance() {
		return new ContactCTypeRel();
	}
		
}