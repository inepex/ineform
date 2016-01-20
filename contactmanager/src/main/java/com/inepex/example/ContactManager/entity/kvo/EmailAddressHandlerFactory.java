package com.inepex.example.ContactManager.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class EmailAddressHandlerFactory {

    private final DescriptorStore descriptorStore;

    /**
     * can be injected or created with 'new
     * EmailAddressHandlerFactory(decStore)'
     */
    @Inject
    public EmailAddressHandlerFactory(DescriptorStore descriptorStore) {
        this.descriptorStore = descriptorStore;
    }

    public EmailAddressHandler createHandler() {
        AssistedObject assistedObject = new KeyValueObject(EmailAddressConsts.descriptorName);
        return new EmailAddressHandler(assistedObject, descriptorStore);
    }

    public EmailAddressHandler createHandler(AssistedObject assistedObject) {
        if (!EmailAddressConsts.descriptorName.equals(assistedObject.getDescriptorName()))
            throw new IllegalArgumentException(
                "Type incompatibility: handler: '" + EmailAddressConsts.descriptorName
                    + "' assistedObject: '" + assistedObject.getDescriptorName() + "'");

        return new EmailAddressHandler(assistedObject, descriptorStore);
    }

    public EmailAddressSearchHandler createSearchHandler() {
        AssistedObject assistedObject = new KeyValueObject(EmailAddressConsts.searchDescriptor);
        return new EmailAddressSearchHandler(assistedObject, descriptorStore);
    }

    public EmailAddressSearchHandler createSearchHandler(AssistedObject assistedObject) {
        if (!EmailAddressConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
            throw new IllegalArgumentException(
                "Type incompatibility: handler: '" + EmailAddressConsts.searchDescriptor
                    + "' assistedObject: '" + assistedObject.getDescriptorName() + "'");

        return new EmailAddressSearchHandler(assistedObject, descriptorStore);
    }

    public static class EmailAddressHandler extends AssistedObjectHandler {

        private EmailAddressHandler(
            AssistedObject assistedObject,
            DescriptorStore descriptorStore) {
            super(assistedObject, descriptorStore);
        }

        public String getEmail() {
            return getString(EmailAddressConsts.k_email);
        }

        public void setEmail(String email) {
            set(EmailAddressConsts.k_email, email);
        }
    }

    public static class EmailAddressSearchHandler extends AssistedObjectHandler {

        private EmailAddressSearchHandler(
            AssistedObject assistedObject,
            DescriptorStore descriptorStore) {
            super(assistedObject, descriptorStore);
        }

        public String getEmail() {
            return getString(EmailAddressConsts.s_email);
        }

        public void setEmail(String email) {
            set(EmailAddressConsts.s_email, email);
        }
    }
}