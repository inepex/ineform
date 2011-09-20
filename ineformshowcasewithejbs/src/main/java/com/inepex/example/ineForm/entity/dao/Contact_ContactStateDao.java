package com.inepex.example.ineForm.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ineForm.entity.Contact_ContactState;
import com.inepex.example.ineForm.entity.dao.query.Contact_ContactStateQuery;
import com.inepex.example.ineForm.entity.mapper.Contact_ContactStateMapper;
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
public class Contact_ContactStateDao extends BaseDao<Contact_ContactState> {

	public static interface Contact_ContactStateSelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,Contact_ContactState>> {
	}
	
	private final DescriptorStore descStore;
	
	@Inject
	public Contact_ContactStateDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory
		, AssistedObjectHandlerFactory handlerFactory
		, DescriptorStore descStore){
		super(em, objectFactory, handlerFactory);
		this.descStore=descStore;
	}

	@Override
	public BaseQuery<Contact_ContactState> getQuery() {
		return new Contact_ContactStateQuery(descStore);
	}

	@Override
	public BaseMapper<Contact_ContactState> getMapper() {
		return new Contact_ContactStateMapper(descStore);
	}

	@Override
	public CriteriaSelector<Contact_ContactState, Contact_ContactState> getSelector() {
		return new CriteriaSelector<Contact_ContactState, Contact_ContactState>(em, getQuery(), Contact_ContactState.class, Contact_ContactState.class);
	}

	@Override
	public CriteriaSelector<Long, Contact_ContactState> getCountSelector() {
		return new CriteriaSelector<Long, Contact_ContactState>(em, getQuery(), Long.class, Contact_ContactState.class);
	}

	@Override
	public Class<Contact_ContactState> getClazz() {
		return Contact_ContactState.class;
	}

	@Override
	public Contact_ContactState newInstance() {
		return new Contact_ContactState();
	}
		
}