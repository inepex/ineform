package com.inepex.example.ContactManager.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.dao.query.PhoneNumberTypeQuery;
import com.inepex.example.ContactManager.entity.mapper.PhoneNumberTypeMapper;
import com.inepex.ineForm.server.BaseDao;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineForm.server.CriteriaSelector;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineForm.shared.dispatch.ManipulationObjectFactory;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E)
 * or remove(E). (Don't forget to call super.persist, super.merge ...)
 * 
 */
@Singleton
public class PhoneNumberTypeDao extends BaseDao<PhoneNumberType> {

    private final DescriptorStore descStore;

    @Inject
    public PhoneNumberTypeDao(
        Provider<EntityManager> em,
        ManipulationObjectFactory objectFactory,
        AssistedObjectHandlerFactory handlerFactory,
        DescriptorStore descStore) {
        super(em, objectFactory, handlerFactory);
        this.descStore = descStore;
    }

    @Override
    public BaseQuery<PhoneNumberType> getQuery() {
        return new PhoneNumberTypeQuery(descStore);
    }

    @Override
    public BaseMapper<PhoneNumberType> getMapper() {
        return new PhoneNumberTypeMapper(descStore);
    }

    @Override
    public CriteriaSelector<PhoneNumberType, PhoneNumberType> getSelector() {
        return new CriteriaSelector<PhoneNumberType, PhoneNumberType>(
            em,
            getQuery(),
            PhoneNumberType.class,
            PhoneNumberType.class);
    }

    @Override
    public CriteriaSelector<Long, PhoneNumberType> getCountSelector() {
        return new CriteriaSelector<Long, PhoneNumberType>(
            em,
            getQuery(),
            Long.class,
            PhoneNumberType.class);
    }

    @Override
    public Class<PhoneNumberType> getClazz() {
        return PhoneNumberType.class;
    }

    @Override
    public PhoneNumberType newInstance() {
        return new PhoneNumberType();
    }

}