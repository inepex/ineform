package com.inepex.ineForm.client.form.widgets.event;

import com.google.gwt.event.shared.GwtEvent;
import com.inepex.ineForm.client.form.widgets.FormWidget;

/**
 * indicates when a field was changed
 *
 */
public class FormWidgetChangeEvent extends GwtEvent<FormWidgetChangeHandler>{

	public static Type<FormWidgetChangeHandler> TYPE = new Type<FormWidgetChangeHandler>();
	private FormWidget sourceFormWidget;
	private boolean isChangeEnd = false;
	
	public FormWidgetChangeEvent(FormWidget sourceFormWidget) {
		this.sourceFormWidget=sourceFormWidget;
	}
	
	public FormWidget getSourceFormWidget() {
		return sourceFormWidget;
	}
	
	public boolean isChangeEnd() {
		return isChangeEnd;
	}

	public FormWidgetChangeEvent setChangeEnd(boolean isChangeEnd) {
		this.isChangeEnd = isChangeEnd;
		return this;
	}

	@Override
	protected void dispatch(FormWidgetChangeHandler handler) {
		handler.onFormWidgetChange(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<FormWidgetChangeHandler> getAssociatedType() {
		return TYPE;
	}

}
