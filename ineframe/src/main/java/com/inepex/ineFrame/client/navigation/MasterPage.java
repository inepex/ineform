package com.inepex.ineFrame.client.navigation;

import java.util.Map;

public interface MasterPage {
	void render(String hierarchicalId, InePlace place, Map<String, String> urlParams);
	void renderForbidden(String hierarhycalId, InePlace place);
}
