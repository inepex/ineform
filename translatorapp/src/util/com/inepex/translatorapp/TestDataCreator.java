package com.inepex.translatorapp;

import javax.persistence.EntityManager;

import com.inepex.ineForm.server.util.StringUtil;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.Module;
import com.inepex.translatorapp.server.entity.User;
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
		dev.getAllowedRoles().add(TXT.Roles.developer);
		dev.setEmail("dev@inepex.com");
		dev.setPassword(StringUtil.hash("a"));
		dev.getTranslates().add(en);
		dev.getTranslates().add(hu);
		em.persist(dev);
		
		User trans = new User();
		trans.getAllowedRoles().add(TXT.Roles.translator);
		trans.setEmail("translator@inepex.com");
		trans.setPassword(StringUtil.hash("a"));
		trans.getTranslates().add(de);
		trans.getTranslates().add(en);
		em.persist(trans);
		
		em.getTransaction().commit();
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
		uiModule.setDefLang(hu);
		uiModule.getLangs().add(de);
		uiModule.getLangs().add(en);
		createUiModuleRows();
		em.persist(uiModule);
		
		coreModule = new Module();
		coreModule.setName("coreI18nModule");
		coreModule.setDefLang(en);
		createCoreModuleRows();
		em.persist(coreModule);
		
		em.getTransaction().commit();
	}

	private static void createCoreModuleRows() {
		//TODO
	}

	private static void createUiModuleRows() {
		// TODO Auto-generated method stub
	}
}
