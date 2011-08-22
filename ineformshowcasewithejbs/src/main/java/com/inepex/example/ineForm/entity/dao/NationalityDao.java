package com.inepex.example.ineForm.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ineForm.entity.Nationality;
import com.inepex.example.ineForm.entity.dao.query.NationalityQuery;
import com.inepex.example.ineForm.entity.mapper.NationalityMapper;
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
public class NationalityDao extends BaseDao<Nationality> {

	public static interface NationalitySelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,Nationality>> {
	}
	
	@Inject
	public NationalityDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory){
		super(em, objectFactory);
	}

	@Override
	public BaseQuery<Nationality> getQuery() {
		return new NationalityQuery();
	}

	@Override
	public BaseMapper<Nationality> getMapper() {
		return new NationalityMapper();
	}

	@Override
	public CriteriaSelector<Nationality, Nationality> getSelector() {
		return new CriteriaSelector<Nationality, Nationality>(em, getQuery(), Nationality.class, Nationality.class);
	}

	@Override
	public CriteriaSelector<Long, Nationality> getCountSelector() {
		return new CriteriaSelector<Long, Nationality>(em, getQuery(), Long.class, Nationality.class);
	}

	@Override
	public Class<Nationality> getClazz() {
		return Nationality.class;
	}

	@Override
	public Nationality newInstance() {
		return new Nationality();
	}
		
}