package com.inepex.translatorapp.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.customware.gwt.dispatch.server.Dispatch;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.server.DaoFinder;
import com.inepex.ineForm.server.i18n.ServerIneFormI18nProvider;
import com.inepex.ineFrame.server.AbstractGuiceDispatch;
import com.inepex.ineFrame.shared.dispatch.Loggable;
import com.inepex.inei18n.server.ApplicationLangs;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.server.ServerIneOmI18nProvider;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.server.i18n.ServertranslatorappI18nProvider;
import com.inepex.translatorapp.shared.assist.LangAssist;
import com.inepex.translatorapp.shared.assist.ModuleAssist;
import com.inepex.translatorapp.shared.assist.ModuleLangAssist;
import com.inepex.translatorapp.shared.assist.ModuleRowAssist;
import com.inepex.translatorapp.shared.assist.RegAssist;
import com.inepex.translatorapp.shared.assist.TranslateTableRowAssist;
import com.inepex.translatorapp.shared.assist.TranslatedValueAssist;
import com.inepex.translatorapp.shared.assist.UserAssist;
import com.inepex.translatorapp.shared.assist.UserLangAssist;

@Singleton
@SuppressWarnings("serial")
public class AppDispatchServlet extends AbstractGuiceDispatch {

    private final DaoFinder daofinder;

    @Inject
    public AppDispatchServlet(
        Dispatch dispatch,
        Provider<CurrentLang> currentLangProvider,
        I18nStore_Server serverI18n,
        DescriptorStore descStore,
        DaoFinder daoFinder,
        ApplicationLangs langs) {
        super(dispatch, currentLangProvider, serverI18n, descStore, true, langs);
        this.daofinder = daoFinder;
    }

    @Override
    public void init() throws ServletException {
        daofinder.addPackageByName("com.inepex.translatorapp.server.entity.dao");
        super.init();
    }

    @Override
    public void doLogAction(Loggable loggable, HttpServletRequest request) {}

    @Override
    public void registerAdditionalI18nModules(
        I18nStore_Server serverI18n,
        Provider<CurrentLang> currentLangProvider) {
        serverI18n.registerModule(new IneFormI18n(
            new ServerIneFormI18nProvider(currentLangProvider)));
        serverI18n.registerModule(new IneOmI18n(new ServerIneOmI18nProvider(currentLangProvider)));
        serverI18n.registerModule(new translatorappI18n(new ServertranslatorappI18nProvider(
            currentLangProvider)));
    }

    @Override
    public void registerAssists(DescriptorStore descStore) {
        new UserAssist(descStore).registerDescriptors();
        new LangAssist(descStore).registerDescriptors();
        new RegAssist(descStore).registerDescriptors();
        new UserLangAssist(descStore).registerDescriptors();
        new ModuleAssist(descStore).registerDescriptors();
        new ModuleLangAssist(descStore).registerDescriptors();
        new ModuleRowAssist(descStore).registerDescriptors();
        new TranslatedValueAssist(descStore).registerDescriptors();
        new TranslateTableRowAssist(descStore).registerDescriptors();
    }

    @Override
    public void setupDefaults() {}
}
