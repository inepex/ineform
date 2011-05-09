package com.inepex.ineFrame.client.navigation;

import java.util.Map;

public interface MasterPage {
	void render(InePlace place, Map<String, String> urlParams);
	void renderForbidden(InePlace place);
}
