package com.inepex.ineForm.client.general;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.IneFormProperties;

public class IneButton extends Composite implements IsWidget, HasEnabled, HasText, HasHTML {
	
	public static enum IneButtonType {
		
		/**
		 * to jump to the next or previous page
		 * <br><b>blue</b>
		 */
		PAGING,
		
		/**
		 * to abort a started action
		 * <br><b>gray</b>
		 */
		CANCEL,
		
		/**
		 * to modify the properties of action (what is useless without saving)
		 * <br><b>transparent</b>
		 */
		CONTROL,
		
		/**
		 * to commit an action that effects data changes or changes in the sate of user or view
		 * <br><b>green</b>
		 */
		ACTION;
	}
	
	private static IFButtonUiBinder uiBinder = GWT
			.create(IFButtonUiBinder.class);

	interface IFButtonUiBinder extends UiBinder<Widget, IneButton> {
	}
	
	interface ButtonStyles extends CssResource {
		String pagingColors();
		String cancelColors();
		String controlColors();
		String actionColors();
		String disabled();
	}

	@UiField
	Button button;
	
	@UiField
	ButtonStyles style;
	
	public IneButton(IneButtonType type, String text) {
		this(type);
		button.setText(text);
	}
	
	public IneButton(IneButtonType type) {
		initWidget(uiBinder.createAndBindUi(this));
		
		if(IneFormProperties.IN_OLD_STYLE_COMPATIBILITY_MODE)
			return;
		
		switch (type) {
		case PAGING:
			button.addStyleName(style.pagingColors());
			break;
		
		case CANCEL:
			button.addStyleName(style.cancelColors());
			break;
					
		case CONTROL:
			button.addStyleName(style.controlColors());
			break;
			
		case ACTION:
			button.addStyleName(style.actionColors());
			break;
		default:
			break;
		}
	}

	@Override
	public void setText(String text) {
		button.setText(text);
	}

	@Override
	public String getText() {
		return button.getText();
	}
	
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return button.addClickHandler(handler);
	}

	@Override
	public boolean isEnabled() {
		return button.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		button.setEnabled(enabled);
		if(enabled)
			button.removeStyleName(style.disabled());
		else
			button.addStyleName(style.disabled());
	}

	@Override
	public String getHTML() {
		return button.getHTML();
	}

	@Override
	public void setHTML(String html) {
		button.setHTML(html);
	}
}
