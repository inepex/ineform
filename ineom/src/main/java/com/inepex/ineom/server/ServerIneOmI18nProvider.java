package com.inepex.ineom.server;

import com.inepex.ineom.shared.i18n.IneOmI18n;

import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.google.inject.Provider;

public class ServerIneOmI18nProvider extends ServerI18nProvider<IneOmI18n> {

    private static final long serialVersionUID = 1L;

    public ServerIneOmI18nProvider(Provider<CurrentLang> currentLangProvider) {
        super(currentLangProvider);
    }

    @Override
    protected Class<IneOmI18n> getModuleClass() {
        return IneOmI18n.class;
    }

    @Override
    public IneOmI18n getVirgineI18nModule() {
        IneOmI18n module = new IneOmI18n(this);
        initTexts(module);
        return module;
    }

    public void initTexts(IneOmI18n module) {
        module.getTextMap().put(
            "validationAlphanum",
            "This field should contain only numbers and characters!");
        module.getTextMap().put(
            "validationAlphanumOrSpace",
            "This field should contain only numbers, characters or space!");
        module.getTextMap().put("validationEmail", "This is not valid e-mail address!");
        module.getTextMap().put("validationFieldError", "Field error");
        module.getTextMap().put("validationGeneralError", "Error:");
        module.getTextMap().put(
            "validatorEQ",
            "The number entered into field {fieldName} must be equal to {constvalAsString}!");
        module
            .getTextMap()
            .put(
                "validatorGE",
                "The number entered into field {fieldName} must be greater then or equal to {constvalAsString}!");
        module.getTextMap().put(
            "validatorGT",
            "The number entered into field {fieldName} must be greater then {constvalAsString}!");
        module
            .getTextMap()
            .put(
                "validatorLE",
                "The number entered into field {fieldName} must be less then or equal to {constvalAsString}!");
        module.getTextMap().put(
            "validatorLT",
            "The number entered into field {fieldName} must be less then {constvalAsString}!");
        module.getTextMap().put("validatorMaxLength", "Maximum {limit} characters can be entered!");
        module.getTextMap().put(
            "validatorMinLength",
            "At least {limit} characters have to be entered!");
        module.getTextMap().put(
            "validatorShouldAfter",
            "This date should be after {otherFieldsName}!");
        module.getTextMap().put(
            "validatorShouldBefore",
            "This date should be before {otherFieldsName}!");
        module.getTextMap().put(
            "validatorUniqueRelList",
            "The {i}th and the {j}th items are equal.");
        module.getTextMap().put("validator_mandatory", "This field is required!");
    }
}
