package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface RadioButtonBase extends HasValue<Boolean>, IsWidget, HasClickHandlers{

	public void setEnabled(boolean enabled);
	public boolean isVisible();
	public void setVisible(boolean visible);
}
