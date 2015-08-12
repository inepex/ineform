package com.inepex.translatorapp.shared.kvo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.HandlerFactoryI;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

@Singleton
public class UserLangHandlerFactory
    implements
    HandlerFactoryI<UserLangHandlerFactory.UserLangHandler> {

    private final DescriptorStore descriptorStore;

    /**
     * can be injected or created with 'new UserLangHandlerFactory(decStore)'
     */
    @Inject
    public UserLangHandlerFactory(DescriptorStore descriptorStore) {
        this.descriptorStore = descriptorStore;
    }

    @Override
    public UserLangHandler createHandler() {
        AssistedObject assistedObject = new KeyValueObject(UserLangConsts.descriptorName);
        return new UserLangHandler(assistedObject, descriptorStore);
    }

    @Override
    public UserLangHandler createHandler(AssistedObject assistedObject) {
        if (!UserLangConsts.descriptorName.equals(assistedObject.getDescriptorName()))
            throw new IllegalArgumentException("Type incompatibility: handler: '"
                + UserLangConsts.descriptorName
                + "' assistedObject: '"
                + assistedObject.getDescriptorName()
                + "'");

        return new UserLangHandler(assistedObject, descriptorStore);
    }

    public UserLangSearchHandler createSearchHandler() {
        AssistedObject assistedObject = new KeyValueObject(UserLangConsts.searchDescriptor);
        return new UserLangSearchHandler(assistedObject, descriptorStore);
    }

    public UserLangSearchHandler createSearchHandler(AssistedObject assistedObject) {
        if (!UserLangConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
            throw new IllegalArgumentException("Type incompatibility: handler: '"
                + UserLangConsts.searchDescriptor
                + "' assistedObject: '"
                + assistedObject.getDescriptorName()
                + "'");

        return new UserLangSearchHandler(assistedObject, descriptorStore);
    }

    public static class UserLangHandler extends AssistedObjectHandler {

        private UserLangHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
            super(assistedObject, descriptorStore);
        }

        public Relation getLang() {
            return getRelation(UserLangConsts.k_lang);
        }

        public void setLang(Relation lang) {
            set(UserLangConsts.k_lang, lang);
        }

        public Relation getUser() {
            return getRelation(UserLangConsts.k_user);
        }

        public void setUser(Relation user) {
            set(UserLangConsts.k_user, user);
        }
    }

    public static class UserLangSearchHandler extends AssistedObjectHandler {

        private UserLangSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
            super(assistedObject, descriptorStore);
        }

    }
}