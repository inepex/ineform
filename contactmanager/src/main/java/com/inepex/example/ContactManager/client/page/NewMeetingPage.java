package com.inepex.example.ContactManager.client.page;

import net.customware.gwt.dispatch.shared.Action;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.kvo.ContactKVO;
import com.inepex.example.ContactManager.entity.kvo.MeetingKVO;
import com.inepex.example.ContactManager.entity.kvo.search.ContactSearchKVO;
import com.inepex.ineForm.client.form.DefaultValueRangeProvider;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.SaveCancelForm;
import com.inepex.ineForm.client.form.SaveCancelForm.ValidateMode;
import com.inepex.ineForm.client.form.events.CancelledEvent;
import com.inepex.ineForm.client.form.events.SavedEvent;
import com.inepex.ineForm.client.form.widgets.ListBoxFW;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeEvent;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeHandler;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineForm.shared.dispatch.RelationListResult;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.navigation.PlaceHandlerHelper;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineom.shared.kvo.Relation;

public class NewMeetingPage extends FlowPanelBasedPage implements SavedEvent.Handler, CancelledEvent.Handler {
	
	private final FormContext formContext;
	private final PlaceHandler placeHandler;
	private final AuthManager authManager;
	
	private final ServerSideDataConnector connector;
	private final SaveCancelForm form;
	
	@Inject
	NewMeetingPage(FormContext formContext, PlaceHandler placeHandler, AuthManager authManager) {
		this.formContext=formContext;
		this.placeHandler=placeHandler;
		this.authManager=authManager;
		
		formContext.valueRangeProvider=new MeetingValueRangeProvider(formContext.ineDispatch);
		
		connector = new ServerSideDataConnector(formContext.ineDispatch, formContext.eventBus, MeetingKVO.descriptorName);
		form= new SaveCancelForm(formContext, MeetingKVO.descriptorName, null, connector);
		form.setValidateData(ValidateMode.PARTIAL);
		form.renderForm();
		mainPanel.add(form.asWidget());
	}

	@Override
	protected void onShow(boolean isFirstShow) {
		form.resetValuesToEmpty();
		form.getRootPanelWidget().getFormUnits().get(0).setSingleWidgetValue(MeetingKVO.k_user,
				new Relation(authManager.getLastAuthStatusResult().getUserId(), ""));
		
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(form.addSavedHandler(this));
		registerHandler(form.addCancelledHandler(this));
		
		registerHandler(form.getRootPanelWidget().getFormUnits().get(0).getWidgetByKey(MeetingKVO.k_company)
				.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
			
			@Override
			public void onFormWidgetChange(FormWidgetChangeEvent e) {
				((ListBoxFW)form.getRootPanelWidget().getFormUnits().get(0).getWidgetByKey(MeetingKVO.k_contact))
					.reLoadListAndKeepSelectedOrSetToNull();
			}
		}));
	}

	@Override
	public void onCancelled(CancelledEvent event) {
		formContext.eventBus.fireEvent(placeHandler.generateJumpUpEvent());
	}

	@Override
	public void onSaved(SavedEvent event) {
		formContext.eventBus.fireEvent(
				placeHandler.generateSameLevelMenuEvent(
						PlaceHandlerHelper.appendParam(
								AppPlaceHierarchyProvider.MEETINGS,
								AppPlaceHierarchyProvider.PARAM_MEETING,
								event.getObjectManipulationResult().getObjectsNewState().getId().toString())));
	}

	private class MeetingValueRangeProvider extends DefaultValueRangeProvider {

		public MeetingValueRangeProvider(IneDispatch dispatch) {
			super(dispatch);
		}
		
		@Override
		protected Action<RelationListResult> getActionForDescriptorName(
				String descriptorName) {
			if(!ContactKVO.descriptorName.equals(descriptorName)
					|| form.getRootPanelWidget()==null)
				return super.getActionForDescriptorName(descriptorName);
			else {
				ContactSearchKVO searchKVO = new ContactSearchKVO();
				searchKVO.setCompany(form.getRootPanelWidget().getFormUnits().get(0).getWidgetByKey(MeetingKVO.k_company).getRelationValue());
				return new RelationListAction(ContactKVO.descriptorName, searchKVO, 0, 1000, false);
			}
		}
	}
}
