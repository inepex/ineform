package com.inepex.translatorapp.server.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineForm.server.BaseDao;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineForm.server.CriteriaSelector;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineForm.shared.dispatch.ManipulationObjectFactory;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.server.entity.Module;
import com.inepex.translatorapp.server.entity.dao.query.ModuleQuery;
import com.inepex.translatorapp.server.entity.mapper.ModuleMapper;

/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E) or remove(E). (Don't 
 * forget to call super.persist, super.merge ...)
 * 
 */
@Singleton
public class ModuleDao extends BaseDao<Module> {
	
	private final DescriptorStore descStore;
	
	@Inject
	public ModuleDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory
		, AssistedObjectHandlerFactory handlerFactory
		, DescriptorStore descStore){
		super(em, objectFactory, handlerFactory);
		this.descStore=descStore;
	}

	@Override
	public BaseQuery<Module> getQuery() {
		return new ModuleQuery(descStore);
	}

	@Override
	public BaseMapper<Module> getMapper() {
		return new ModuleMapper(descStore);
	}

	@Override
	public CriteriaSelector<Module, Module> getSelector() {
		return new CriteriaSelector<Module, Module>(em, getQuery(), Module.class, Module.class);
	}

	@Override
	public CriteriaSelector<Long, Module> getCountSelector() {
		return new CriteriaSelector<Long, Module>(em, getQuery(), Long.class, Module.class);
	}

	@Override
	public Class<Module> getClazz() {
		return Module.class;
	}

	@Override
	public Module newInstance() {
		return new Module();
	}
	
	/*hc:customMethods*/
	//overrides and custom methods
	/*hc*/
		
}