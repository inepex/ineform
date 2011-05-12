package com.inepex.ineForm.client.form;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.form.factories.FormUnitFactory;
import com.inepex.ineForm.client.form.factories.FormWidgetFactory;
import com.inepex.ineForm.client.form.factories.PanelWidgetFactory;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

/**
 * This class hold all context dependencies that is needed to create forms or tables.<br/>
 * When using GIN dependencies are injected at creation time. This class should not be bound in singleton context,
 * as the {@link ValueRangeProvider} can be different for different forms.
 *  
 * @author istvanszoboszlai
 *
 */
public class FormContext {

	// General setup specific field
	public final DescriptorStore descStore;
	public final EventBus eventBus;
	public final IneDispatch ineDispatch;

	public final FormWidgetFactory formWidgetFactory;
	public final FormUnitFactory formUnitFactory;
	public final PanelWidgetFactory panelWidgetFactory;
	public final KeyValueObjectValidationManager validatorManager;
	public final DateProvider dateProvider;
	
	/**
	 * ValueRangeProvider can be overridden in a specific context. 
	 */
	public ValueRangeProvider valueRangeProvider;

	@Inject
	public FormContext(DescriptorStore descStore
			, EventBus eventBus
			, IneDispatch ineDispatch
			, ValueRangeProvider defaultValueRangeProvider
			, FormWidgetFactory formWidgetFactory
			, FormUnitFactory formUnitFactory
			, PanelWidgetFactory panelWidgetFactory
			, KeyValueObjectValidationManager validatorManager
			, DateProvider dateProvider) {
		super();
		this.descStore = descStore;
		this.eventBus = eventBus;
		this.valueRangeProvider = defaultValueRangeProvider;
		this.formWidgetFactory = formWidgetFactory;
		this.formUnitFactory = formUnitFactory;
		this.ineDispatch = ineDispatch;
		this.panelWidgetFactory = panelWidgetFactory;
		this.validatorManager = validatorManager;
		this.dateProvider = dateProvider;
	}	
	
}
