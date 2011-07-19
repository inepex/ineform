package com.inepex.ineForm.client.form;

import java.util.Collection;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.form.events.AfterUnsuccessfulSaveEvent;
import com.inepex.ineForm.client.form.events.BeforeCancelEvent;
import com.inepex.ineForm.client.form.events.BeforeSaveEvent;
import com.inepex.ineForm.client.form.events.CancelledEvent;
import com.inepex.ineForm.client.form.events.FormLifecycleEventBase;
import com.inepex.ineForm.client.form.events.SavedEvent;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.client.table.IneDataConnector.ManipulateResultCallback;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.validation.ValidationResult;

/**
 * A form that supports saving a new KeyValueObject and saving the modifications
 * of an existing one. It needs an IneDataConnector that implements the saving.
 * This form also fires following FormLifeCycle events:<br/>
 * <br/>
 *  - {@link BeforeSaveEvent}<br/>
 *  - {@link SavedEvent}<br/>
 *  - {@link BeforeCancelEvent}<br/>
 *  - {@link CancelledEvent}<br/>
 * <br/>
 * The form also validates before saving according to the default {@link ValidatorDesc}.<br/>
 * 
 * @author istvanszoboszlai
 *
 */
public class SaveCancelForm extends IneForm {
	
	class MainPanel extends HandlerAwareFlowPanel {
		@Override
		protected void onAttach() {
			super.onAttach();
			onMainPanelAttach(this);
		}
	}
	
	protected final MainPanel mainPanel = new MainPanel();
	protected final FlowPanel buttonPanel = new FlowPanel();
	
	private String saveButtonText=IneFormI18n.SAVE();
	private String cancelButtonText=IneFormI18n.CANCEL();
	
	protected final Button saveButton = new Button();
	protected final Button cancelButton = new Button();
	
	private IneDataConnector ineDataConnector;
	
	private AssistedObject originalData;
	private AssistedObject kvo;
	
	public enum ValidateMode {
		ALL, PARTIAL, NONE;
	}
	private ValidateMode validateData = ValidateMode.ALL;
	
 	
	/**
	 * @param ineDataConnector 
	 * @param descriptorName
	 * @param valueRangeProvider
	 * @param formRenderDescName should be DescriptorStore.DEFAULT_DESC_KEY or null if default descriptor needed
	 * @param eventBus
	 */
	@Inject
	public SaveCancelForm(FormContext formCtx,
						@Assisted("dn") String descriptorName,
						@Assisted("frdn") String formRDescName,
						@Assisted IneDataConnector ineDataConnector) {
		super(formCtx, descriptorName, formRDescName);
		this.ineDataConnector = ineDataConnector;
		
		saveButton.addStyleName("saveBtn");
		cancelButton.addStyleName("cancelBtn");
	}
	
	protected void onMainPanelAttach(MainPanel panel) {
		panel.registerHandler(saveButton.addClickHandler(new SaveClickHandler()));
		panel.registerHandler(cancelButton.addClickHandler(new CancelClickHandler()));
	}
	
	public void setCancelButtonVisible(boolean visible) {
		cancelButton.setVisible(visible);
	}
	
	@Override
	public Widget asWidget() {
		return mainPanel;
	}
	
	@Override
	public void renderForm() {
		mainPanel.add(super.asWidget());
		mainPanel.add(buttonPanel);
		buttonPanel.add(cancelButton);
		buttonPanel.add(saveButton);
		saveButton.setText(saveButtonText);
		cancelButton.setText(cancelButtonText);
		super.renderForm();
	}
	
	public void setSaveButtonText(String saveButtonText) {
		this.saveButtonText = saveButtonText;
	}

	public void setCancelButtonText(String cancelButtonText) {
		this.cancelButtonText = cancelButtonText;
	}
	
