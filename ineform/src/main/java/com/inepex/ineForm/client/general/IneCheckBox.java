package com.inepex.ineForm.client.general;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.InlineLabel;
import com.inepex.ineForm.client.form.widgets.CheckBoxFW;
import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineFrame.client.widget.ClickableFlowPanel;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class IneCheckBox extends DenyingFormWidget implements HasValue<Boolean> {

	private final FlowPanel mainPanel = new FlowPanel();
	private final ClickableFlowPanel checkPanel = new ClickableFlowPanel();
	private final InlineLabel textLabel = new InlineLabel();
	private final HTML textHTML = new HTML("");
	private Boolean checked = false;
	
	public IneCheckBox() {
		this(null);
	}
	
	public IneCheckBox(String text){
		this(null, null, text);
	}
	
	public IneCheckBox(FDesc fielddescriptor, WidgetRDesc wrDesc) {
		this(fielddescriptor, wrDesc, null);
	}
	
	public IneCheckBox(FDesc fDesc, WidgetRDesc wrDesc, String text) {
		super(fDesc);
		
		if(text!=null) {
			textLabel.setText(text);
		} else if(wrDesc!=null && wrDesc.hasProp(CheckBoxFW.CHECKBOXTEXT)) {
			textLabel.setText(wrDesc.getPropValue(CheckBoxFW.CHECKBOXTEXT));
		}else if(wrDesc!=null && wrDesc.hasProp(CheckBoxFW.CHECKBOXHTML)) {
			textHTML.setHTML(wrDesc.getPropValue(CheckBoxFW.CHECKBOXHTML));
		}
		
		initWidget(mainPanel);
		createUI();
	}

	private void createUI() {
		mainPanel.add(checkPanel);
		if(textHTML.getHTML().equals("")){
			mainPanel.add(textLabel);
		}else{
			mainPanel.add(textHTML);
		}
		textLabel.setStyleName(GeneralRes.INST.get().GeneralStyle().ineCheckBoxText());
		textHTML.setStyleName(GeneralRes.INST.get().GeneralStyle().ineCheckBoxText());
		checkPanel.setStyleName(GeneralRes.INST.get().GeneralStyle().ineCheckBox());
		checkPanel.getElement().getStyle().setPosition(Position.STATIC);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(checkPanel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				checked=!checked;
				ValueChangeEvent.fire(IneCheckBox.this, checked);
				fireFormWidgetChanged();
				correctCheckboxStyle();
			}
		}));
	}
	
	private void correctCheckboxStyle() {
		if(checked){
			checkPanel.addStyleName(GeneralRes.INST.get().GeneralStyle().ineCheckBoxActive());
		} else {
			checkPanel.removeStyleName(GeneralRes.INST.get().GeneralStyle().ineCheckBoxActive());
		}
	}

	@Override
	public void setValue(Boolean value){
		checked=(value!=null && value);
		correctCheckboxStyle();
	}
		
	public HandlerRegistration addClickHandler(ClickHandler handler){
		return checkPanel.addClickHandler(handler);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public Boolean getValue() {
		return checked;
	}

	@Override
	public void setValue(Boolean value, boolean fireEvents) {
		checked=(value!=null && value);
		
		if(fireEvents && value != null) {
			ValueChangeEvent.fire(IneCheckBox.this, value);
			fireFormWidgetChanged();
		}
		
		correctCheckboxStyle();
	}

	@Override
	public void setEnabled(boolean enabled) {
		//TODO
		throw new UnsupportedOperationException("unimplemented yet!");
	}

	@Override
	public boolean isFocusable() {
		return false;
	}

	@Override
	public boolean handlesBoolean() {
		return true;
	}

	@Override
	public void setBooleanValue(Boolean value) {
		setValue(value);
	}

	@Override
	public Boolean getBooleanValue() {
		return getValue();
	}

	public HandlerRegistration addLabelClickHandler(ClickHandler clickHandler) {
		return textLabel.addClickHandler(clickHandler);
	}

}
