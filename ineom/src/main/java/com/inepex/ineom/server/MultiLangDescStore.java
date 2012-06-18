package com.inepex.ineom.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.inei18n.shared.CurrentLang;

public class MultiLangDescStore extends MultiDescStore {
	
	private static final Logger _logger = LoggerFactory.getLogger(MultiLangDescStore.class);
	private final Provider<CurrentLang> currLangProvider;
	
	@Inject
	public MultiLangDescStore(Provider<CurrentLang> currLangProvider) {
		this.currLangProvider = currLangProvider;
	}
	
	protected String key() {
		try {
			return currLangProvider.get().getCurrentLang();
		} catch (Exception e) {
			_logger.warn("Lang can not found:", e);
			return CurrentLang.DEFAULT_LANG;
		}
	}
}

