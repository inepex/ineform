package com.inepex.example.ContactManager.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.Meeting;
import com.inepex.example.ContactManager.entity.dao.query.MeetingQuery;
import com.inepex.example.ContactManager.entity.mapper.MeetingMapper;
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
public class MeetingDao extends BaseDao<Meeting> {

	public static interface MeetingSelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,Meeting>> {
	}
	
	@Inject
	public MeetingDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory){
		super(em, objectFactory);
	}

	@Override
	public BaseQuery<Meeting> getQuery() {
		return new MeetingQuery();
	}

	@Override
	public BaseMapper<Meeting> getMapper() {
		return new MeetingMapper();
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