package com.inepex.ineForm.client.form;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.form.factories.FormUnitFactory;
import com.inepex.ineForm.client.form.factories.FormWidgetFactory;
import com.inepex.ineForm.client.form.factories.PanelWidgetFactory;
import com.inepex.ineForm.client.form.prop.PropFW;
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineFrame.shared.util.date.DateFormatter;
import com.inepex.ineFrame.shared.util.date.DateProvider;
import com.inepex.ineom.shared.AssistedObjectDifference;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
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
	public final ObjectFinder objectFinder;
	public final Provider<PropFW.View> propFwViewProvider;
	public final DateFormatter dateFormatter;
	public final NumberUtil numberUtil;
	public final AssistedObjectDifference aoDifference;
	
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
			, DateProvider dateProvider
			, ObjectFinder objectFinder
			, DateFormatter dateFormatter
			, NumberUtil numberUtil
			, Provider<PropFW.View> propFwViewProvider,
			AssistedObjectDifference aoDifference) {
		this.objectFinder=objectFinder;
		this.descStore = descStore;
		this.eventBus = eventBus;
		this.valueRangeProvider = defaultValueRangeProvider;
		this.formWidgetFactory = formWidgetFactory;
		this.formUnitFactory = formUnitFactory;
		this.ineDispatch = ineDispatch;
		this.panelWidgetFactory = panelWidgetFactory;
		this.validatorManager = validatorManager;
		this.dateProvider = dateProvider;
		this.dateFormatter = dateFormatter;
		this.numberUtil = numberUtil;
		this.propFwViewProvider = propFwViewProvider;
		this.aoDifference = aoDifference;
	}	
	
}
