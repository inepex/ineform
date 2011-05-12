package com.inepex.ineFrame.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;

/**
 * Dialog for convenient notification about saving
 * 
 * @author istvan
 *
 */
public class InfoDialog extends DialogBoxBase {

	private HandlerRegistration hr = null;

	public InfoDialog(String title, String dialogMessage) {
		setText(title);
		setMessage(dialogMessage);
		this.show();
	}
	
	public InfoDialog(String dialogMessage) {
		setMessage(dialogMessage);
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
			InfoDialog.this.hide();
		}

	}

	@Override
	protected ImageResource getImageResource() {
		return DialogBoxBaseImages.INSTANCE.dialogInformation();
	}

	@Override
	protected void configureButtonBar() {
		
	}



}
