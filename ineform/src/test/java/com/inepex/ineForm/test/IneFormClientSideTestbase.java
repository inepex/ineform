package com.inepex.ineForm.test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.inepex.ineFrame.test.IneFrameClientSideTestBase;

public abstract class IneFormClientSideTestbase extends IneFrameClientSideTestBase {

    private Injector defaultInjector;

    public Injector getDefaultInjector() {
        if (defaultInjector == null) {
            defaultInjector = Guice.createInjector(new TestIneFormClientGuiceModule());
        }
        return defaultInjector;
    }

}
