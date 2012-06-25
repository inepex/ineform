package com.inepex.ineForm.client.form;

import static com.inepex.ineom.shared.util.SharedUtil.listFromDotSeparated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.form.SaveCancelForm.ValidateMode;
import com.inepex.ineForm.client.form.events.BeforeRenderEvent;
import com.inepex.ineForm.client.form.events.FilledWithDataEvent;
import com.inepex.ineForm.client.form.events.FormLifecycleEventBase;
import com.inepex.ineForm.client.form.events.RenderedEvent;
import com.inepex.ineForm.client.form.events.ResetEvent;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.client.form.panelwidgets.DisplayedFormUnitChangeHandler;
import com.inepex.ineForm.client.form.panelwidgets.PanelWidget;
import com.inepex.ineForm.client.form.widgets.FormWidget;
import com.inepex.ineForm.client.form.widgets.RelationList;
import com.inepex.ineForm.client.form.widgets.RelationListFW;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFW;
import com.inepex.ineForm.client.general.SimpleTableErrorMessageManager;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDescBase;
import com.inepex.ineForm.shared.descriptorext.FormUnitRDesc;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.ListFDesc;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.util.SharedUtil;
import com.inepex.ineom.shared.validation.KeyValueObjectValidator;
import com.inepex.ineom.shared.validation.ValidationResult;

public class IneForm implements DisplayedFormUnitChangeHandler {
	
	protected PanelWidget rootPanel;
	private final FlowPanel mainPanel = new FlowPanel();
	FlowPanel generalErrorPanel = new FlowPanel();

	private SimpleTableErrorMessageManager defaultErrorMessageManager;

	// forms paramteres
	protected final String descriptorName;
	protected String formRenderDescName;
	protected FormRDesc formRenderDescriptor;
	protected ObjectDesc objectDescriptor;
	protected ValueRangeProvider valueRangeProvider;
	
	// dependences
	protected final EventBus eventBus;
	protected final DescriptorStore descStore;
	protected final FormContext formCtx; 
	protected final AssistedObjectHandlerFactory handlerFactory;
	
	// named panels and forms (because its interesting for programmer)
	protected HashMap<String, PanelWidget> panels = new HashMap<String, PanelWidget>();
	protected HashMap<String, AbstractFormUnit> forms = new HashMap<String, AbstractFormUnit>();

	// data flow
	protected AssistedObject initialData;
	private boolean filledWithInitialData = false;

	private CustomCode custCode = null;
	
	private ValidateMode validateData = ValidateMode.ALL;
	
	private Multimap<String, KeyValueObjectValidator> customValidators = 
			ArrayListMultimap.create();

	/**
	 * Uses default modelKeyPrefix, default formRenderDescriptor
	 * 
	 * @param descriptorName
	 */
	@Inject
	public IneForm(FormContext formCtx,
			@Assisted("dn") String descriptorName,
			@Assisted("frdn") String formRDescName) {
		this.descStore = formCtx.descStore;
		this.descriptorName = descriptorName;
		this.formRenderDescName = formRDescName;
		this.valueRangeProvider = formCtx.valueRangeProvider;
		this.eventBus = formCtx.eventBus;
		this.formCtx = formCtx;
		this.handlerFactory=new AssistedObjectHandlerFactory(descStore);
	}
	
	public void setValueRangeProvider(ValueRangeProvider valueRangeProvider) {
		this.valueRangeProvider = valueRangeProvider;
	}

	
	private void initProperties() {

		if (formRenderDescName == null)
			formRenderDescName = DescriptorStore.DEFAULT_DESC_KEY;
		if (DescriptorStore.DEFAULT_DESC_KEY.equals(formRenderDescName)) {
			this.formRenderDescriptor = descStore.getDefaultTypedDesc(descriptorName, FormRDesc.class);
		} else {
			this.formRenderDescriptor = descStore.getNamedTypedDesc(descriptorName, formRenderDescName, FormRDesc.class);
		}
		this.objectDescriptor = descStore.getOD(descriptorName);
		if (formRenderDescriptor == null) throw new RuntimeException("Formrenderdescriptor [" + formRenderDescName +
				"] for Objectdescriptor [" + descriptorName + "] not found. You may forget to register it in AppDispatchServlet!");
	}

