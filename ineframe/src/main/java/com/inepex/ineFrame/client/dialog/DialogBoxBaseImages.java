package com.inepex.ineFrame.client.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author Horváth Szabolcs
 *
 */
public interface DialogBoxBaseImages extends ClientBundle {

	public static final DialogBoxBaseImages INSTANCE =  GWT.create(DialogBoxBaseImages.class);
	
	@Source("dialog-error.png")
	ImageResource dialogError();
  
	@Source("dialog-information.png")
	ImageResource dialogInformation();
  
	@Source("dialog-confirm.png")
	ImageResource dialogConfirm();
  
	@Source("dialog-warning.png")
	ImageResource dialogWarning();

}
