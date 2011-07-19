package com.inepex.ineFrame.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;

/**
 * Dialog for convenient notification about saving
 * 
 * @author istvan
 *
 */
public class SavingDialog extends DialogBoxBase {

	private HandlerRegistration hr = null;

	public SavingDialog(String savedThingsName) {
		message.setText(IneFrameI18n.savingInProgress(savedThingsName));
		this.show();
	}

	@Override
	protected void onAttach() {
		hr = okButton.addClickHandler(new OkClickHandler());
		super.onAttach();
	}

	@Override
	protected void onDetach() {
		hr.removeHandler();
		super.onDetach();
	}

	private class OkClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			SavingDialog.this.hide();
		}

	}

	public void saveDone(boolean success){
		okButton.setEnabled(true);
		if (success) {
			message.setHTML(IneFrameI18n.saveSuccessful());
		} else {
			message.setHTML(IneFrameI18n.saveUnknownError());
		}
		this.center();
	}

	public void showSaveFailure(String failure){
		okButton.setEnabled(true);
		message.setHTML(IneFrameI18n.saveError(failure));
		this.center();
	}


	/* (non-Javadoc)
	 * @see hu.bluebird.rita.common.client.widgets.dialog.BaseDialogBox#configureButtonBar()
	 */
	@Override
	protected void configureButtonBar() {
		okButton.setEnabled(false);
	}

	@Override
	protected ImageResource getImageResource() {
		return DialogBoxBaseImages.INSTANCE.dialogInformation();
	}



}
