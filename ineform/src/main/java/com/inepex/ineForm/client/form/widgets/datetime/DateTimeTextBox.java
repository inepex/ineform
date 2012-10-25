package com.inepex.ineForm.client.form.widgets.datetime;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;
import com.inepex.ineForm.client.form.widgets.datetime.IneDateGWT.Precision;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;

class DateTimeTextBox extends HandlerAwareComposite {

	private final Precision precision;
	private final String precTmp;
	private String prevValue="";
	private int prevCursorpos=-1;
	
	private final boolean selectmanager;
	
	private class TextBoxWrapper extends TextBox{
		@Override
		protected void onDetach() {
			abstractField.textBoxChange();
			super.onDetach();
		}
	}
	private final TextBoxWrapper wrappedtb = new TextBoxWrapper();
	private final AbstractField abstractField;
	
	public DateTimeTextBox(Precision precision, boolean enableselectmanager, AbstractField abstractField) {
		this.precision=precision;
		this.abstractField = abstractField;
		initWidget(wrappedtb);
		
		this.selectmanager=enableselectmanager;
		
		//make prectmp
		precTmp=precision.getFormattterString().replaceAll("y+|M+|d+|h+|k+]", "01").replaceAll("H+|m+|s+|S+|K+", "00");
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		registerHandler(wrappedtb.addBlurHandler(new CompleteStringHandler()));
		registerHandler(wrappedtb.addKeyUpHandler(new WriteCorrectorHandler()));
		registerHandler(wrappedtb.addClickHandler(new ManageSelectHandler()));
	}
	
	public void setStringValue(String newvalue) {
		prevValue=newvalue;
		wrappedtb.setValue(prevValue);
	}
	
	public void setFocus(boolean focused) {
		wrappedtb.setFocus(focused);
	}
	
	public void setEnabled(boolean enabled) {
		wrappedtb.setEnabled(enabled);
	}
	
	
	public HandlerRegistration addDateTimeTextBoxEventHandler(DateTimeTextBoxEventHandler handler) {
		return addHandler(handler, DateTimeTextBoxEvent.TYPE);
	}
		
	private String complete(String str) {
		if(str.length()==0) return precTmp;
		if(fit(str)) return str;
		
		int prec_j=0;
		
		for(int str_i=0; str_i<str.length();/*nothing*/) {
			boolean res=false;
			while(str_i<str.length() && str.charAt(str_i)>='0' && str.charAt(str_i)<='9') {
				str_i++;
				res=true;
			}
			if(!res && str_i<str.length() ) {
				if(prec_j<precTmp.length() && precTmp.charAt(prec_j)==str.charAt(str_i)) {
					str_i++;
					prec_j++;
				} else return null;
			} else {
				while(prec_j<precTmp.length() && precTmp.charAt(prec_j)>='0' && precTmp.charAt(prec_j)<='9') {
					prec_j++;
				}
			}
			
		}
		
		return str+precTmp.substring(prec_j);
	}
	
	private boolean fit(String str) {
		try {
			precision.getFormatter().parseStrict(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
    
	public void manageSelectSimple() {
		if(!selectmanager) return;
		
		if(wrappedtb.getValue().length()==0) return;
		
		String value=wrappedtb.getValue();
		
		int b=wrappedtb.getCursorPos();
		if(b>0) b--;
		while(b>0 && value.charAt(b)>='0' && value.charAt(b)<='9') b--;
		if(b>0 || value.charAt(b)<'0' || value.charAt(b)>'9') b++;
		
		int e=wrappedtb.getCursorPos();
		if(e>value.length()-1) {
			e=value.length()-1;
		} else {
			while(e<value.length()-1 && value.charAt(e)>='0' && value.charAt(e)<='9') e++;
			if(e<value.length()-1 || value.charAt(e)<'0' || value.charAt(e)>'9') e--;
		}
		
		if(b<=e) wrappedtb.setSelectionRange(b, e-b+1);
		
		prevCursorpos=wrappedtb.getCursorPos();
	}
	
	public void manageSelectOnArrow() {
		if(!selectmanager) return;
		
		String txt=wrappedtb.getText();
		int cp=wrappedtb.getCursorPos();
		
		while( cp>0
				&& txt.charAt(cp)<='9'
			    && txt.charAt(cp)>='0') 
				cp--;
		
		wrappedtb.setCursorPos(cp);
		
		manageSelectSimple();
	}
	
    
    private class CompleteStringHandler implements BlurHandler {

		@Override
		public void onBlur(BlurEvent event) {
			if(wrappedtb.getValue().length()!=0 || precision==Precision.OOO_HM || precision == Precision.OOO_HMS) {
				String cval=complete(wrappedtb.getValue());
				wrappedtb.setValue(cval);
				prevValue=cval;
			}
			
			fireEvent(new DateTimeTextBoxEvent());
		}
    	
    }
    
    private class WriteCorrectorHandler implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			//just step cursor (arrow keys, home, end)
			if(event.getNativeKeyCode()>=35 && event.getNativeKeyCode()<=40) {
				if(event.getNativeKeyCode()==37) manageSelectOnArrow();
				else manageSelectSimple();
				return;
			}
			
			//handle write
			if(!fit(complete(wrappedtb.getValue()))) {
				wrappedtb.setValue(prevValue);
				wrappedtb.setCursorPos(prevCursorpos);
				
			} else {
				prevValue=wrappedtb.getValue();
				prevCursorpos=wrappedtb.getCursorPos();
			}
		}
    	
    }
    
    private class ManageSelectHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			manageSelectSimple();
		}
    	
    }

	public String getStringValue() {
		return wrappedtb.getValue();
	}
	
	public interface DateTimeTextBoxEventHandler extends EventHandler {
		public void onChange();
	}
	
	public static class DateTimeTextBoxEvent extends GwtEvent<DateTimeTextBoxEventHandler> {

		public static final Type<DateTimeTextBoxEventHandler> TYPE = new Type<DateTimeTextBoxEventHandler>();
		
		@Override
		protected void dispatch(DateTimeTextBoxEventHandler handler) {
			handler.onChange();
		}

		@Override
		public com.google.gwt.event.shared.GwtEvent.Type<DateTimeTextBoxEventHandler> getAssociatedType() {
			return TYPE;
		}
	}
	

}
