package com.inepex.translatorapp.server.entity.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.server.entity.Module;
import com.inepex.translatorapp.server.entity.ModuleRow;
import com.inepex.translatorapp.server.entity.TranslatedValue;
import com.inepex.translatorapp.shared.kvo.ModuleRowConsts;
import com.inepex.translatorapp.shared.kvo.ModuleRowHandlerFactory;
import com.inepex.translatorapp.shared.kvo.ModuleRowHandlerFactory.ModuleRowHandler;

public class ModuleRowMapper extends BaseMapper<ModuleRow> {

    private final DescriptorStore descriptorStore;
    private final ModuleRowHandlerFactory handlerFactory;

    @Inject
    public ModuleRowMapper(DescriptorStore descriptorStore) {
        this.descriptorStore = descriptorStore;
        this.handlerFactory = new ModuleRowHandlerFactory(descriptorStore);
    }

    @Override
    public ModuleRow kvoToEntity(AssistedObject fromKvo, ModuleRow to) {
        ModuleRowHandler fromHandler = handlerFactory.createHandler(fromKvo);

        if (to == null)
            to = new ModuleRow();
        if (!fromHandler.isNew())
            to.setId(fromHandler.getId());
        if (fromHandler.containsString(ModuleRowConsts.k_key))
            to.setKey(fromHandler.getKey());
        if (fromHandler.containsString(ModuleRowConsts.k_description))
            to.setDescription(fromHandler.getDescription());
        if (fromHandler.containsRelation(ModuleRowConsts.k_module)) {
            if (fromHandler.getModule() == null) {
                to.setModule(null);
            } else {
                to.setModule(new Module(fromHandler.getModule().getId()));
            }
        }
        if (fromHandler.containsList(ModuleRowConsts.k_values)) {
            if (to.getValues() == null)
                to.setValues(new ArrayList<TranslatedValue>());

            Map<Long, TranslatedValue> origItems = new HashMap<Long, TranslatedValue>();
            for (TranslatedValue item : to.getValues()) {
                origItems.put(item.getId(), item);
            }

            TranslatedValueMapper mapper = new TranslatedValueMapper(descriptorStore);
            for (Relation rel : fromHandler.getValues().getRelationList()) {
                if (rel == null)
                    continue;
                if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new
                                                                // item
                    TranslatedValue entity = new TranslatedValue(IFConsts.NEW_ITEM_ID);
                    mapper.kvoToEntity(rel.getKvo(), entity);
                    entity.setRow(to);
                    to.getValues().add(entity);
                } else {
                    TranslatedValue origItem = origItems.get(rel.getId());
                    if (rel.getKvo() == null) { // delete item
                        to.getValues().remove(origItem);
                    } else { // edit item
                        mapper.kvoToEntity(rel.getKvo(), origItem);
                    }
                }
            }
        }

        /* hc:customToEntity */
        // custom mappings to Entity comes here.
        /* hc */

        return to;
    }

    @Override
    public AssistedObject entityToKvo(ModuleRow entity) {
        ModuleRowHandler handler = handlerFactory.createHandler();

        if (entity.getId() != null)
            handler.setId(entity.getId());
        if (entity.getKey() != null && !"".equals(entity.getKey()))
            handler.setKey(entity.getKey());
        if (entity.getDescription() != null && !"".equals(entity.getDescription()))
            handler.setDescription(entity.getDescription());
        if (entity.getModule() != null)
            handler.setModule(new ModuleMapper(descriptorStore).toRelation(
                entity.getModule(),
                false));
        {
            IneList ineList = new IneList();
            List<Relation> relationList = new ArrayList<Relation>();
            if (entity.getValues() != null)
                for (TranslatedValue item : entity.getValues()) {
                    relationList.add(new TranslatedValueMapper(descriptorStore).toRelation(
                        item,
                        true));
                }
            if (relationList.size() > 0) {
                ineList.setRelationList(relationList);
                handler.setValues(ineList);
            }
        }

        /* hc:customToKvo */
        // custom mappings to Kvo comes here. Eg. when some properties should
        // not be sent to the UI
        /* hc */

        return handler.getAssistedObject();
    }

    @Override
    public Relation toRelation(ModuleRow entity, boolean includeKvo) {
        if (entity == null)
            return null;
        return new Relation(entity.getId(), entity.toString(), includeKvo
            ? entityToKvo(entity)
            : null);
    }

}