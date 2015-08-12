package com.inepex.translatorapp.server.entity.mapper;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.ModuleLang;
import com.inepex.translatorapp.shared.kvo.ModuleLangConsts;
import com.inepex.translatorapp.shared.kvo.ModuleLangHandlerFactory;
import com.inepex.translatorapp.shared.kvo.ModuleLangHandlerFactory.ModuleLangHandler;

public class ModuleLangMapper extends BaseMapper<ModuleLang> {

    private final DescriptorStore descriptorStore;
    private final ModuleLangHandlerFactory handlerFactory;

    @Inject
    public ModuleLangMapper(DescriptorStore descriptorStore) {
        this.descriptorStore = descriptorStore;
        this.handlerFactory = new ModuleLangHandlerFactory(descriptorStore);
    }

    @Override
    public ModuleLang kvoToEntity(AssistedObject fromKvo, ModuleLang to) {
        ModuleLangHandler fromHandler = handlerFactory.createHandler(fromKvo);

        if (to == null)
            to = new ModuleLang();
        if (!fromHandler.isNew())
            to.setId(fromHandler.getId());
        if (fromHandler.containsRelation(ModuleLangConsts.k_lang)) {
            if (fromHandler.getLang() == null) {
                to.setLang(null);
            } else {
                to.setLang(new Lang(fromHandler.getLang().getId()));
            }
        }
        if (fromHandler.containsRelation(ModuleLangConsts.k_module)) {
            if (fromHandler.getModule() == null) {
                to.setModule(null);
            } else {}
        }

        /* hc:customToEntity */
        // custom mappings to Entity comes here.
        /* hc */

        return to;
    }

    @Override
    public AssistedObject entityToKvo(ModuleLang entity) {
        ModuleLangHandler handler = handlerFactory.createHandler();

        if (entity.getId() != null)
            handler.setId(entity.getId());
        if (entity.getLang() != null)
            handler.setLang(new LangMapper(descriptorStore).toRelation(entity.getLang(), true));
        if (entity.getModule() != null)
            handler.setModule(new ModuleMapper(descriptorStore).toRelation(
                entity.getModule(),
                false));

        /* hc:customToKvo */
        // custom mappings to Kvo comes here. Eg. when some properties should
        // not be sent to the UI
        /* hc */

        return handler.getAssistedObject();
    }

    @Override
    public Relation toRelation(ModuleLang entity, boolean includeKvo) {
        if (entity == null)
            return null;
        return new Relation(entity.getId(), entity.toString(), includeKvo
            ? entityToKvo(entity)
            : null);
    }

}