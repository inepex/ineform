package com.inepex.ineForm.client.form;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.form.events.AfterUnsuccessfulSaveEvent;
import com.inepex.ineForm.client.form.events.BeforeCancelEvent;
import com.inepex.ineForm.client.form.events.BeforeSaveEvent;
import com.inepex.ineForm.client.form.events.CancelledEvent;
import com.inepex.ineForm.client.form.events.DeletedEvent;
import com.inepex.ineForm.client.form.events.FormLifecycleEventBase;
import com.inepex.ineForm.client.form.events.ResetEvent;
import com.inepex.ineForm.client.form.events.SavedEvent;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFW;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.client.table.IneDataConnector.ManipulateResultCallback;
import com.inepex.ineom.shared.AssistedObjectDifference;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;
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
public class SaveCancelForm extends IneForm implements SaveCancelFormView.Delegate {
	
	private class ManipulateCallback implements ManipulateResultCallback {
		@Override
		public void onManipulationResult(com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult result) {
			dealValidationResult(result.getValidationResult());
			if(result.isSuccess() && (result.getValidationResult() == null || result.getValidationResult().isValid())) {
				if (result.getObjectsNewState() != null) {
					setInitialData(result.getObjectsNewState());
				}
				fireSavedEvent(result);
			} else {
				fireAfterUnsuccesfulSaveEvent(result);
			}
		}
	}
	
	private class DeleteCallback implements ManipulateResultCallback {

		private Long id;

		public DeleteCallback(Long id) {
			super();
			this.id = id;
		}

		@Override
		public void onManipulationResult(ObjectManipulationResult result) {
			fireDeletedEvent(id);
		}
		
	}
	
	// this flag indicates whether we want to display an error message 
	// when the save button is clicked, but there were no modifications
	private boolean displayNothingToSaveMsg = true;
	
	protected final SimpleEventBus ineformEventbus = new SimpleEventBus();
	
	protected final AssistedObjectDifference aoDifference;
	
	private IneDataConnector ineDataConnector;
	
	private AssistedObject originalData;
	private AssistedObject kvo;
	
	public enum ValidateMode {
		ALL, PARTIAL, NONE;
	}
	
	protected SaveCancelFormView view;
 	
	/**
	 * @param ineDataConnector 
	 * @param descriptorName
	 * @param valueRangeProvider
	 * @param formRenderDescName should be DescriptorStore.DEFAULT_DESC_KEY or null if default descriptor needed
	 * @param eventBus
	 * @param view set it to null to use default view
	 */
	@Inject
	public SaveCancelForm(FormContext formCtx,
						@Assisted("dn") String descriptorName,
						@Assisted("frdn") String formRDescName,
						@Assisted IneDataConnector ineDataConnector,
						@Assisted SaveCancelFormView view) {
		super(formCtx, descriptorName, formRDescName);
		this.aoDifference = formCtx.aoDifference;
		this.ineDataConnector = ineDataConnector;
		if (view == null) 
			this.view = new DefaultSaveCancelFormView();
		else this.view = view;
		
		this.view.setDelegate(this);
	}
	
	public IneDataConnector getIneDataConnector() {
		return ineDataConnector;
	}

	public void setSaveButtonVisible(boolean visible) {
		view.setSaveButtonVisible(visible);
	}
	
	public void setCancelButtonVisible(boolean visible) {
		view.setCancelButtonVisible(visible);
	}
	
	@Override
	public Widget asWidget() {
		return view.asWidget();
	}
	
	@Override
	public void renderForm() {
		view.setFormWidget(super.asWidget());
		super.renderForm();
	}
	
	public void setSaveButtonText(String saveButtonText) {
		view.setSaveButtonText(saveButtonText);
	}

	public void setCancelButtonText(String cancelButtonText) {
		view.setCancelButtonText(cancelButtonText);
	}
	
	public void save(){
		originalData = getInitialOrEmptyData();
		kvo = getValues(originalData.clone());
		if (fireBeforeSaveEvent(kvo).isCancelled()){
			fireAfterUnsuccesfulSaveEvent(null);
			return;			
		}						
		
		doSave();
	}
	
	@Override
	public void dealValidationResult(ValidationResult vr) {
		view.setFormValidationSuccess(vr==null || vr.isValid());
		
		super.dealValidationResult(vr);
	}
	
