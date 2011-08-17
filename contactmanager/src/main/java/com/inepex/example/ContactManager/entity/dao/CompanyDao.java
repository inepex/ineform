package com.inepex.example.ContactManager.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.Company;
import com.inepex.example.ContactManager.entity.dao.query.CompanyQuery;
import com.inepex.example.ContactManager.entity.mapper.CompanyMapper;
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
public class CompanyDao extends BaseDao<Company> {

	public static interface CompanySelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,Company>> {
	}
	
	@Inject
	public CompanyDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory){
		super(em, objectFactory);
	}

	@Override
	public BaseQuery<Company> getQuery() {
		return new CompanyQuery();
	}

	@Override
	public BaseMapper<Company> getMapper() {
		return new CompanyMapper();
	}

	@Override
	public CriteriaSelector<Company, Company> getSelector() {
		return new CriteriaSelector<Company, Company>(em, getQuery(), Company.class, Company.class);
	}

	@Override
	public CriteriaSelector<Long, Company> getCountSelector() {
		return new CriteriaSelector<Long, Company>(em, getQuery(), Long.class, Company.class);
	}

	@Override
	public Class<Company> getClazz() {
		return Company.class;
	}

	@Override
	public Company newInstance() {
		return new Company();
	}
		
}