	private class SaveClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			save();			
		}		
	}
	
	public void save(){
		originalData = getInitialOrEmptyData();
		kvo = getValues(originalData.clone());
		if (fireBeforeSaveEvent(kvo).isCancelled())
			return;			
		
		doSave();
	}
	
	public void doSave(){
		if(validateData!=ValidateMode.NONE){
			if (!doValidate(kvo).isValid())
				return;
		}
		
		// Send only the changes to the server 
		AssistedObject difference = kvo.getDifference(originalData);
		if (difference.getKeys().size() == 0 
				|| difference.getKeys().size() == 1 && difference.getKeys().get(0).equals(IFConsts.KEY_ID)) {
			ValidationResult vr = new ValidationResult();
			vr.addGeneralError(IneFormI18n.validationNothingToSave());
			dealValidationResult(vr);
			return;
		}
		
		ineDataConnector.objectCreateOrEditRequested(difference, new ManipulateCallback());
	}
	
	private ValidationResult doValidate(AssistedObject kvo) {
		ValidationResult vr=null;
		switch(validateData) {
		case ALL:
			vr = formCtx.validatorManager.validate(kvo);
			break;
		case PARTIAL:
			Collection<String> fields = formRenderDescriptor.getRootNode().getKeysUnderNode();
			vr = formCtx.validatorManager.validatePartial(kvo, fields);
			break;
		case NONE:
			break;
		}
		
		dealValidationResult(vr);
		return vr;
	}
	
	private class ManipulateCallback implements ManipulateResultCallback {
		@Override
		public void onManipulationResult(ObjectManipulationResult result) {
			dealValidationResult(result.getValidationResult());
			if(result.getValidationResult() == null) {
				if (result.getObjectsNewState() != null) {
					setInitialData(result.getObjectsNewState());
				}
				fireSavedEvent(result);
			} else {
				fireAfterUnsuccesfulSaveEvent(result);
			}
		}
	}
	
	private class CancelClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (!fireBeforeCancelEvent().isCancelled())
				fireCancelledEvent();
		}
	}

	public void setValidateData(ValidateMode validataMode) {
		this.validateData = validataMode;
	}
	
	public HandlerRegistration addBeforeSaveHandler(BeforeSaveEvent.Handler handler) {
		return mainPanel.addHandler(handler, BeforeSaveEvent.getType());
	}

	public HandlerRegistration addSavedHandler(SavedEvent.Handler handler) {
		return mainPanel.addHandler(handler, SavedEvent.getType());
	}
	
	public HandlerRegistration addBeforeCancelHandler(BeforeCancelEvent.Handler handler) {
		return mainPanel.addHandler(handler, BeforeCancelEvent.getType());
	}
	
	public HandlerRegistration addCancelledHandler(CancelledEvent.Handler handler) {
		return mainPanel.addHandler(handler, CancelledEvent.getType());
	}
	
	public HandlerRegistration addAfterUnsuccesfulSaveHandler(AfterUnsuccessfulSaveEvent.Handler handler) {
		return mainPanel.addHandler(handler, AfterUnsuccessfulSaveEvent.getType());
	}
	
	public BeforeCancelEvent fireBeforeCancelEvent() {
		return doFireEvent(new BeforeCancelEvent());
	}

	public CancelledEvent fireCancelledEvent() {
		return doFireEvent(new CancelledEvent());
	}
	
	public BeforeSaveEvent fireBeforeSaveEvent(AssistedObject kvo) {
		return doFireEvent(new BeforeSaveEvent(kvo));
	}
	
	public SavedEvent fireSavedEvent(ObjectManipulationResult objectManipulationResult) {
		return doFireEvent(new SavedEvent(objectManipulationResult));
	}
	
	public AfterUnsuccessfulSaveEvent fireAfterUnsuccesfulSaveEvent(ObjectManipulationResult objectManipulationResult) {
		return doFireEvent(new AfterUnsuccessfulSaveEvent(objectManipulationResult));
	}
	
	private <T extends EventHandler, E extends FormLifecycleEventBase<T>> E doFireEvent(E event) {
		mainPanel.fireEvent(event);
		return event;
	}

	public void setSaveBtnStyle(String style){
		saveButton.setStyleName(style);
	}
	
	public void setCancelBtnStyle(String style){
		cancelButton.setStyleName(style);
	}
	
	
	
}
