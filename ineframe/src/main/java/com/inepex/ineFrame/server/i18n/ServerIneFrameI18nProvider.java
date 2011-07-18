package com.inepex.ineFrame.server.i18n;

import com.google.inject.Provider;
import com.inepex.ineFrame.client.i18n.IneFrameI18n_old;
import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;

public class ServerIneFrameI18nProvider extends ServerI18nProvider<IneFrameI18n_old> {

	private static final long serialVersionUID = 1L;

	public ServerIneFrameI18nProvider(Provider<CurrentLang> currentLangProvider) {
		super(currentLangProvider);
	}

	@Override
	protected Class<IneFrameI18n_old> getModuleClass() {
		return IneFrameI18n_old.class;
	}

	@Override
	protected IneFrameI18n_old getVirgineI18nModule() {
		return new IneFrameI18n_old(this);
	}
}
