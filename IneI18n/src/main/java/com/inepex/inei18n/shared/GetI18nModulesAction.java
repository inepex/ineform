package com.inepex.inei18n.shared;

import java.util.Collection;

import net.customware.gwt.dispatch.shared.Action;

public class GetI18nModulesAction implements Action<GetI18nModulesResult> {
	
	public Collection<String> moduleNames;

	public GetI18nModulesAction() {
	}
	
	public GetI18nModulesAction(Collection<String> moduleNames) {
		this.moduleNames = moduleNames;
	}
}
