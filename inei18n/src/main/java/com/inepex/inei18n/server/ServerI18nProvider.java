package com.inepex.inei18n.server;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provider;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public abstract class ServerI18nProvider<T extends I18nModule> implements I18nModuleProvider<T> {

	final Logger logger = LoggerFactory.getLogger(ServerI18nProvider.class);
	
	private static final long serialVersionUID = 1L;

	protected Provider<CurrentLang> currentLangProvider;
	
	protected Map<String, T> i18nsByLang = new HashMap<String, T>(); 
	
	private TreeMap<String, Field> fieldsByName = null;
	
	public ServerI18nProvider() {
	}
	
	public ServerI18nProvider(Provider<CurrentLang> currentLangProvider) {
		this.currentLangProvider = currentLangProvider;
	}
	
	@Override
	public T get() {
		return i18nsByLang.get(currentLangProvider.get().getCurrentLang());
	}

	@SuppressWarnings("unchecked")
	public void addI18nForLang(String lang, I18nModule i18n) {
		i18nsByLang.put(lang, (T) i18n);
	}
	
	public T getI18nForLang(String lang) {
		if (!i18nsByLang.containsKey(lang)) {
			i18nsByLang.put(lang, getVirgineI18nModule());
		}
		return i18nsByLang.get(lang);
	}
	
	public void setByKeyAndLang(String lang, String key, String value) {
		if (lang == null || key == null) {
			logger.warn("setByKeyAndLang called with invalid properties lang: {}, key: {}, value: {}", new Object[]{lang, key, value});
			return;
		}
		try {
			T module = getI18nForLang(lang);
			Field field = getFieldsTreeMap().get(key);
			field.set(module, value);
		} catch (IllegalArgumentException e) {
			logger.warn("IllegalArgumentException when setByKeyAndLang called with invalid properties lang: {}, key: {}, value: {}", new Object[]{lang, key, value});
		} catch (IllegalAccessException e) {
			logger.warn("IllegalAccessException when setByKeyAndLang called with invalid properties lang: {}, key: {}, value: {}", new Object[]{lang, key, value});
		} catch (Exception e) {
			logger.warn(e.getClass().getName() +
					" when setByKeyAndLang called with invalid properties lang: {}, key: {}, value: {}", new Object[]{lang, key, value});
		}
	}
	
	protected TreeMap<String, Field> getFieldsTreeMap() {
		if (fieldsByName == null) {
			fieldsByName = new TreeMap<String, Field>();
			for (Field field : getModuleClass().getFields()) {
				fieldsByName.put(field.getName(), field);
			}
		}
		
		return fieldsByName;
	}
	
	protected abstract Class<T> getModuleClass();
	
	protected abstract T getVirgineI18nModule();
	
}
