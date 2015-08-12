package com.inepex.example.ContactManager.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.customware.gwt.dispatch.server.Dispatch;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.assist.CompanyAssist;
import com.inepex.example.ContactManager.entity.assist.ContactAssist;
import com.inepex.example.ContactManager.entity.assist.EmailAddressAssist;
import com.inepex.example.ContactManager.entity.assist.MeetingAssist;
import com.inepex.example.ContactManager.entity.assist.PhoneNumberAssist;
import com.inepex.example.ContactManager.entity.assist.PhoneNumberTypeAssist;
import com.inepex.example.ContactManager.entity.assist.UserAssist;
import com.inepex.example.ContactManager.server.i18n.ServerCMI18nProvider;
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
        daofinder.addPackageByName("com.inepex.example.ContactManager.entity.dao");
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
        serverI18n.registerModule(new CMI18n(new ServerCMI18nProvider(currentLangProvider)));
    }

    @Override
    public void registerAssists(DescriptorStore descStore) {
        new MeetingAssist(descStore).registerDescriptors();
        new CompanyAssist(descStore).registerDescriptors();
        new ContactAssist(descStore).registerDescriptors();
        new EmailAddressAssist(descStore).registerDescriptors();
        new PhoneNumberAssist(descStore).registerDescriptors();
        new PhoneNumberTypeAssist(descStore).registerDescriptors();
        new UserAssist(descStore).registerDescriptors();
    }

    @Override
    public void setupDefaults() {}
}
