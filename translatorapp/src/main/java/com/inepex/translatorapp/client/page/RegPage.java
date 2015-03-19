package com.inepex.translatorapp.client.page;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.form.SaveCancelForm;
import com.inepex.ineForm.client.form.events.BeforeSaveEvent;
import com.inepex.ineForm.client.form.events.CancelledEvent;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationActionResult;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineFrame.client.dialog.ErrorDialogBox;
import com.inepex.ineFrame.client.dialog.InfoDialog;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.page.FlowPanelBasedPageWithScroll;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.action.RegAction;
import com.inepex.translatorapp.shared.kvo.RegConsts;

public class RegPage extends FlowPanelBasedPageWithScroll {
	
	private final EventBus eventBus;
	private final IneDispatch ineDispatch;
	
	private final SaveCancelForm form;
	private final ServerSideDataConnector connector;
	
	@Inject
	public RegPage(FormFactory formFactory,
			DataConnectorFactory connectorFactory,
			EventBus eventBus,
			IneDispatch ineDispatch) {
		this.eventBus=eventBus;
		this.ineDispatch=ineDispatch;
		
		mainPanel.add(new HTML(translatorappI18n.regPageTitle()));
		
		connector = connectorFactory.createServerSide(RegConsts.descriptorName);
		form = formFactory.createSaveCancel(RegConsts.descriptorName, null, connector, null);
		form.renderForm();
		mainPanel.add(form.asWidget());
	}

	@Override
	protected void onShow(boolean isFirstShow) {
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		
		registerHandler(form.addBeforeSaveHandler(new BeforeSaveEvent.Handler() {
			
			@Override
			public void onBeforeSave(BeforeSaveEvent event) {
				event.setCancelled(true);
			
				ineDispatch.execute(new RegAction(event.getKvo()), new SuccessCallback<ObjectManipulationActionResult>() {

					@Override
					public void onSuccess(ObjectManipulationActionResult result) {
						if(result.isSuccess() || result.getValidationResult()!=null) {
							if(result.getValidationResult()==null || result.getValidationResult().isValid()) {
								InfoDialog dialog = new InfoDialog(translatorappI18n.succesfulRegistration());
								dialog.center();
								dialog.addCloseHandler(new CloseHandler<PopupPanel>() {
									
									@Override
									public void onClose(CloseEvent<PopupPanel> closeEvent) {
										backToLoginPage();
									}
								});
							} else {
								form.dealValidationResult(result.getValidationResult());
							}
						} else {
							System.out.println("Error while registering: "+result.getMessage());
							ErrorDialogBox.showError(translatorappI18n.regError());
						}
					}
				});
			}
		}));
		
		registerHandler(form.addCancelledHandler(new CancelledEvent.Handler() {
			
			@Override
			public void onCancelled(CancelledEvent event) {
				backToLoginPage();
			}
		}));
	}
	
	private void backToLoginPage() {
		eventBus.fireEvent(new PlaceRequestEvent(NavigationProperties.defaultPlace));
		form.resetValuesToEmpty();
	}
	
}
