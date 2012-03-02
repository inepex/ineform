package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineom.shared.descriptor.FDesc;

public class LabelButtonFW extends FormWidgetWrapperFormWidget {

	private final FlowPanel mainPanel;
	private final Button button = new Button(IneFormI18n.change());
	
	public LabelButtonFW(FDesc fielddescriptor, boolean showLongAsDate, String nullAlterText, DateProvider dateProvider) {
		super(new LabelFW(fielddescriptor, showLongAsDate, nullAlterText, dateProvider));
		
		mainPanel=new FlowPanel();
		mainPanel.add(getWrappedFW());
		mainPanel.add(button);
		initWidget(mainPanel);
	}
	
	public Button getButton() {
		return button;
	}

}
