package com.inepex.ineForm.client.form;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface SaveCancelFormView extends IsWidget{

	public static interface Delegate {
		void saveClicked();
		void cancelClicked();
		void deleteClicked();
	}
	
	public void setDelegate(SaveCancelFormView.Delegate delegate);
	
	public void setFormWidget(Widget widget);
	/**
	 * to override default value
	 */
	public void setSaveButtonText(String saveButtonText);
	/**
	 * to override default value
	 */
	public void setCancelButtonText(String cancelButtonText);
	/**
	 * to override default value
	 */
	public void setSaveBtnStyle(String style);
	/**
	 * to override default value
	 */
	public void setCancelBtnStyle(String style);
	public void setSaveButtonVisible(boolean visible);
	public void setCancelButtonVisible(boolean visible);
}