	public void doSave(){
		if (!doValidate(kvo).isValid()) {
			fireAfterUnsuccesfulSaveEvent(null);
			return;
		}
		
		// Send only the changes to the server 
		AssistedObject difference = aoDifference.getDifference(originalData, kvo).getAssistedObject();
		if ((difference.getKeys().size() == 0 
				|| difference.getKeys().size() == 1 && difference.getKeys().get(0).equals(IFConsts.KEY_ID))
				&& difference.getAllPropsJson() != null && difference.getAllPropsJson().size() == 0) {
			
			if(displayNothingToSaveMsg){
				ValidationResult vr = new ValidationResult();
				vr.addGeneralError(IneFormI18n.validationNothingToSave());
				dealValidationResult(vr);
			}else
				cancelClicked();
				
			return;
		}
		
		List<CustomKVOObjectDesc> descs = new ArrayList<CustomKVOObjectDesc>();
		for(AbstractFormUnit unit : getRootPanelWidget().getFormUnits()) {
			for(String s : unit.getFormWidgetKeySet()) {
				if(!(unit.getWidgetByKey(s) instanceof CustomKVOFW))
					continue;
				
				descs.add(((CustomKVOFW)unit.getWidgetByKey(s)).getOdFromRows());
			}
		}
		
		ineDataConnector.objectCreateOrEditRequested(difference, new ManipulateCallback(), 
				descs.size()>0 ? descs.toArray(new CustomKVOObjectDesc[descs.size()]) : null);
	}
	
	public HandlerRegistration addBeforeSaveHandler(BeforeSaveEvent.Handler handler) {
		return ineformEventbus.addHandler(BeforeSaveEvent.getType(), handler);
	}

	public HandlerRegistration addSavedHandler(SavedEvent.Handler handler) {
		return ineformEventbus.addHandler(SavedEvent.getType(), handler);
	}
	
	public HandlerRegistration addBeforeCancelHandler(BeforeCancelEvent.Handler handler) {
		return ineformEventbus.addHandler(BeforeCancelEvent.getType(), handler);
	}
	
	public HandlerRegistration addCancelledHandler(CancelledEvent.Handler handler) {
		return ineformEventbus.addHandler(CancelledEvent.getType(), handler);
	}
	
	public HandlerRegistration addAfterUnsuccesfulSaveHandler(AfterUnsuccessfulSaveEvent.Handler handler) {
		return ineformEventbus.addHandler(AfterUnsuccessfulSaveEvent.getType(), handler);
	}
	
	public HandlerRegistration addDeletedHandler(DeletedEvent.Handler handler) {
		return ineformEventbus.addHandler(DeletedEvent.getType(), handler);
	}
	
	public BeforeCancelEvent fireBeforeCancelEvent() {
		return doFireEvent(new BeforeCancelEvent());
	}

	public CancelledEvent fireCancelledEvent() {
		return doFireEvent(new CancelledEvent());
	}
	
	@Override
	public ResetEvent fireResetEvent() {
		view.dataReseted();
		return super.fireResetEvent();
	}
	
	public BeforeSaveEvent fireBeforeSaveEvent(AssistedObject kvo) {
		return doFireEvent(new BeforeSaveEvent(kvo));
	}
	
	public SavedEvent fireSavedEvent(com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult objectManipulationResult) {
		return doFireEvent(new SavedEvent(objectManipulationResult));
	}
	
	public AfterUnsuccessfulSaveEvent fireAfterUnsuccesfulSaveEvent(com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult objectManipulationResult) {
		return doFireEvent(new AfterUnsuccessfulSaveEvent(objectManipulationResult));
	}
	
	public DeletedEvent fireDeletedEvent(Long id) {
		return doFireEvent(new DeletedEvent(id));
	}
	
	private <T extends EventHandler, E extends FormLifecycleEventBase<T>> E doFireEvent(E event) {
		ineformEventbus.fireEvent(event);
		return event;
	}

	public void setSaveBtnStyle(String style){
		view.setSaveBtnStyle(style);
	}
	
	public void addSaveBtnStyle(String style){
		view.addSaveBtnStyle(style);
	}
	
	public void setCancelBtnStyle(String style){
		view.setCancelBtnStyle(style);
	}

	public void addCancelBtnStyle(String style){
		view.addCancelBtnStyle(style);
	}
	
	public boolean isDisplayNothingToSaveMsg() {
		return displayNothingToSaveMsg;
	}

	/**
	 * This flag indicates whether we want to display an error message 
	 * when the save button is clicked, but there were no modifications
	 * 
	 * @param display
	 */
	public void displayNothingToSaveMsg(boolean display) {
		this.displayNothingToSaveMsg = display;
	}

	@Override
	public void saveClicked() {
		save();
	}

	@Override
	public void cancelClicked() {
		if (!fireBeforeCancelEvent().isCancelled())
			fireCancelledEvent();
	}

	@Override
	public void deleteClicked() {
		AssistedObject data = getInitialOrEmptyData();
		if (data.getId() == IFConsts.NEW_ITEM_ID) throw new RuntimeException("Delete called for a newly created object");
		ineDataConnector.objectDeleteRequested(data, new DeleteCallback(data.getId()));
	}

	@Override
	public HandlerRegistration addFormSavedHandlerFromView(SavedEvent.Handler handler) {
		return addSavedHandler(handler);
	}

	@Override
	public HandlerRegistration addFormAfterUnsuccesfulSaveHandlerFromView(
			AfterUnsuccessfulSaveEvent.Handler handler) {
		return addAfterUnsuccesfulSaveHandler(handler);
	}
	
	public void forceLoadingOnSaveBtn(boolean loading){
		view.forceLoadingOnSaveBtn(loading);
	}
}
