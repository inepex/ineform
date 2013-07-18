package com.inepex.ineForm.client.general;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;

/**
 * use {@link IneRadioButton#createRadioButtons(String, String...)} to create objects
 */
public class IneRadioButton extends HandlerAwareComposite implements HasValue<Boolean>,
	HasClickHandlers {
	
	private final static String rGroupNameBase="rGroupNameBase";
	private static int counter = 0;

	private static IneRadioButtonUiBinder uiBinder = GWT
			.create(IneRadioButtonUiBinder.class);

	interface IneRadioButtonUiBinder extends UiBinder<Widget, IneRadioButton> {
	}
	
	interface MyStyle extends CssResource {
		String checked();
		String disabled();
		String rbIconStyle();
		String rbIconStyle_old();
	}
	
	interface Res extends ClientBundle {
		ImageResource radioButton();
		ImageResource radioButton_old();
	}
	
	@UiField(provided=true)
	RadioButton rb;	
	@UiField
	FlowPanel rbIcon;
	@UiField
	MyStyle style;
	
	private final RadioGroup group;
	
	/**
	 * use {@link IneRadioButton#createRadioButtons(String, String...)} to create objects
	 */
	private IneRadioButton(RadioGroup group, String name, String label) {
		this.group=group;
		rb = new RadioButton(name, label);
		initWidget(uiBinder.createAndBindUi(this));
		if(IneFormProperties.IN_OLD_STYLE_COMPATIBILITY_MODE) {
			rbIcon.setStyleName(style.rbIconStyle_old());
		} else {
			rbIcon.setStyleName(style.rbIconStyle());
		}
	}
	
	/**
	 * 
	 * Create all the radio group in the same time for the correct behave!!!
	 * 
	 */
	public static List<IneRadioButton> createGroup(String... labels) {
		String name=rGroupNameBase+counter++;
				
		List<IneRadioButton> ret = new ArrayList<IneRadioButton>(labels.length);
		RadioGroup group = new RadioGroup();
		
		for(String label : labels) {
			IneRadioButton button = new IneRadioButton(group, name, label);
			group.addRadioButton(button);
			ret.add(button);
		}
		
		return ret;
	}
	
	@Override
	public void setValue(Boolean value, boolean fireEvents) {
		rb.setValue(value, fireEvents);
		group.updateGroupUI();
	}
	
	private void updateUi() {
		if(rb.getValue()) {
			rbIcon.addStyleName(style.checked());
		} else {
			rbIcon.removeStyleName(style.checked());
		}
		
		if(group.enabled)
			removeStyleName(style.disabled());
		else
			addStyleName(style.disabled());
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(rbIcon.addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(group.enabled)
					setValue(true);
			}
		}, ClickEvent.getType()));
		
		registerHandler(rb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				group.updateGroupUI();
			}
		}));
	}
	
	public void setWholeGroupEnabled(boolean enabled) {
		group.enabled=enabled;
		group.updateGroupUI();
	}
	
	public boolean isWholeGroupEnabled() {
		return group.enabled;
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Boolean> handler) {
		return rb.addValueChangeHandler(handler);
	}

	@Override
	public Boolean getValue() {
		return rb.getValue();
	}

	@Override
	public void setValue(Boolean value) {
		setValue(value, false);
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return rb.addClickHandler(handler);
	}
	
	private static class RadioGroup {
		
		private boolean enabled = true;
		private final List<IneRadioButton> members = new LinkedList<IneRadioButton>();

		public void updateGroupUI() {
			for(IneRadioButton b: members)
				b.updateUi();
		}

		public void addRadioButton(IneRadioButton ineTrackRadioButton) {
			members.add(ineTrackRadioButton);
		}
	
	}
}
