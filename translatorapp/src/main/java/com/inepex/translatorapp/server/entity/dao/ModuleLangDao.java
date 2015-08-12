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
import com.inepex.translatorapp.server.entity.ModuleLang;
import com.inepex.translatorapp.server.entity.dao.query.ModuleLangQuery;
import com.inepex.translatorapp.server.entity.mapper.ModuleLangMapper;

/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E)
 * or remove(E). (Don't forget to call super.persist, super.merge ...)
 * 
 */
@Singleton
public class ModuleLangDao extends BaseDao<ModuleLang> {

    private final DescriptorStore descStore;

    @Inject
    public ModuleLangDao(
        Provider<EntityManager> em,
        ManipulationObjectFactory objectFactory,
        AssistedObjectHandlerFactory handlerFactory,
        DescriptorStore descStore) {
        super(em, objectFactory, handlerFactory);
        this.descStore = descStore;
    }

    @Override
    public BaseQuery<ModuleLang> getQuery() {
        return new ModuleLangQuery(descStore);
    }

    @Override
    public BaseMapper<ModuleLang> getMapper() {
        return new ModuleLangMapper(descStore);
    }

    @Override
    public CriteriaSelector<ModuleLang, ModuleLang> getSelector() {
        return new CriteriaSelector<ModuleLang, ModuleLang>(
            em,
            getQuery(),
            ModuleLang.class,
            ModuleLang.class);
    }

    @Override
    public CriteriaSelector<Long, ModuleLang> getCountSelector() {
        return new CriteriaSelector<Long, ModuleLang>(em, getQuery(), Long.class, ModuleLang.class);
    }

    @Override
    public Class<ModuleLang> getClazz() {
        return ModuleLang.class;
    }

    @Override
    public ModuleLang newInstance() {
        return new ModuleLang();
    }

    /* hc:customMethods */
    // overrides and custom methods
    /* hc */

}