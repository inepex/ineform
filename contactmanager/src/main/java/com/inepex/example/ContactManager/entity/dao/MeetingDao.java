package com.inepex.example.ContactManager.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.Meeting;
import com.inepex.example.ContactManager.entity.dao.query.MeetingQuery;
import com.inepex.example.ContactManager.entity.mapper.MeetingMapper;
import com.inepex.ineForm.server.BaseDao;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineForm.server.CriteriaSelector;
import com.inepex.ineForm.server.SelectorCustomizer;
import com.inepex.ineForm.shared.BaseMapper;
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
public class MeetingDao extends BaseDao<Meeting> {

	public static interface MeetingSelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,Meeting>> {
	}
	
	private final DescriptorStore descStore;
	
	@Inject
	public MeetingDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory
		, AssistedObjectHandlerFactory handlerFactory
		, DescriptorStore descStore){
		super(em, objectFactory, handlerFactory);
		this.descStore=descStore;
	}

	@Override
	public BaseQuery<Meeting> getQuery() {
		return new MeetingQuery(descStore);
	}

	@Override
	public BaseMapper<Meeting> getMapper() {
		return new MeetingMapper(descStore);
	}

	@Override
	public CriteriaSelector<Meeting, Meeting> getSelector() {
		return new CriteriaSelector<Meeting, Meeting>(em, getQuery(), Meeting.class, Meeting.class);
	}

	@Override
	public CriteriaSelector<Long, Meeting> getCountSelector() {
		return new CriteriaSelector<Long, Meeting>(em, getQuery(), Long.class, Meeting.class);
	}

	@Override
	public Class<Meeting> getClazz() {
		return Meeting.class;
	}

	@Override
	public Meeting newInstance() {
		return new Meeting();
	}
		
}