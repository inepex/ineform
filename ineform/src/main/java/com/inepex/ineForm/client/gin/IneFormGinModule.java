package com.inepex.ineForm.client.gin;

import net.customware.gwt.dispatch.client.gin.StandardDispatchModule;

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
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.client.form.RestValueRangeProvider;
import com.inepex.ineForm.client.form.SaveCancelForm;
import com.inepex.ineForm.client.form.SearchForm;
import com.inepex.ineForm.client.form.ServerSideValueRangeProvider;
import com.inepex.ineForm.client.form.ValueRangeProviderFactory;
import com.inepex.ineForm.client.form.WizardForm;
import com.inepex.ineForm.client.form.factories.DefaultFormUnitFactory;
import com.inepex.ineForm.client.form.factories.DefaultFormWidgetFactory;
import com.inepex.ineForm.client.form.factories.DefaultPanelWidgetFactory;
import com.inepex.ineForm.client.form.factories.FormUnitFactory;
import com.inepex.ineForm.client.form.factories.FormWidgetFactory;
import com.inepex.ineForm.client.form.factories.PanelWidgetFactory;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFW;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFWView;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.client.table.IneTable;
import com.inepex.ineForm.client.table.IneTableFactory;
import com.inepex.ineForm.client.table.RestDataConnector;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.client.table.SortableIneTable;
import com.inepex.ineForm.client.util.GwtDateFormatter;
import com.inepex.ineForm.client.util.GwtRequestBuilderFactory;
import com.inepex.ineForm.client.util.NumberUtilCln;
import com.inepex.ineForm.client.util.RequestBuilderFactory;
import com.inepex.ineForm.shared.dispatch.ObjectFinderRest;
import com.inepex.ineForm.shared.dispatch.ObjectFinderRestFactory;
import com.inepex.ineForm.shared.tablerender.CsvRenderer;
import com.inepex.ineForm.shared.tablerender.CsvRenderer.CsvRendererFactory;
import com.inepex.ineForm.shared.tablerender.HtmlRenderer;
import com.inepex.ineForm.shared.tablerender.HtmlRenderer.HtmlRendererFactory;
import com.inepex.ineForm.shared.tablerender.TrtdRenderer;
import com.inepex.ineForm.shared.tablerender.TrtdRenderer.TrtdRendererFactory;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.async.ConnectionFailedHandler;
import com.inepex.ineFrame.client.async.DefaultFailedHandler;
import com.inepex.ineFrame.client.async.FullscreenStatusIndicator;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.pushedevents.PushedEventProvider;
import com.inepex.ineFrame.client.util.CETDateProviderCln;
import com.inepex.ineFrame.client.util.GwtNumberFormatter;
import com.inepex.ineFrame.shared.util.DateFormatter;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineFrame.shared.util.NumberFormatter;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class IneFormGinModule extends AbstractGinModule {

	private Class<? extends AsyncStatusIndicator> asyncStatusIndicator = FullscreenStatusIndicator.class;
	private Class<? extends ConnectionFailedHandler> connectionFailedHandler = DefaultFailedHandler.class;
	private Class<? extends FormWidgetFactory> formWidgetFactory = DefaultFormWidgetFactory.class;
	private Class<? extends DateProvider> dateProvider = CETDateProviderCln.class;
	private Class<? extends FormUnitFactory> formUnitFactory = DefaultFormUnitFactory.class;
	
	public IneFormGinModule() {
	}

	public IneFormGinModule setAsyncStatusIndicator(Class<? extends AsyncStatusIndicator> asyncStatusIndicator) {
		this.asyncStatusIndicator = asyncStatusIndicator;
		return this;
	}

	public IneFormGinModule setConnectionFailedHandler(Class<? extends ConnectionFailedHandler> connectionFailedHandler) {
		this.connectionFailedHandler = connectionFailedHandler;
		return this;
	}

	public IneFormGinModule setFormWidgetFactory(Class<? extends FormWidgetFactory> formWidgetFactory) {
		this.formWidgetFactory = formWidgetFactory;
		return this;
	}
	
	public IneFormGinModule setDateProvider(Class<? extends DateProvider> dateProvider) {
		this.dateProvider = dateProvider;
		return this;
	}
	
	public IneFormGinModule setFormUnitFactory(
			Class<? extends FormUnitFactory> formUnitFactory) {
		this.formUnitFactory = formUnitFactory;
		return this;
	}

	@Override
	protected void configure() {
		install(new StandardDispatchModule());
		
		bind(HistoryProvider.class).in(Singleton.class);
		bind(PushedEventProvider.class).in(Singleton.class);
		bind(DateFormatter.class).to(GwtDateFormatter.class);
		bind(NumberFormatter.class).to(GwtNumberFormatter.class);
		bind(NumberUtil.class).to(NumberUtilCln.class);
		
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(DescriptorStore.class).to(ClientDescriptorStore.class).in(Singleton.class);
		
		bind(FormContext.class);
		
		bind(FormWidgetFactory.class).to(formWidgetFactory).in(Singleton.class);
		
		bind(FormUnitFactory.class).to(formUnitFactory).in(Singleton.class);
		bind(PanelWidgetFactory.class).to(DefaultPanelWidgetFactory.class).in(Singleton.class);
		
		bind(RequestBuilderFactory.class).to(GwtRequestBuilderFactory.class).in(Singleton.class);
		bind(DateProvider.class).to(dateProvider).in(Singleton.class);
		
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
	 	.implement(ValueRangeProvider.class, Names.named("default"), ServerSideValueRangeProvider.class)
	 	.implement(ValueRangeProvider.class, Names.named("rest"), RestValueRangeProvider.class)
		.build(ValueRangeProviderFactory.class));
		
		install(new GinFactoryModuleBuilder()
	 	.implement(ObjectFinderRest.class, ObjectFinderRest.class)
		.build(ObjectFinderRestFactory.class));
		
		
		bind(CustomKVOFW.View.class).to(CustomKVOFWView.class);

		install(new GinFactoryModuleBuilder()
	 	.implement(CsvRenderer.class, CsvRenderer.class)
		.build(CsvRendererFactory.class));
		
		install(new GinFactoryModuleBuilder()
	 	.implement(TrtdRenderer.class, TrtdRenderer.class)
		.build(TrtdRendererFactory.class));
		
		install(new GinFactoryModuleBuilder()
	 	.implement(HtmlRenderer.class, HtmlRenderer.class)
		.build(HtmlRendererFactory.class));
		
		install(new GinFactoryModuleBuilder()
	 	.implement(IneTable.class, Names.named("simple"), IneTable.class)
	 	.implement(IneTable.class, Names.named("simple2"), IneTable.class)
	 	.implement(IneTable.class, Names.named("sortable"), SortableIneTable.class)
	 	.implement(IneTable.class, Names.named("sortable2"), SortableIneTable.class)
		.build(IneTableFactory.class));
		
		
		bind(AsyncStatusIndicator.class).to(asyncStatusIndicator).in(Singleton.class);
		bind(ConnectionFailedHandler.class).to(connectionFailedHandler).in(Singleton.class);
	}
	
	
	

}
