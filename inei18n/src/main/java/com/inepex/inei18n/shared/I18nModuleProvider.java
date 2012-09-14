package com.inepex.inei18n.shared;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface I18nModuleProvider<T extends I18nModule> extends Serializable, IsSerializable {
	public T get();
}
