package com.inepex.example.ineForm.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ineForm.entity.ContactAddresDetail;
import com.inepex.example.ineForm.entity.dao.query.ContactAddresDetailQuery;
import com.inepex.example.ineForm.entity.mapper.ContactAddresDetailMapper;
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
public class ContactAddresDetailDao extends BaseDao<ContactAddresDetail> {

	public static interface ContactAddresDetailSelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,ContactAddresDetail>> {
	}
	
	@Inject
	public ContactAddresDetailDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory){
		super(em, objectFactory);
	}

	@Override
	public BaseQuery<ContactAddresDetail> getQuery() {
		return new ContactAddresDetailQuery();
	}

	@Override
	public BaseMapper<ContactAddresDetail> getMapper() {
		return new ContactAddresDetailMapper();
	}

	@Override
	public CriteriaSelector<ContactAddresDetail, ContactAddresDetail> getSelector() {
		return new CriteriaSelector<ContactAddresDetail, ContactAddresDetail>(em, getQuery(), ContactAddresDetail.class, ContactAddresDetail.class);
	}

	@Override
	public CriteriaSelector<Long, ContactAddresDetail> getCountSelector() {
		return new CriteriaSelector<Long, ContactAddresDetail>(em, getQuery(), Long.class, ContactAddresDetail.class);
	}

	@Override
	public Class<ContactAddresDetail> getClazz() {
		return ContactAddresDetail.class;
	}

	@Override
	public ContactAddresDetail newInstance() {
		return new ContactAddresDetail();
	}
		
}