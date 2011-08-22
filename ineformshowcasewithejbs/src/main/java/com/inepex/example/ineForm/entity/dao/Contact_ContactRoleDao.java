package com.inepex.example.ineForm.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ineForm.entity.Contact_ContactRole;
import com.inepex.example.ineForm.entity.dao.query.Contact_ContactRoleQuery;
import com.inepex.example.ineForm.entity.mapper.Contact_ContactRoleMapper;
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
public class Contact_ContactRoleDao extends BaseDao<Contact_ContactRole> {

	public static interface Contact_ContactRoleSelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,Contact_ContactRole>> {
	}
	
	@Inject
	public Contact_ContactRoleDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory){
		super(em, objectFactory);
	}

	@Override
	public BaseQuery<Contact_ContactRole> getQuery() {
		return new Contact_ContactRoleQuery();
	}

	@Override
	public BaseMapper<Contact_ContactRole> getMapper() {
		return new Contact_ContactRoleMapper();
	}

	@Override
	public CriteriaSelector<Contact_ContactRole, Contact_ContactRole> getSelector() {
		return new CriteriaSelector<Contact_ContactRole, Contact_ContactRole>(em, getQuery(), Contact_ContactRole.class, Contact_ContactRole.class);
	}

	@Override
	public CriteriaSelector<Long, Contact_ContactRole> getCountSelector() {
		return new CriteriaSelector<Long, Contact_ContactRole>(em, getQuery(), Long.class, Contact_ContactRole.class);
	}

	@Override
	public Class<Contact_ContactRole> getClazz() {
		return Contact_ContactRole.class;
	}

	@Override
	public Contact_ContactRole newInstance() {
		return new Contact_ContactRole();
	}
		
}