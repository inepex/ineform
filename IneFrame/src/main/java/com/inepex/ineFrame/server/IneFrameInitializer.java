package com.inepex.ineFrame.server;

import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public interface IneFrameInitializer {
	void registerAdditionalI18nModules(I18nStore_Server serverI18n);
	void registerAssists(DescriptorStore descStore);
	void setupDefaults();

}
