package com.inepex.ineForm.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.inepex.ineForm.client.datamanipulator.DataManipulator;
import com.inepex.ineForm.client.datamanipulator.ManipulatorFactory;
import com.inepex.ineForm.client.datamanipulator.RowCommandDataManipulator;
import com.inepex.ineForm.client.datamanipulator.SingleSelectDataManipulator;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.form.DefaultValueRangeProvider;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.client.form.RestValueRangeProvider;
import com.inepex.ineForm.client.form.SaveCancelForm;
import com.inepex.ineForm.client.form.SearchForm;
import com.inepex.ineForm.client.form.ValueRangeProviderFactory;
import com.inepex.ineForm.client.form.WizardForm;
import com.inepex.ineForm.client.form.factories.DefaultFormUnitFactory;
import com.inepex.ineForm.client.form.factories.DefaultFormWidgetFactory;
import com.inepex.ineForm.client.form.factories.DefaultPanelWidgetFactory;
import com.inepex.ineForm.client.form.factories.FormUnitFactory;
import com.inepex.ineForm.client.form.factories.FormWidgetFactory;
import com.inepex.ineForm.client.form.factories.PanelWidgetFactory;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.client.table.RestDataConnector;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.client.util.CETDateProviderCln;
import com.inepex.ineForm.client.util.GwtRequestBuilderFactory;
import com.inepex.ineForm.client.util.RequestBuilderFactory;
import com.inepex.ineForm.shared.dispatch.ObjectFinderRest;
import com.inepex.ineForm.shared.dispatch.ObjectFinderRestFactory;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.async.FullscreenStatusIndicator;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.pushedevents.PushedEventProvider;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class IneFormGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(HistoryProvider.class).in(Singleton.class);
		bind(IneDispatch.class).in(Singleton.class);
		bind(PushedEventProvider.class).in(Singleton.class);
		bind(DateProvider.class).to(CETDateProviderCln.class).in(Singleton.class);
		
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(DescriptorStore.class).to(ClientDescriptorStore.class).in(Singleton.class);
		
		bind(FormContext.class);
		
		bind(FormWidgetFactory.class).to(DefaultFormWidgetFactory.class).in(Singleton.class);
		bind(FormUnitFactory.class).to(DefaultFormUnitFactory.class).in(Singleton.class);
		bind(PanelWidgetFactory.class).to(DefaultPanelWidgetFactory.class).in(Singleton.class);

		bind(AsyncStatusIndicator.class).to(FullscreenStatusIndicator.class).in(Singleton.class);
		bind(ValueRangeProvider.class).to(DefaultValueRangeProvider.class).in(Singleton.class);
		
		bind(RequestBuilderFactory.class).to(GwtRequestBuilderFactory.class).in(Singleton.class);
		
		install(new GinFactoryModuleBuilder()
					.implement(IneForm.class, Names.named("simple"), IneForm.class)
					.implement(IneForm.class, Names.named("saveCancel"), SaveCancelForm.class)
					.implement(IneForm.class, Names.named("wizard"), WizardForm.class)
					.implement(IneForm.class, Names.named("search"), SearchForm.class)
					.build(FormFactory.class));
		
		install(new GinFactoryModuleBuilder()
				 	.implement(IneDataConnector.class, Names.named("serverside"), ServerSideDataConnector.class)
				 	.implement(IneDataConnector.class, Names.named("rest"), RestDataConnector.class)
					.build(DataConnectorFactory.class));
		
		install(new GinFactoryModuleBuilder()
				 	.implement(DataManipulator.class, Names.named("rowCommand"), RowCommandDataManipulator.class)
				 	.implement(DataManipulator.class, Names.named("singleSelect"), SingleSelectDataManipulator.class)
					.build(ManipulatorFactory.class));
		
		install(new GinFactoryModuleBuilder()
	 	.implement(ValueRangeProvider.class, Names.named("default"), DefaultValueRangeProvider.class)
	 	.implement(ValueRangeProvider.class, Names.named("rest"), RestValueRangeProvider.class)
		.build(ValueRangeProviderFactory.class));
		
		install(new GinFactoryModuleBuilder()
	 	.implement(ObjectFinderRest.class, ObjectFinderRest.class)
		.build(ObjectFinderRestFactory.class));
		
	}
	
	
	

}
