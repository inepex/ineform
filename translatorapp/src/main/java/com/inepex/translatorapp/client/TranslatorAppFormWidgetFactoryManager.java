package com.inepex.translatorapp.client;

import jakarta.inject.Inject;
import com.inepex.ineForm.client.form.factories.DefaultFormWidgetFactory;
import com.inepex.ineForm.client.form.factories.FormWidgetFactoryManager;

public class TranslatorAppFormWidgetFactoryManager extends FormWidgetFactoryManager {

    @Inject
    public TranslatorAppFormWidgetFactoryManager(
        TranslatorAppFormWidgetFactory translatorFactory,
        DefaultFormWidgetFactory defaultFactory) {
        super(translatorFactory, defaultFactory);
    }

}
