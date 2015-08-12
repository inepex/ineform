package com.inepex.ineFrame.test;

import java.util.List;

import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.I18nModule;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class DefaultIneFrameClientSideTestBase extends IneFrameClientSideTestBase {

    @Override
    public void registerAssists(DescriptorStore descStore) {}

    @Override
    public void setupDefaults() {}

    @Override
    public List<Class<? extends ServerI18nProvider>> listUsedI18nClasses() {
        // TODO Auto-generated method stub
        return null;
    }
}
