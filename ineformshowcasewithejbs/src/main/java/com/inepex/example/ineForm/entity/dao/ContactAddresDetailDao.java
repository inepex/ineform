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
public class ContactAddresDetailDao extends BaseDao<ContactAddresDetail> {

	public static interface ContactAddresDetailSelectorCustomizer extends SelectorCustomizer<CriteriaSelector<?,ContactAddresDetail>> {
	}
	
	private final DescriptorStore descStore;
	
	@Inject
	public ContactAddresDetailDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory
		, AssistedObjectHandlerFactory handlerFactory
		, DescriptorStore descStore){
		super(em, objectFactory, handlerFactory);
		this.descStore=descStore;
	}

	@Override
	public BaseQuery<ContactAddresDetail> getQuery() {
		return new ContactAddresDetailQuery(descStore);
	}

	@Override
	public BaseMapper<ContactAddresDetail> getMapper() {
		return new ContactAddresDetailMapper(descStore);
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