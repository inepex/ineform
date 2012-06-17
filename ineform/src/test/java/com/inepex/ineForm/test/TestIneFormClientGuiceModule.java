package com.inepex.ineForm.test;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;
import com.inepex.ineForm.client.datamanipulator.DataManipulator;
import com.inepex.ineForm.client.datamanipulator.ManipulatorFactory;
import com.inepex.ineForm.client.datamanipulator.RowCommandDataManipulator;
import com.inepex.ineForm.client.datamanipulator.SingleSelectDataManipulator;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.client.form.SaveCancelForm;
import com.inepex.ineForm.client.form.SearchForm;
import com.inepex.ineForm.client.form.ServerSideValueRangeProvider;
import com.inepex.ineForm.client.form.WizardForm;
import com.inepex.ineForm.client.form.factories.DefaultFormUnitFactory;
import com.inepex.ineForm.client.form.factories.DefaultFormWidgetFactory;
import com.inepex.ineForm.client.form.factories.DefaultPanelWidgetFactory;
import com.inepex.ineForm.client.form.factories.FormUnitFactory;
import com.inepex.ineForm.client.form.factories.FormWidgetFactory;
import com.inepex.ineForm.client.form.factories.PanelWidgetFactory;
import com.inepex.ineForm.client.form.widgets.customkvo.ActionBasedOdFinder;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFW;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFWView;
import com.inepex.ineForm.client.form.widgets.customkvo.OdFinder;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.client.util.GwtRequestBuilderFactory;
import com.inepex.ineForm.client.util.RequestBuilderFactory;
import com.inepex.ineForm.shared.dispatch.ActionBasedObjectFinder;
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.pushedevents.PushedEventProvider;
import com.inepex.ineFrame.server.util.CETDateProviderSrv;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineFrame.test.DummyStatusIndicator;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class TestIneFormClientGuiceModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(OdFinder.class).to(ActionBasedOdFinder.class);
		bind(ObjectFinder.class).to(ActionBasedObjectFinder.class);
		
		bind(HistoryProvider.class).in(Singleton.class);
		bind(IneDispatch.class).in(Singleton.class);
		bind(PushedEventProvider.class).in(Singleton.class);
		bind(DateProvider.class).to(CETDateProviderSrv.class).in(Singleton.class);
				
		bind(EventBus.class).to(SimpleEventBus.class);
		bind(DescriptorStore.class).to(ClientDescriptorStore.class).in(Singleton.class);
		
		bind(FormContext.class);
		
		bind(FormWidgetFactory.class).to(DefaultFormWidgetFactory.class).in(Singleton.class);
		bind(FormUnitFactory.class).to(DefaultFormUnitFactory.class).in(Singleton.class);
		bind(PanelWidgetFactory.class).to(DefaultPanelWidgetFactory.class).in(Singleton.class);

		bind(AsyncStatusIndicator.class).to(DummyStatusIndicator.class).in(Singleton.class);
		bind(ValueRangeProvider.class).to(ServerSideValueRangeProvider.class).in(Singleton.class);
		bind(RequestBuilderFactory.class).to(GwtRequestBuilderFactory.class).in(Singleton.class);
		
		install(new FactoryModuleBuilder()
					.implement(IneForm.class, Names.named("simple"), IneForm.class)
					.implement(IneForm.class, Names.named("saveCancel"), SaveCancelForm.class)
					.implement(IneForm.class, Names.named("wizard"), WizardForm.class)
					.implement(IneForm.class, Names.named("search"), SearchForm.class)
					.build(FormFactory.class));
		
		install(new FactoryModuleBuilder()
				 	.implement(IneDataConnector.class, ServerSideDataConnector.class)
					.build(DataConnectorFactory.class));

		install(new FactoryModuleBuilder()
				 	.implement(DataManipulator.class, Names.named("rowCommand"), RowCommandDataManipulator.class)
				 	.implement(DataManipulator.class, Names.named("singleSelect"), SingleSelectDataManipulator.class)
					.build(ManipulatorFactory.class));
		
		bind(CustomKVOFW.View.class).to(CustomKVOFWView.class);
		
	}
	
	@Provides
	public DispatchAsync getDispatchAsync() {
		return new StandardDispatchAsync(new DefaultExceptionHandler());
	}
}