	private void addErrorLableHidden() {
		defaultErrorMessageManager = new SimpleTableErrorMessageManager(generalErrorPanel.getElement());
		mainPanel.add(generalErrorPanel);
		generalErrorPanel.setVisible(false);
	}

	public void renderForm() {
		initProperties();
		addErrorLableHidden();
		
		fireBeforeRenderEvent();

		List<Node<FormRDescBase>> formRDescChildren = formRenderDescriptor.getRootNode()
				.getChildren();

		if (formRDescChildren == null || formRDescChildren.size() == 0 
			|| formRDescChildren.get(0).getNodeElement() == null) {
			throw new RuntimeException(
					"Ineform:renderForm: invalid FormRenderDescriptor");
		}

		if (formRDescChildren.size() == 1
				&& formRDescChildren.get(0).getNodeElement() instanceof PanelWidgetRDesc) {
			rootPanel = createPanelAndAddToParent(null, formRDescChildren.get(0));
		} else {
			rootPanel = createPanelAndAddToParent(formRDescChildren);
		}

		mainPanel.add(rootPanel);

		fireRenderedEvent();
	}

	private void process(PanelWidget parentPanel,
			List<Node<FormRDescBase>> nodesToProcess) {
		List<Node<FormRDescBase>> standaloneWRDs = new ArrayList<Node<FormRDescBase>>();

		if (nodesToProcess != null && nodesToProcess.size() > 0) {
			for (Node<FormRDescBase> node : nodesToProcess) {
				if (node.getNodeElement() == null)
					continue;
				if (node.getNodeElement() instanceof PanelWidgetRDesc) {

					// collect widgets which haven't form parent
					createFormAndAddToParent(parentPanel, standaloneWRDs);
					standaloneWRDs.clear();

					createPanelAndAddToParent(parentPanel, node);
					continue;
				}

				if (node.getNodeElement() instanceof FormUnitRDesc) {
					// collect widgets which haven't form parent
					createFormAndAddToParent(parentPanel, standaloneWRDs);
					standaloneWRDs.clear();

					createFormAndAddToParent(parentPanel, node);
					continue;
				}

				if (node.getNodeElement() instanceof WidgetRDesc) {
					standaloneWRDs.add(node);
					continue;
				}
			}
		}

		// collect widgets that does not have form parent
		createFormAndAddToParent(parentPanel, standaloneWRDs);
		standaloneWRDs.clear();
	}

	/**
	 * @param children
	 * @param paneldescNode
	 * @return
	 */
	private PanelWidget createPanelAndAddToParent(PanelWidget parentPanel,
			Node<FormRDescBase> paneldescNode) {

		PanelWidget panel = formCtx.panelWidgetFactory.createPanel(
				(PanelWidgetRDesc) paneldescNode.getNodeElement(), parentPanel,
				parentPanel == null ? this : parentPanel);
		if (!paneldescNode.hasDefaultId())
			panels.put(paneldescNode.getNodeId(), panel);
		process(panel, paneldescNode.getChildren());
		if (parentPanel != null)
			parentPanel.addToPanel(panel);
		return panel;

	}

	/**
	 * for root
	 */
	private PanelWidget createPanelAndAddToParent(
			List<Node<FormRDescBase>> children) {
		PanelWidget panel = formCtx.panelWidgetFactory.createPanel(
				null, null, this);
		process(panel, children);
		return panel;
	}

	/**
	 * 
	 * @param parentPanel
	 * @param widgetDescriptorNodes
	 */
	private void createFormAndAddToParent(PanelWidget parentPanel,
			Node<FormRDescBase> formdescNode) {
		if (formdescNode.getChildren() == null
				|| formdescNode.getChildren().size() == 0)
			return;

		AbstractFormUnit form = formCtx.formUnitFactory.createFormUnit(formCtx,
				(FormUnitRDesc) formdescNode.getNodeElement(), descriptorName,
				formdescNode.getChildren());

		if (!formdescNode.hasDefaultId())
			forms.put(formdescNode.getNodeId(), form);
		parentPanel.addToPanel(form);
		parentPanel.regFormUnit(form);
	}

