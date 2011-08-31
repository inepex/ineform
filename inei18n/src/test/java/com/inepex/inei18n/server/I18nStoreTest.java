package com.inepex.inei18n.server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Provider;
import com.inepex.inei18n.client.I18nStore_Client;
import com.inepex.inei18n.shared.ClientI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.inei18n.shared.LocalizedString;

public class I18nStoreTest {
	
	public static String currentLang = "en";
	
	public I18nStore_Client clientSotre = null;
	public I18nStore_Server serverStore = null;
	
	// Foo module vars
	ClientI18nProvider<FooI18n> fooClientI18nProvider = null;
	ServerFooI18nProvider fooServerI18nProvider = null;
	FooI18n fooI18nModule = null;

	// Bar module vars
	ClientI18nProvider<BarI18n> barClientI18nProvider = null;
	ServerBarI18nProvider barServerI18nProvider = null;
	BarI18n barI18nModule = null;
		
	@Before
	public void init() {
		
		clientSotre = new I18nStore_Client();
		serverStore = new I18nStore_Server();

		fooClientI18nProvider = new ClientI18nProvider<FooI18n>();
		fooServerI18nProvider = new ServerFooI18nProvider(new TestUsersCurrentLangProvider());

		barClientI18nProvider = new ClientI18nProvider<BarI18n>();
		barServerI18nProvider = new ServerBarI18nProvider(new TestUsersCurrentLangProvider());

		
		/**
		 * provider should be set to appropriate client or server side before use
		 */
		fooI18nModule = new FooI18n(null);

		/**
		 * provider should be set to appropriate client or server side before use
		 */
		barI18nModule = new BarI18n(null);

	}
	
	@Test
	public void serverSideTest() {
		setupServerStore_SetServerMode();
		loadDummyData();
		
		assertDefault();
	}
	
	private void assertDefault() {
		currentLang = "en";
		assertEquals("foo1_EN", FooI18n.foo1());
		assertEquals("foo2_EN", FooI18n.foo2());
		assertEquals("bar1_EN", BarI18n.bar1());
		assertEquals("bar2_EN", BarI18n.bar2());
		
		currentLang = "hu";
		assertEquals("foo1_HU", FooI18n.foo1());
		assertEquals("foo2_HU", FooI18n.foo2());
		assertEquals("bar1_HU", BarI18n.bar1());
		assertEquals("bar2_HU", BarI18n.bar2());	}
	
	@Test
	public void clientSideTest() {
		// also setup server side
		setupServerStore_SetServerMode();
		loadDummyData();
		
		currentLang = "en";
		updateClientSideProvidersForCurrentLang();
		setupClientStore_SetClientMode();
		
		assertEquals("foo1_EN", FooI18n.foo1());
		assertEquals("foo2_EN", FooI18n.foo2());
		assertEquals("bar1_EN", BarI18n.bar1());
		assertEquals("bar2_EN", BarI18n.bar2());
		
		currentLang = "hu";
		setServerSideMode();
		updateClientSideProvidersForCurrentLang();
		setClientSideMode();
		
		assertEquals("foo1_HU", FooI18n.foo1());
		assertEquals("foo2_HU", FooI18n.foo2());
		assertEquals("bar1_HU", BarI18n.bar1());
		assertEquals("bar2_HU", BarI18n.bar2());
	
	}
	
	@Test
	public void createCsv() throws IOException {
		setupServerStore_SetServerMode();
		loadDummyData();
		
		I18nModuleConverter fooConverter = new I18nModuleConverter(FooI18n.class
														, serverStore.getLocalizablesForModule(FooI18n.MODULE_NAME));
		fooConverter.saveCsvToDefaultPath();

		I18nModuleConverter barConverter = new I18nModuleConverter(BarI18n.class
				, serverStore.getLocalizablesForModule(BarI18n.MODULE_NAME));
		barConverter.saveCsvToDefaultPath();
	}
	
	@Test
	public void loadModulesFromCsv() {
		setupServerStore_SetServerMode();
		
		serverStore.loadAllModulesDataFormCsv(true);
		serverStore.initI18nModules();
		
		assertDefault();
	}

