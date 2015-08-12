package com.inepex.translatorapp.shared.kvo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.HandlerFactoryI;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

@Singleton
public class ModuleRowHandlerFactory
    implements
    HandlerFactoryI<ModuleRowHandlerFactory.ModuleRowHandler> {

    private final DescriptorStore descriptorStore;

    /**
     * can be injected or created with 'new ModuleRowHandlerFactory(decStore)'
     */
    @Inject
    public ModuleRowHandlerFactory(DescriptorStore descriptorStore) {
        this.descriptorStore = descriptorStore;
    }

    @Override
    public ModuleRowHandler createHandler() {
        AssistedObject assistedObject = new KeyValueObject(ModuleRowConsts.descriptorName);
        return new ModuleRowHandler(assistedObject, descriptorStore);
    }

    @Override
    public ModuleRowHandler createHandler(AssistedObject assistedObject) {
        if (!ModuleRowConsts.descriptorName.equals(assistedObject.getDescriptorName()))
            throw new IllegalArgumentException("Type incompatibility: handler: '"
                + ModuleRowConsts.descriptorName
                + "' assistedObject: '"
                + assistedObject.getDescriptorName()
                + "'");

        return new ModuleRowHandler(assistedObject, descriptorStore);
    }

    public ModuleRowSearchHandler createSearchHandler() {
        AssistedObject assistedObject = new KeyValueObject(ModuleRowConsts.searchDescriptor);
        return new ModuleRowSearchHandler(assistedObject, descriptorStore);
    }

    public ModuleRowSearchHandler createSearchHandler(AssistedObject assistedObject) {
        if (!ModuleRowConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
            throw new IllegalArgumentException("Type incompatibility: handler: '"
                + ModuleRowConsts.searchDescriptor
                + "' assistedObject: '"
                + assistedObject.getDescriptorName()
                + "'");

        return new ModuleRowSearchHandler(assistedObject, descriptorStore);
    }

    public static class ModuleRowHandler extends AssistedObjectHandler {

        private ModuleRowHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
            super(assistedObject, descriptorStore);
        }

        public String getKey() {
            return getString(ModuleRowConsts.k_key);
        }

        public void setKey(String key) {
            set(ModuleRowConsts.k_key, key);
        }

        public String getDescription() {
            return getString(ModuleRowConsts.k_description);
        }

        public void setDescription(String description) {
            set(ModuleRowConsts.k_description, description);
        }

        public Relation getModule() {
            return getRelation(ModuleRowConsts.k_module);
        }

        public void setModule(Relation module) {
            set(ModuleRowConsts.k_module, module);
        }

        public IneList getValues() {
            return getList(ModuleRowConsts.k_values);
        }

        public void setValues(IneList values) {
            set(ModuleRowConsts.k_values, values);
        }
    }

    public static class ModuleRowSearchHandler extends AssistedObjectHandler {

        private ModuleRowSearchHandler(
            AssistedObject assistedObject,
            DescriptorStore descriptorStore) {
            super(assistedObject, descriptorStore);
        }

        public String getKey() {
            return getString(ModuleRowConsts.s_key);
        }

        public void setKey(String key) {
            set(ModuleRowConsts.s_key, key);
        }

        public Relation getModule() {
            return getRelation(ModuleRowConsts.s_module);
        }

        public void setModule(Relation module) {
            set(ModuleRowConsts.s_module, module);
        }
    }
}