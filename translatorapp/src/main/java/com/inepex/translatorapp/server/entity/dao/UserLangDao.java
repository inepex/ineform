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
import com.inepex.translatorapp.server.entity.UserLang;
import com.inepex.translatorapp.server.entity.dao.query.UserLangQuery;
import com.inepex.translatorapp.server.entity.mapper.UserLangMapper;

/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E)
 * or remove(E). (Don't forget to call super.persist, super.merge ...)
 * 
 */
@Singleton
public class UserLangDao extends BaseDao<UserLang> {

    private final DescriptorStore descStore;

    @Inject
    public UserLangDao(
        Provider<EntityManager> em,
        ManipulationObjectFactory objectFactory,
        AssistedObjectHandlerFactory handlerFactory,
        DescriptorStore descStore) {
        super(em, objectFactory, handlerFactory);
        this.descStore = descStore;
    }

    @Override
    public BaseQuery<UserLang> getQuery() {
        return new UserLangQuery(descStore);
    }

    @Override
    public BaseMapper<UserLang> getMapper() {
        return new UserLangMapper(descStore);
    }

    @Override
    public CriteriaSelector<UserLang, UserLang> getSelector() {
        return new CriteriaSelector<UserLang, UserLang>(
            em,
            getQuery(),
            UserLang.class,
            UserLang.class);
    }

    @Override
    public CriteriaSelector<Long, UserLang> getCountSelector() {
        return new CriteriaSelector<Long, UserLang>(em, getQuery(), Long.class, UserLang.class);
    }

    @Override
    public Class<UserLang> getClazz() {
        return UserLang.class;
    }

    @Override
    public UserLang newInstance() {
        return new UserLang();
    }

    /* hc:customMethods */
    // overrides and custom methods
    /* hc */

}