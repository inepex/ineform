package com.inepex.inei18n.shared;

@SuppressWarnings("serial")
public class ClientI18nProvider<T extends I18nModule> implements I18nModuleProvider<T> {

	private T module;
	
	@Override
	public T get() {
		return module;
	}
	
	/**
	 * If used with other than the provided Generic type, will throw {@link ClassCastException}
	 * @param module
	 */
	@SuppressWarnings("unchecked")
	public void setCurrentModule(I18nModule module) {
		this.module = (T) module;
	}
	
	
	
}
