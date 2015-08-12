package com.inepex.ineForm.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.form.ServerSideValueRangeProvider;
import com.inepex.ineForm.shared.dispatch.ActionBasedObjectFinder;
import com.inepex.ineForm.shared.dispatch.ActionObjectFactory;
import com.inepex.ineForm.shared.dispatch.ManipulationObjectFactory;
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
import com.inepex.ineFrame.client.async.IneDispatch;

public class IneFormDispatcherGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ValueRangeProvider.class).to(ServerSideValueRangeProvider.class).in(Singleton.class);
        bind(ManipulationObjectFactory.class).to(ActionObjectFactory.class);
        bind(IneDispatch.class).in(Singleton.class);
        bind(ObjectFinder.class).to(ActionBasedObjectFinder.class);
    }

}
