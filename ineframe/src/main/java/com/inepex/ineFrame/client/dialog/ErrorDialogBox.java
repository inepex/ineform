/**
 * 
 */
package com.inepex.ineFrame.client.dialog;

import com.google.gwt.resources.client.ImageResource;
import com.inepex.ineFrame.client.i18n.IneFrameI18n_old;

/**
 * @author Horváth Szabolcs, Istvan Szoboszlai
 *
 */
public class ErrorDialogBox extends DialogBoxBase {

	public ErrorDialogBox() {
		super();
		setText(IneFrameI18n_old.errorDialogTitle());
	}

	/* (non-Javadoc)
	 * @see hu.bluebird.rita.common.client.widgets.dialog.BaseDialogBox#configureButtonBar()
	 */
	@Override
	protected void configureButtonBar() {
	}

	@Override
	protected ImageResource getImageResource() {
		return DialogBoxBaseImages.INSTANCE.dialogError();
	}
	
	public static void showError(String errorMesage) {
		ErrorDialogBox edb = new ErrorDialogBox();
		edb.show(errorMesage);
	}
	
}
