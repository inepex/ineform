package com.inepex.example.ineForm.server;

import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.customware.gwt.dispatch.server.Dispatch;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ineForm.entity.assist.ContactAddresDetailAssist;
import com.inepex.example.ineForm.entity.assist.ContactAssist;
import com.inepex.example.ineForm.entity.assist.ContactCTypeRelAssist;
import com.inepex.example.ineForm.entity.assist.ContactNatRelAssist;
import com.inepex.example.ineForm.entity.assist.ContactTypeAssist;
import com.inepex.example.ineForm.entity.assist.Contact_ContactRoleAssist;
import com.inepex.example.ineForm.entity.assist.Contact_ContactStateAssist;
import com.inepex.example.ineForm.entity.assist.NationalityAssist;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.server.DaoFinder;
import com.inepex.ineForm.server.i18n.ServerIneFormI18nProvider;
import com.inepex.ineFrame.server.AbstractGuiceDispatch;
import com.inepex.ineFrame.shared.dispatch.Loggable;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.server.MultiLangDescStore;
import com.inepex.ineom.server.ServerIneOmI18nProvider;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.i18n.IneOmI18n;

@Singleton
public class ShowcaseDispatchServlet extends AbstractGuiceDispatch {

	private static final long serialVersionUID = 1L;
	private final DaoFinder daoFinder;

	@Inject
	public ShowcaseDispatchServlet(Dispatch dispatch, Provider<CurrentLang> currentLangProvider, I18nStore_Server serverI18n,
			MultiLangDescStore multiLangDescStore, DaoFinder daoFinder) {
		super(dispatch, currentLangProvider, serverI18n, multiLangDescStore);
		this.daoFinder=daoFinder;
	}

	@Override
	public void init() throws ServletException {
		daoFinder.addPackageByName("com.inepex.example.ineForm.entity.dao");
		super.init();
	}



	@Override
	public boolean userHasAnyOfListedRoles(Set<String> allowedRoles) {
		return true;
	}


	@Override
	public void doLogAction(Loggable loggable, HttpServletRequest request) {
		
	}



	@Override
	public void registerAssists(DescriptorStore descStore) {
		new ContactAssist(descStore).registerDescriptors();
		new NationalityAssist(descStore).registerDescriptors();
		new ContactTypeAssist(descStore).registerDescriptors();
		new ContactAddresDetailAssist(descStore).registerDescriptors();
		new ContactCTypeRelAssist(descStore).registerDescriptors();
		new ContactNatRelAssist(descStore).registerDescriptors();
		new Contact_ContactRoleAssist(descStore).registerDescriptors();
		new Contact_ContactStateAssist(descStore).registerDescriptors();		
	}



	@Override
	public void setupDefaults() {
		
	}

	@Override
	public void registerAdditionalI18nModules(I18nStore_Server serverI18n, Provider<CurrentLang> currentLangProvider) {
		serverI18n.registerModule(new IneFormI18n(new ServerIneFormI18nProvider(currentLangProvider)));
		serverI18n.registerModule(new IneOmI18n(new ServerIneOmI18nProvider(currentLangProvider)));
	}

}
