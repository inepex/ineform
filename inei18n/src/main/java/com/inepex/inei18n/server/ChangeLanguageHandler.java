package com.inepex.inei18n.server;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.inepex.inei18n.shared.ChangeLanguageAction;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.inei18n.shared.SimpleResult;

/**
 * 
 * see {@link ChangeLanguageAction} for details
 * 
 */
public class ChangeLanguageHandler implements ActionHandler<ChangeLanguageAction, SimpleResult> {

    private final static Logger logger = LoggerFactory.getLogger(ChangeLanguageHandler.class);

    @Inject
    CurrentLang currentLang;
    @Inject
    I18nStore_Server i18nStore;
    @Inject
    ApplicationLangs langs;

    @Override
    public Class<ChangeLanguageAction> getActionType() {
        return ChangeLanguageAction.class;
    }

    @Override
    public SimpleResult execute(
        ChangeLanguageAction action,
        net.customware.gwt.dispatch.server.ExecutionContext arg1) throws DispatchException {

        String reqLang = null;
        if (action.getRequestedLanguage() == null || "".equals(action.getRequestedLanguage())
            || !langs.getLangs().contains(action.getRequestedLanguage())) {
            reqLang = CurrentLang.DEFAULT_LANG;
            logger
                .warn("Requested lang is not supported or null: {}", action.getRequestedLanguage());
        } else {
            reqLang = action.getRequestedLanguage();
        }

        currentLang.setSessionLang(reqLang);
        return new SimpleResult();
    }

    @Override
    public void rollback(
        ChangeLanguageAction arg0,
        SimpleResult arg1,
        net.customware.gwt.dispatch.server.ExecutionContext arg2) throws DispatchException {}

}