	/**
	 * forms without descriptor
	 */
	private void createFormAndAddToParent(PanelWidget parentPanel,
			List<Node<FormRDescBase>> children) {
		if (children == null || children.size() == 0)
			return;

		AbstractFormUnit form = formCtx.formUnitFactory.createFormUnit(formCtx,
				null, descriptorName, children);

		parentPanel.addToPanel(form);
		parentPanel.regFormUnit(form);
	}

	public Widget asWidget() {
		return mainPanel;
	}

	public PanelWidget getRootPanelWidget() {
		return rootPanel;
	}

	public void setCustomCode(CustomCode code) {
		this.custCode = code;
	}

	// ----------------------- event handlers
	// -------------------------------------------------------

	public HandlerRegistration addBeforeRenderHandler(
			BeforeRenderEvent.Handler handler) {
		return mainPanel.addHandler(handler, BeforeRenderEvent.getType());
	}

	public HandlerRegistration addRenderedHandler(RenderedEvent.Handler handler) {
		return mainPanel.addHandler(handler, RenderedEvent.getType());
	}

	public HandlerRegistration addFilledWithDataHandler(
			FilledWithDataEvent.Handler handler) {
		return mainPanel.addHandler(handler, FilledWithDataEvent.getType());
	}

	public HandlerRegistration addResetHandler(ResetEvent.Handler handler) {
		return mainPanel.addHandler(handler, ResetEvent.getType());
	}

	public BeforeRenderEvent fireBeforeRenderEvent() {
		return doFireEvent(new BeforeRenderEvent());
	}

	public FilledWithDataEvent fireFilledWithDataEvent(AssistedObject newData) {
		return doFireEvent(new FilledWithDataEvent(newData));
	}

	public RenderedEvent fireRenderedEvent() {
		return doFireEvent(new RenderedEvent());
	}

	public ResetEvent fireResetEvent() {
		return doFireEvent(new ResetEvent());
	}

	private <T extends EventHandler, E extends FormLifecycleEventBase<T>> E doFireEvent(
			E event) {
		mainPanel.fireEvent(event);
		return event;
	}

	/**
	 * notice that exForms will be not hided till you invoke onSuccess on
	 * responseObject
	 */
	@Override
	public void onDisplayedFormUnitChange(PanelWidget sourcePanel,
			List<AbstractFormUnit> exForms,
			DisplayedFormUnitChangeResponse<?> responseObject) {
		if (custCode == null)
			responseObject.onSuccess();
		else
			custCode.execute(this, sourcePanel, exForms, responseObject);
	}

	public void setEnabled(boolean enabled) {
		for (AbstractFormUnit form : getRootPanelWidget().getFormUnits()) {
			form.setEnabled(enabled);
		}
	}

	@Override
	public void onPanelWidgetRefreshed(PanelWidget sourcePanel) {
	}
	
	public void clearValidationMessages() {
		dealValidationResult(null);
	}
	
