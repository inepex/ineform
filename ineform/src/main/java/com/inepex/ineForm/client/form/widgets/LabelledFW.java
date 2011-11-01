package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.inepex.ineForm.client.form.widgets.FormWidget;
import com.inepex.ineForm.client.form.widgets.FormWidgetWrapperFormWidget;

public class LabelledFW extends FormWidgetWrapperFormWidget{

	private FlowPanel mainPanel = new FlowPanel();
	private InlineHTML afterFormLabel= new InlineHTML();
	
	public LabelledFW(FormWidget wrappedFormWidget, String defLbl) {
		super(wrappedFormWidget);
		initWidget(mainPanel);
		mainPanel.add(wrappedFormWidget);
		mainPanel.add(afterFormLabel);
		if(defLbl!=null) {
			afterFormLabel.setText(defLbl);
			afterFormLabel.setHTML("&nbsp;" + afterFormLabel.getText());
		}
	}
	
	@Override
	public void setWidth(String width) {
		getWrappedFW().setWidth(width);
	}
	
	public void setLabelText(String newText) {
		if(newText==null) afterFormLabel.setHTML("");
		else afterFormLabel.setHTML(newText);
	}
	
	public String getLabelText() {
		return afterFormLabel.getHTML();
	}

}
