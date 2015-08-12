package com.inepex.example.ContactManager.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class PhoneNumberTypeHandlerFactory {

    private final DescriptorStore descriptorStore;

    /**
     * can be injected or created with 'new
     * PhoneNumberTypeHandlerFactory(decStore)'
     */
    @Inject
    public PhoneNumberTypeHandlerFactory(DescriptorStore descriptorStore) {
        this.descriptorStore = descriptorStore;
    }

    public PhoneNumberTypeHandler createHandler() {
        AssistedObject assistedObject = new KeyValueObject(PhoneNumberTypeConsts.descriptorName);
        return new PhoneNumberTypeHandler(assistedObject, descriptorStore);
    }

    public PhoneNumberTypeHandler createHandler(AssistedObject assistedObject) {
        if (!PhoneNumberTypeConsts.descriptorName.equals(assistedObject.getDescriptorName()))
            throw new IllegalArgumentException("Type incompatibility: handler: '"
                + PhoneNumberTypeConsts.descriptorName
                + "' assistedObject: '"
                + assistedObject.getDescriptorName()
                + "'");

        return new PhoneNumberTypeHandler(assistedObject, descriptorStore);
    }

    public PhoneNumberTypeSearchHandler createSearchHandler() {
        AssistedObject assistedObject = new KeyValueObject(PhoneNumberTypeConsts.searchDescriptor);
        return new PhoneNumberTypeSearchHandler(assistedObject, descriptorStore);
    }

    public PhoneNumberTypeSearchHandler createSearchHandler(AssistedObject assistedObject) {
        if (!PhoneNumberTypeConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
            throw new IllegalArgumentException("Type incompatibility: handler: '"
                + PhoneNumberTypeConsts.searchDescriptor
                + "' assistedObject: '"
                + assistedObject.getDescriptorName()
                + "'");

        return new PhoneNumberTypeSearchHandler(assistedObject, descriptorStore);
    }

    public static class PhoneNumberTypeHandler extends AssistedObjectHandler {

        private PhoneNumberTypeHandler(
            AssistedObject assistedObject,
            DescriptorStore descriptorStore) {
            super(assistedObject, descriptorStore);
        }

        public String getName() {
            return getString(PhoneNumberTypeConsts.k_name);
        }

        public void setName(String name) {
            set(PhoneNumberTypeConsts.k_name, name);
        }
    }

    public static class PhoneNumberTypeSearchHandler extends AssistedObjectHandler {

        private PhoneNumberTypeSearchHandler(
            AssistedObject assistedObject,
            DescriptorStore descriptorStore) {
            super(assistedObject, descriptorStore);
        }

        public String getName() {
            return getString(PhoneNumberTypeConsts.s_name);
        }

        public void setName(String name) {
            set(PhoneNumberTypeConsts.s_name, name);
        }
    }
}