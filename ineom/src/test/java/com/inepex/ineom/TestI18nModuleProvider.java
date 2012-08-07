package com.inepex.ineom;

import java.lang.reflect.Field;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

@SuppressWarnings("serial")
public class TestI18nModuleProvider<T extends I18nModule> implements I18nModuleProvider<T>{
	
	public static <T extends I18nModule> T createTestI18nProvider(Class<T> clazz) {
		try {
			final T mod = clazz.newInstance();
			Field f = clazz.getDeclaredField("moduleProvider");
			f.setAccessible(true);		
			f.set(mod, new TestI18nModuleProvider<T>(mod));			
			return mod;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private final T mod;
	
	TestI18nModuleProvider(T mod) {
		this.mod=mod;
	}

	@Override
	public T get() {
		return mod;
	}
}