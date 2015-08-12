package com.inepex.example.ContactManager.entity.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.EmailAddress;
import com.inepex.example.ContactManager.entity.dao.query.EmailAddressQuery;
import com.inepex.example.ContactManager.entity.mapper.EmailAddressMapper;
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
public class EmailAddressDao extends BaseDao<EmailAddress> {

    private final DescriptorStore descStore;

    @Inject
    public EmailAddressDao(
        Provider<EntityManager> em,
        ManipulationObjectFactory objectFactory,
        AssistedObjectHandlerFactory handlerFactory,
        DescriptorStore descStore) {
        super(em, objectFactory, handlerFactory);
        this.descStore = descStore;
    }

    @Override
    public BaseQuery<EmailAddress> getQuery() {
        return new EmailAddressQuery(descStore);
    }

    @Override
    public BaseMapper<EmailAddress> getMapper() {
        return new EmailAddressMapper(descStore);
    }

    @Override
    public CriteriaSelector<EmailAddress, EmailAddress> getSelector() {
        return new CriteriaSelector<EmailAddress, EmailAddress>(
            em,
            getQuery(),
            EmailAddress.class,
            EmailAddress.class);
    }

    @Override
    public CriteriaSelector<Long, EmailAddress> getCountSelector() {
        return new CriteriaSelector<Long, EmailAddress>(
            em,
            getQuery(),
            Long.class,
            EmailAddress.class);
    }

    @Override
    public Class<EmailAddress> getClazz() {
        return EmailAddress.class;
    }

    @Override
    public EmailAddress newInstance() {
        return new EmailAddress();
    }

}