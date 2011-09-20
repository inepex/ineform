package com.inepex.example.ineForm.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ineForm.entity.ContactNatRel;
import com.inepex.example.ineForm.entity.dao.query.ContactNatRelQuery;
import com.inepex.example.ineForm.entity.mapper.ContactNatRelMapper;
import com.inepex.ineForm.server.BaseDao;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineForm.server.CriteriaSelector;
import com.inepex.ineForm.server.SelectorCustomizer;
import com.inepex.ineForm.shared.dispatch.ManipulationObjectFactory;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E) or remove(E). (Don't 
 * forget to call super.persist, super.merge ...)
 * 
 */
@Singleton
public class ContactNatRelDao extends BaseDao<ContactNatRel> {

	public static interface ContactNatRelSelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,ContactNatRel>> {
	}
	
	private final DescriptorStore descStore;
	
	@Inject
	public ContactNatRelDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory
		, AssistedObjectHandlerFactory handlerFactory
		, DescriptorStore descStore){
		super(em, objectFactory, handlerFactory);
		this.descStore=descStore;
	}

	@Override
	public BaseQuery<ContactNatRel> getQuery() {
		return new ContactNatRelQuery(descStore);
	}

	@Override
	public BaseMapper<ContactNatRel> getMapper() {
		return new ContactNatRelMapper(descStore);
	}

	@Override
	public CriteriaSelector<ContactNatRel, ContactNatRel> getSelector() {
		return new CriteriaSelector<ContactNatRel, ContactNatRel>(em, getQuery(), ContactNatRel.class, ContactNatRel.class);
	}

	@Override
	public CriteriaSelector<Long, ContactNatRel> getCountSelector() {
		return new CriteriaSelector<Long, ContactNatRel>(em, getQuery(), Long.class, ContactNatRel.class);
	}

	@Override
	public Class<ContactNatRel> getClazz() {
		return ContactNatRel.class;
	}

	@Override
	public ContactNatRel newInstance() {
		return new ContactNatRel();
	}
		
}