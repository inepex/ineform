package com.inepex.ineForm.server.customkvo;

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

/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E) or remove(E). (Don't 
 * forget to call super.persist, super.merge ...)
 * 
 */
@Singleton
public class CustomKVODao extends BaseDao<CustomKVO> {
	
	@Inject
	public CustomKVODao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory
		, AssistedObjectHandlerFactory handlerFactory){
		super(em, objectFactory, handlerFactory);
	}

	@Override
	public Class<CustomKVO> getClazz() {
		return CustomKVO.class;
	}
	
//-------------------------------------
// unsupported stuffs
//
//-------------------------------------
	@Override
	public BaseQuery<CustomKVO> getQuery() {
		throw new UnsupportedOperationException();
	}

	@Override
	public BaseMapper<CustomKVO> getMapper() {
		throw new UnsupportedOperationException();
	}

	@Override
	public CriteriaSelector<CustomKVO, CustomKVO> getSelector() {
		throw new UnsupportedOperationException();
	}

	@Override
	public CriteriaSelector<Long, CustomKVO> getCountSelector() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public CustomKVO newInstance() {
		throw new UnsupportedOperationException();
	}

}