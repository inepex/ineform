package com.inepex.inei18n.shared;

import java.io.Serializable;

public interface I18nModuleProvider<T extends I18nModule> extends Serializable {
	public T get();
}