	@Test
	public void createSourceFilesFromCsv() throws ResourceNotFoundException, ParseErrorException, Exception {
		loadModulesFromCsv();

		I18nModuleConverter fooConverter = new I18nModuleConverter(FooI18n.class
				, serverStore.getLocalizablesForModule(FooI18n.MODULE_NAME));
		
		fooConverter.generateModuleFile();
		fooConverter.generateServerModuleProviderFile();

		I18nModuleConverter barConverter = new I18nModuleConverter(BarI18n.class
				, serverStore.getLocalizablesForModule(BarI18n.MODULE_NAME));
		barConverter.generateModuleFile();
		barConverter.generateServerModuleProviderFile();
	}
	
	@Test
	public void generateModuleFileToConsole() throws ResourceNotFoundException, ParseErrorException, Exception {
		loadModulesFromCsv();

		I18nModuleConverter fooConverter = new I18nModuleConverter(FooI18n.class
				, serverStore.getLocalizablesForModule(FooI18n.MODULE_NAME));

		StringWriter sw = new StringWriter();
		fooConverter.generateFileToWriter(sw, I18nModuleConverter.MODULE_VM
										, fooConverter.getModuleFileCreatorContext());
		
		System.out.println(sw.getBuffer());
	}
	
	private void updateClientSideProvidersForCurrentLang() {
		fooClientI18nProvider.setCurrentModule(serverStore.getI18nModuleByNameForCurrentLang(FooI18n.MODULE_NAME));
		barClientI18nProvider.setCurrentModule(serverStore.getI18nModuleByNameForCurrentLang(BarI18n.MODULE_NAME));
	}
	
	private void setupServerStore_SetServerMode() {
		setServerSideMode();
		serverStore.registerModule(fooI18nModule);
		serverStore.registerModule(barI18nModule);
	}
	
	private void loadDummyData() {
		serverStore.addLocalizables(FooI18n.MODULE_NAME, getFooLocalizables());
		serverStore.addLocalizables(BarI18n.MODULE_NAME, getBarLocalizables());
		serverStore.initI18nModules();
	}
	
	private void setServerSideMode() {
		FooI18n.moduleProvider = fooServerI18nProvider;
		BarI18n.moduleProvider = barServerI18nProvider;
	}	
	
	private void setupClientStore_SetClientMode() {
		setClientSideMode();
		clientSotre.registerModule(fooI18nModule);
		clientSotre.registerModule(barI18nModule);
		// TODO query actual module states form server
	}
	
	private void setClientSideMode() {
		FooI18n.moduleProvider = fooClientI18nProvider;
		BarI18n.moduleProvider = barClientI18nProvider;
	}
	
	private List<LocalizedString> getFooLocalizables() {
		List<LocalizedString> testData = new ArrayList<LocalizedString>();
		
		LocalizedString foo1 = new LocalizedString("foo1", "Description for foo1");
		foo1.putString("en", "foo1_EN");
		foo1.putString("hu", "foo1_HU");
		testData.add(foo1);
		LocalizedString foo2 = new LocalizedString("foo2", "Description for foo2");
		foo2.putString("en", "foo2_EN");
		foo2.putString("hu", "foo2_HU");
		testData.add(foo2);
		
		return testData;
	}
	
	private List<LocalizedString> getBarLocalizables() {
		List<LocalizedString> testData = new ArrayList<LocalizedString>();
		
		LocalizedString bar1 = new LocalizedString("bar1", "Description for bar1");
		bar1.putString("en", "bar1_EN");
		bar1.putString("hu", "bar1_HU");
		testData.add(bar1);
		LocalizedString bar2 = new LocalizedString("bar2", "Description for bar2");
		bar2.putString("en", "bar2_EN");
		bar2.putString("hu", "bar2_HU");
		testData.add(bar2);		
		return testData;
	}
	
	private class TestUsersCurrentLangProvider implements Provider<CurrentLang> {
		@Override
		public CurrentLang get() {
			return new CurrentLang() {
				@Override
				public String getCurrentLang() {
					return currentLang;
				}
				@Override
				public void setLangOverride(String langOverride) {
				}
				@Override
				public void resetLangOverride() {
					
				}
			};
		}		
	}

}