	public void dealValidationResult(ValidationResult vr) {

		// clean prev errors
		for (AbstractFormUnit unit : getRootPanelWidget().getFormUnits()) {
			for (String modelKey : unit.getErrorManagerKeySet()) {
				unit.getErrormanagersByKey().get(modelKey).clearErrorMsg();
			}
			for(String modelKey : unit.getFormWidgetKeySet()) {
				unit.getWidgetByKey(modelKey).removeStyleName(ResourceHelper.ineformRes().style().formWidgetError());
			}
		}

		// deal new error msgs
		if (vr != null && !vr.isValid() && vr.getFieldErrors() != null) {
			for (AbstractFormUnit unit : getRootPanelWidget().getFormUnits()) {
				for (String modelKey : unit.getErrorManagerKeySet()) {
					unit.getErrormanagersByKey().get(modelKey)
							.addErrorMsg(vr.getFieldErrors().get(modelKey));
				}
				for(String modelKey : unit.getFormWidgetKeySet()) {
					if(vr.getFieldErrors().containsKey(modelKey))
						unit.getWidgetByKey(modelKey).addStyleName(ResourceHelper.ineformRes().style().formWidgetError());
				}
			}
		}

		// general errors
		List<String> generalErrors = vr == null ? null : vr.getGeneralErrors();
		generalErrorPanel.setVisible(generalErrors!=null && generalErrors.size()>0);
		
		defaultErrorMessageManager.clearErrorMsg();
		defaultErrorMessageManager.addErrorMsg(generalErrors);
	
		// display errors without place
		if (vr != null && !vr.isValid() && vr.getFieldErrors() != null) {
			Set<String> unTargettedKeys = new HashSet<String>(vr
					.getFieldErrors().keySet());
			for (AbstractFormUnit unit : getRootPanelWidget().getFormUnits()) {
				unTargettedKeys.removeAll(unit.getErrorManagerKeySet());
			}
			for (String key : unTargettedKeys) {
				Window.alert("There is an error which hase no place, key: "
						+ key + " value: " + vr.getFieldErrors().get(key));
			}
		}

	}

	// ----------------------- data management
	// -------------------------------------------------------

	public AssistedObject getInitialOrEmptyData() {
		if (initialData == null) {
			return new KeyValueObject(descriptorName);
		}

		return initialData;
	}

	/**
	 *  Important: Be aware of the differences to setData(...)
	 *  see javadoc of setData(...)
	 * @param data
	 */
	public void setInitialData(AssistedObject data) {

		if (data.getDescriptorName() == null
				|| !descriptorName.equals(data.getDescriptorName()))
			throw new IncompatibleObjectException(
					"setValues: the given AssistedObject's type is not the same as the one provided when constructing the form");

		filledWithInitialData = true;
		initialData = data;
		AssistedObjectHandler dataHandler = handlerFactory.createHandler(data);

		for (AbstractFormUnit form : getRootPanelWidget().getFormUnits()) {
			for (String key : form.getFormWidgetKeySet()) {
				FormWidget widget = form.getWidgetByKey(key);
				
				String lastKey = null;
				AssistedObjectHandler actual = null;
				IneT type = null;
				
				if(SharedUtil.isMultilevelKey(key)) {
					List<String> keyAsList = listFromDotSeparated(key);
					lastKey = keyAsList.get(keyAsList.size() - 1);
					actual = dataHandler.getRelatedKVOMultiLevel(keyAsList);
					type = descStore.getRelatedFieldDescrMultiLevel(objectDescriptor, keyAsList).getType();
				} else {
					lastKey = key;
					actual = dataHandler;
					type = objectDescriptor.getField(key).getType();
				}

				try {
					switch (type) {
					case BOOLEAN:
						if (widget.handlesBoolean())
							widget.setBooleanValue(actual.getBoolean(lastKey));
						break;
					case DOUBLE:
						if (widget.handlesDouble())
							widget.setDoubleValue(actual.getDouble(lastKey));
						break;
					case LIST:
						if (widget.handlesList())
							widget.setListValue(actual.getList(lastKey));
						break;
					case LONG:
						if (widget.handlesLong())
							widget.setLongValue(actual.getLong(lastKey));
						break;
					case RELATION:
						if (widget.handlesRelation())
							widget.setRelationValue(actual.getRelation(lastKey));
						break;
					case STRING:
						if (widget.handlesString())
							widget.setStringValue(actual.getString(lastKey));
						break;
					default:
						break;
					}

				} catch (Exception e) {
					System.out.println("Descriptor: " + descriptorName
							+ " Key: " + key);
					e.printStackTrace();
				}
			}
		}

		fireFilledWithDataEvent(data);
	}
	
