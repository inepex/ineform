package com.inepex.translatorapp.client.page;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.translatorapp.client.i18n.translatorappI18n;

public class TransRowEditPopup {
	
	public static interface Callback {
		public void onCancelled();
		public void onSave(String newTranslated);
	}
	
	private class DialogContent extends HandlerAwareComposite {
		
		private final Callback callback;
		
		private final TextArea textArea;
		private final Button revertBtn;
		private final Button doneBtn;
		
		public DialogContent(Callback callback) {
			this.callback=callback;
			
			VerticalPanel vp = new VerticalPanel();
			initWidget(vp);
			
			vp.add(new Label(translatorappI18n.translateTableRow_engVal()));
			Label lbl = new Label(engVal);
			lbl.getElement().getStyle().setWidth(blockWidth, Unit.PX);
			lbl.getElement().getStyle().setHeight(blockHeight, Unit.PX);
			lbl.getElement().getStyle().setOverflowY(Overflow.SCROLL);
			lbl.getElement().getStyle().setBorderColor("#efefef");
			lbl.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			lbl.getElement().getStyle().setBorderWidth(2, Unit.PX);
			vp.add(lbl);
			
			vp.add(new Label(translatorappI18n.translatedValue_value()));
			textArea = new TextArea();
			textArea.setText(translatedVal);
			textArea.getElement().getStyle().setWidth(blockWidth, Unit.PX);
			textArea.getElement().getStyle().setHeight(blockHeight, Unit.PX);
			vp.add(textArea);
			
			HorizontalPanel hp = new HorizontalPanel();
			hp.setSpacing(10);
			
			revertBtn = new Button(translatorappI18n.revertBtn());
			hp.add(revertBtn);
			
			doneBtn = new Button(translatorappI18n.doneBtn());
			hp.add(doneBtn);
			
			vp.add(hp);
		}
		
		@Override
		protected void onLoad() {
			super.onLoad();
			
			registerHandler(revertBtn.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					callback.onCancelled();
					dialogBox.hide();
				}
			}));
			
			registerHandler(doneBtn.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					callback.onSave(textArea.getText());
					dialogBox.hide();
				}
			}));
		}
	}

	private static int blockWidth=500;
	private static int blockHeight=200;
	
	private final String engVal;
	private final String translatedVal;
	
	private DialogBox dialogBox; 
	
	public TransRowEditPopup(String engVal, String translatedVal) {
		this.engVal=engVal;
		this.translatedVal=translatedVal;
	}
	
	public void show(Callback callback) {
		dialogBox = new DialogBox(false, true);
		dialogBox.setWidget(new DialogContent(callback));
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		dialogBox.setText(IneFormI18n.EDIT());
		dialogBox.center();
	}
}
