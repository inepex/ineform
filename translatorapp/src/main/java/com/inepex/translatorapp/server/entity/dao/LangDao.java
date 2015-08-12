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
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.dao.query.LangQuery;
import com.inepex.translatorapp.server.entity.mapper.LangMapper;

/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E)
 * or remove(E). (Don't forget to call super.persist, super.merge ...)
 * 
 */
@Singleton
public class LangDao extends BaseDao<Lang> {

    private final DescriptorStore descStore;

    @Inject
    public LangDao(
        Provider<EntityManager> em,
        ManipulationObjectFactory objectFactory,
        AssistedObjectHandlerFactory handlerFactory,
        DescriptorStore descStore) {
        super(em, objectFactory, handlerFactory);
        this.descStore = descStore;
    }

    @Override
    public BaseQuery<Lang> getQuery() {
        return new LangQuery(descStore);
    }

    @Override
    public BaseMapper<Lang> getMapper() {
        return new LangMapper(descStore);
    }

    @Override
    public CriteriaSelector<Lang, Lang> getSelector() {
        return new CriteriaSelector<Lang, Lang>(em, getQuery(), Lang.class, Lang.class);
    }

    @Override
    public CriteriaSelector<Long, Lang> getCountSelector() {
        return new CriteriaSelector<Long, Lang>(em, getQuery(), Long.class, Lang.class);
    }

    @Override
    public Class<Lang> getClazz() {
        return Lang.class;
    }

    @Override
    public Lang newInstance() {
        return new Lang();
    }

    /* hc:customMethods */
    // overrides and custom methods
    /* hc */

}