	public void setData(AssistedObject data, AbstractFormUnit form, boolean fireEvent) {
		
		if (data.getDescriptorName() == null
				|| !descriptorName.equals(data.getDescriptorName()))
			throw new IncompatibleObjectException(
					"setValues: the given AssistedObject's type is not the same as the one provided when constructing the form");
		
		AssistedObjectHandler dataHandler = handlerFactory.createHandler(data);
		
		for (String key : form.getFormWidgetKeySet()) {
			FormWidget widget = form.getWidgetByKey(key);
			
			String lastKey = null;
			AssistedObjectHandler actual = null;
			IneT type = null;
			
			if(SharedUtil.isMultilevelKey(key)) {
				List<String> keyAsList = listFromDotSeparated(key);
				lastKey = keyAsList.get(keyAsList.size() - 1);
				actual = dataHandler.getRelatedKVOMultiLevel(keyAsList);
				type = descStore.getRelatedFieldDescrMultiLevel(objectDescriptor, keyAsList).getType();
			} else {
				lastKey = key;
				actual = dataHandler;
				type = objectDescriptor.getField(key).getType();
			}

			try {
				switch (type) {
				case BOOLEAN:
					if (widget.handlesBoolean() && actual.containsBoolean(lastKey))
						widget.setBooleanValue(actual.getBoolean(lastKey));
					break;
				case DOUBLE:
					if (widget.handlesDouble() && actual.containsDouble(lastKey))
						widget.setDoubleValue(actual.getDouble(lastKey));
					break;
				case LIST:
					if (widget.handlesList() && actual.containsList(lastKey))
						widget.setListValue(actual.getList(lastKey));
					break;
				case LONG:
					if (widget.handlesLong() && actual.containsLong(lastKey))
						widget.setLongValue(actual.getLong(lastKey));
					break;
				case RELATION:
					if (widget.handlesRelation() && actual.containsRelation(lastKey))
						widget.setRelationValue(actual.getRelation(lastKey));
					break;
				case STRING:
					if (widget.handlesString() && actual.containsString(lastKey))
						widget.setStringValue(actual.getString(lastKey));
					break;
				default:
					break;
				}

			} catch (Exception e) {
				System.out.println("Descriptor: " + descriptorName
						+ " Key: " + key + " Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		if(fireEvent)
			fireFilledWithDataEvent(data);
	}
	
	/**
	 * Important: Be aware of the differences to setInitialData(...)
	 * When using setData() the difference produced from the form when saving will contain these values, too!
	 * @param data
	 */
	public void setData(AssistedObject data, boolean fireEvent) {
		
		if (data.getDescriptorName() == null
				|| !descriptorName.equals(data.getDescriptorName()))
			throw new IncompatibleObjectException(
					"setValues: the given AssistedObject's type is not the same as the one provided when constructing the form");

		for (AbstractFormUnit form : getRootPanelWidget().getFormUnits()) {
			setData(data, form, false);
		}
		
		if(fireEvent)
			fireFilledWithDataEvent(data);
	}

	/**
	 * fill a KayValueObject with data from the forms
	 * 
	 * @param objectToFill
	 *            - the method write into this object the form's data
	 * 
	 * @return objectToFill
	 */
	public AssistedObject getValues(AssistedObject objectToFill) {
		if (objectToFill.getDescriptorName() == null
				|| !descriptorName.equals(objectToFill.getDescriptorName()))
			throw new IncompatibleObjectException(
					"getValues: the given AssistedObject's type is not the same as the one provided when constructing the form");

		for (AbstractFormUnit form : getRootPanelWidget().getFormUnits()) {
			getValuesOfFormUnit(objectToFill, form);
		}

		return objectToFill;
	}

	public AbstractFormUnit getFormUnitByName(String name) {
		return forms.get(name);
	}

	public PanelWidget getPanelWidgetByName(String name) {
		return panels.get(name);
	}

	public AssistedObject getValuesOfFormUnit(AssistedObject objectToFill,
			AbstractFormUnit formUnit) {
		
		AssistedObjectHandler objectHandler = handlerFactory.createHandler(objectToFill);
		for (String key : formUnit.getFormWidgetKeySet()) {
			FormWidget widget = formUnit.getWidgetByKey(key);
			
			String lastKey = null;
			AssistedObjectHandler actual = null;
			IneT type = null;
			
			if(SharedUtil.isMultilevelKey(key)) {
				List<String> keyAsList = listFromDotSeparated(key);
				lastKey = keyAsList.get(keyAsList.size() - 1);
				actual = objectHandler.getRelatedKVOMultiLevel(keyAsList);
				type = descStore.getRelatedFieldDescrMultiLevel(objectDescriptor, keyAsList).getType();
			} else {
				lastKey = key;
				actual = objectHandler;
				type = objectDescriptor.getField(key).getType();
			}
			
			if (widget.isReadOnlyWidget())
				continue;
			
			try {
				switch (type) {
				case BOOLEAN:
					actual.set(lastKey,
							widget.getBooleanValue());
					break;
				case DOUBLE:
					actual.set(lastKey,
							widget.getDoubleValue());
					break;
				case LIST:
					actual.set(lastKey, widget.getListValue());
					break;
				case LONG:
					actual.set(lastKey, widget.getLongValue());
					break;
				case RELATION:
					actual.set(lastKey,
							widget.getRelationValue());
					break;
				case STRING:
					actual.set(lastKey,
							widget.getStringValue());
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objectToFill;
	}

	public boolean isFilledWithIntialData() {
		return filledWithInitialData;
	}

	/**
	 *  
	 *  reset values to empty kvo
	 *  clear validation result
	 *  
	 */
	public void resetValuesToEmpty() {
		setInitialData(new KeyValueObject(descriptorName));
		initialData=null;
		filledWithInitialData=false;
		
		dealValidationResult(null);
		fireResetEvent();
	}
	
	/**
	 *  
	 *  reset values to initial data (or empty kvo)
	 *  clear validation result
	 *  
	 */
	public void resetValuesToInitialData() {
		if(!isFilledWithIntialData()) {
			setInitialData(new KeyValueObject(descriptorName));
			initialData=null;
			filledWithInitialData=false;
		} else {
			setInitialData(getInitialOrEmptyData());
		}
			
		dealValidationResult(null);
		fireResetEvent();
	}
	
	public void setWidgetValue(String key, Boolean value) {
		for (AbstractFormUnit afu : getRootPanelWidget().getFormUnits()) {
			afu.setSingleWidgetValue(key, value);
		}
	}
	
	public void setWidgetValue(String key, Double value) {
		for (AbstractFormUnit afu : getRootPanelWidget().getFormUnits()) {
			afu.setSingleWidgetValue(key, value);
		}
	}
	
	public void setWidgetValue(String key, IneList value) {
		for (AbstractFormUnit afu : getRootPanelWidget().getFormUnits()) {
			afu.setSingleWidgetValue(key, value);
		}
	}
	
	public void setWidgetValue(String key, Long value) {
		for (AbstractFormUnit afu : getRootPanelWidget().getFormUnits()) {
			afu.setSingleWidgetValue(key, value);
		}
	}
	
	public void setWidgetValue(String key, Relation value) {
		for (AbstractFormUnit afu : getRootPanelWidget().getFormUnits()) {
			afu.setSingleWidgetValue(key, value);
		}
	}
	
	public void setWidgetValue(String key, String value) {
		for (AbstractFormUnit afu : getRootPanelWidget().getFormUnits()) {
			afu.setSingleWidgetValue(key, value);
		}
	}

	public FormContext getFormCtx() {
		return formCtx;
	}
	
	public ValidationResult doValidate(AssistedObject kvo) {
		ValidationResult vr=null;
		
		/**
		 * it keeps the consistence of CustomKVOFW's
		 */
		if(!validateConsistenceOfCustomKVOFWS()) {
			vr=new ValidationResult();
			vr.setValid(false);
			return vr;
		}
		
		switch(validateData) {
		case ALL:
			vr = formCtx.validatorManager.validate(kvo, customValidators);
			validateRelationLists(vr, null);
			validateCustKVOs(vr, null);
			break;
		case PARTIAL:
			Collection<String> fields = formRenderDescriptor.getRootNode().getKeysUnderNode();
			vr = formCtx.validatorManager.validatePartial(kvo, fields, customValidators);
			validateRelationLists(vr, fields);
			validateCustKVOs(vr, fields);
			break;
		case NONE:
			break;
		}
		
		dealValidationResult(vr);
		return vr;
	}
	
	/**
	 * 
	 * @param vr
	 * @param fields null = all field
	 */
	private void validateCustKVOs(ValidationResult vr, Collection<String> fields) {
		for(AbstractFormUnit unit : getRootPanelWidget().getFormUnits()) {
			for(String s : unit.getFormWidgetKeySet()) {
				if(!(unit.getWidgetByKey(s) instanceof CustomKVOFW) || (fields!=null && !fields.contains(s)))
					continue;
				
				CustomKVOFW fw = (CustomKVOFW) unit.getWidgetByKey(s);
				
				if(fw.getRelationValue()==null || fw.getRelationValue().getKvo()==null)
					continue;
				
				ValidationResult tmpRes = formCtx.validatorManager.validate(fw.getRelationValue().getKvo(), fw.getOdFromRows(), customValidators);
				
				if(!tmpRes.isValid()) {
					vr.setValid(false);
					if(vr.getFieldErrors()==null)
						vr.setFieldErrors(new HashMap<String, List<String>>());
					
					for(String key : tmpRes.getFieldErrors().keySet()) {
						vr.getFieldErrors().put(s+SharedUtil.ID_PART_SEPARATOR+key, tmpRes.getFieldErrors().get(key));
					}
				}
				
			}
		}
	}

	/**
	 * @return true = all of CustomKVO are valid, can continue the saving
	 * 
	 */
	private boolean validateConsistenceOfCustomKVOFWS() {
		boolean validsum = true;
		
		for(AbstractFormUnit unit : getRootPanelWidget().getFormUnits()) {
			for(String s : unit.getFormWidgetKeySet()) {
				if(!(unit.getWidgetByKey(s) instanceof CustomKVOFW))
					continue;
				
				boolean  valid = ((CustomKVOFW) unit.getWidgetByKey(s)).validateConsistence();
				validsum = validsum && valid;
			}
		}
		
		return validsum;
	}
	
	/**
	 * 
	 * @param vr - set invalid if relation list contains error!!!!
	 * @param fields - null = all
	 */
	private void validateRelationLists(ValidationResult vr, Collection<String> fields) {
		//TODO move to KeyValueObjectValidationManager
		
		for(AbstractFormUnit unit : getRootPanelWidget().getFormUnits()) {
			for(String s : unit.getFormWidgetKeySet()) {
				if(!(unit.getWidgetByKey(s) instanceof RelationListFW) || (fields!=null && !fields.contains(s)))
					continue;
				
				RelationList relList =  ((RelationListFW)unit.getWidgetByKey(s)).getRelationList();
				
				if(relList.getRelations()!=null && relList.getRelations().size()>0) {
					for(int i=0; i<relList.getRelations().size(); i++) {
						AssistedObject relKVO = relList.getRelations().get(i).getKvo();
						if(relKVO==null)
							relKVO=new KeyValueObject(((ListFDesc)formCtx.descStore
									.getRelatedFieldDescrMultiLevel(objectDescriptor, SharedUtil.listFromDotSeparated(s))).getRelatedDescriptorType());
						
						ValidationResult related_vr=null;
						switch (validateData) {
						case PARTIAL:
							related_vr=
								formCtx.validatorManager.validatePartial(relKVO,
										formCtx.descStore.getDefaultTypedDesc(relKVO.getDescriptorName(), FormRDesc.class).getRootNode().getKeysUnderNode(),
										customValidators);
							break;
						case ALL:
							related_vr=formCtx.validatorManager.validate(relKVO);
							break;
							
						default:
							related_vr=new ValidationResult();
						}
						
						if(!related_vr.isValid()) {
							vr.setValid(false);
						}
						
						((RelationListFW)unit.getWidgetByKey(s)).dealValidationResult(i, related_vr);
					}
				}
			}
		}
	}

	public void setValidateData(ValidateMode validataMode) {
		this.validateData = validataMode;
	}

	public Multimap<String, KeyValueObjectValidator> getCustomValidators() {
		return customValidators;
	}
	
}
