package com.inepex.translatorapp.client.fw;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.TextDecoration;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.WhiteSpace;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.inepex.ineForm.client.form.widgets.TextBoxFWBase;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.client.page.popup.EditCallback;
import com.inepex.translatorapp.client.page.popup.TransRowEditPopup;

public class TextBoxWithPopupEditorFw extends TextBoxFWBase {

	private HorizontalPanel panel = new HorizontalPanel();
	private Label showPopupLabel = new Label(translatorappI18n.textBoxWithPopupLabel());
	private TransRowEditPopup popup;
	
	public TextBoxWithPopupEditorFw(FDesc fielddescriptor, WidgetRDesc wrDesc) {
		super(fielddescriptor);
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		textBox= new TextBox();
		updateWidth(wrDesc);
		panel.add(textBox);
		panel.add(showPopupLabel);
		initWidget(panel);
		
		showPopupLabel.getElement().getStyle().setCursor(Cursor.POINTER);
		showPopupLabel.getElement().getStyle().setTextDecoration(TextDecoration.UNDERLINE);
		showPopupLabel.getElement().getStyle().setMarginLeft(6, Unit.PX);
		showPopupLabel.getElement().getStyle().setWhiteSpace(WhiteSpace.NOWRAP);
		
		
		showPopupLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showPopup();
			}
		});
	}
	
	private void showPopup(){
		popup = new TransRowEditPopup("", textBox.getText());
		popup.show(new EditCallback() {
			
			@Override
			public void onSave(String newTranslated) {
				textBox.setValue(newTranslated, true);
			}
			
			@Override
			public void onCancelled() {
			}
		});
	}

}
