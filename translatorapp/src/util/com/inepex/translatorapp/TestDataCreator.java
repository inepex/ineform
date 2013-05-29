package com.inepex.translatorapp;

import javax.persistence.EntityManager;

import com.inepex.ineForm.server.util.StringUtil;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.Module;
import com.inepex.translatorapp.server.entity.ModuleLang;
import com.inepex.translatorapp.server.entity.User;
import com.inepex.translatorapp.server.entity.UserLang;
import com.inepex.translatorapp.shared.TXT;

public class TestDataCreator {

	private static EntityManager em;
	
	private static Lang en;
	private static Lang de;
	private static Lang hu;
	
	private static Module uiModule;
	private static Module coreModule;
	
	public static void main(String[] args) throws Exception {
		em = EntityManagerInitializier.initInDropCreateMode();
		
		createLangsAndModules();
		createUsers();
	}
	
	private static void createUsers() throws Exception {
		em.getTransaction().begin();
		
		User dev = new User();
		dev.setRole(TXT.Roles.developer);
		dev.setEmail("developer@inepex.com");
		dev.setPassword(StringUtil.hash("a"));
		dev.getTranslates().add(lang(dev,en));
		dev.getTranslates().add(lang(dev,hu));
		em.persist(dev);
		
		User trans = new User();
		trans.setRole(TXT.Roles.translator);
		trans.setEmail("translator@inepex.com");
		trans.setPassword(StringUtil.hash("a"));
		trans.getTranslates().add(lang(trans,hu));
		trans.getTranslates().add(lang(trans,en));
		em.persist(trans);
		
		User inactive = new User();
		inactive.setEmail("inactive@inepex.com");
		inactive.setPassword(StringUtil.hash("a"));
		em.persist(inactive);
		
		em.getTransaction().commit();
	}

	private static UserLang lang(User u, Lang l) {
		UserLang lang = new UserLang();
		lang.setUser(u);
		lang.setLang(l);
		return lang;
	}

	private static void createLangsAndModules() {
		em.getTransaction().begin();
		
		en = new Lang("en");
		em.persist(en);
		
		de = new Lang("de");
		em.persist(de);
		
		hu = new Lang("hu");
		em.persist(hu);
		
		uiModule = new Module();
		uiModule.setName("uiI18nModule");
		uiModule.getLangs().add(lang(uiModule, hu));
		uiModule.getLangs().add(lang(uiModule, de));
		uiModule.getLangs().add(lang(uiModule, en));
		createUiModuleRows();
		em.persist(uiModule);
		
		coreModule = new Module();
		coreModule.setName("coreI18nModule");
		coreModule.getLangs().add(lang(coreModule, hu));
		createCoreModuleRows();
		em.persist(coreModule);
		
		em.getTransaction().commit();
	}

	private static ModuleLang lang(Module module, Lang l) {
		ModuleLang ml = new ModuleLang();
		ml.setModule(module);
		ml.setLang(l);
		return ml;
	}

	private static void createCoreModuleRows() {
		// TODO Auto-generated method stub
	}

	private static void createUiModuleRows() {
		// TODO Auto-generated method stub
	}
}
