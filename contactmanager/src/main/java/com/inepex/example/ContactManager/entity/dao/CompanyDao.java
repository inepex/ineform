package com.inepex.example.ContactManager.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.Company;
import com.inepex.example.ContactManager.entity.dao.query.CompanyQuery;
import com.inepex.example.ContactManager.entity.mapper.CompanyMapper;
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
public class CompanyDao extends BaseDao<Company> {
	
	private final DescriptorStore descStore;
	
	@Inject
	public CompanyDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory
		, AssistedObjectHandlerFactory handlerFactory
		, DescriptorStore descStore,
		PropDao propDao){
		super(em, objectFactory, handlerFactory);
		setMongoDao(propDao);
		this.descStore=descStore;
	}

	@Override
	public BaseQuery<Company> getQuery() {
		return new CompanyQuery(descStore);
	}

	@Override
	public BaseMapper<Company> getMapper() {
		return new CompanyMapper(descStore);
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