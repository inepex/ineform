package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class NumberTextBoxFW extends DenyingFormWidget {
	
	public static final String FRACTIONALDIGITCONT = "fractdigitcount";
	public static final String WHOLEDIGITCONT = "wholedigitcount";
	public static final String ENABLE_NEGATIVE_NUMBER = "negativeNumber";
	
	private static char decimalpoint = '.';
	private static char decimalpoint2 = ',';
	
	private boolean hasDecimalpoint = false;
	private int maxFractDigits = -1;
	private int maxWholeDigits = -1;

	protected final TextBox textBox = new TextBox();
	private boolean negativeNumEnabled = false;

	public NumberTextBoxFW(FDesc fielddescriptor) {
		this(fielddescriptor, false);
	}
	
	public NumberTextBoxFW(FDesc fielddescriptor, boolean dontInitWidget){
		super(fielddescriptor);
		if (!dontInitWidget)
			initWidget(textBox);
	}
	
	public void setMaxLength(int length) {
		textBox.setMaxLength(length);
	}
	
	public void setMaxFractDigits(int maxDigits) {
		this.maxFractDigits = maxDigits;
	}
	
	public void setMaxFractDigitsUpdateValue(int maxDigits) {
		this.maxFractDigits = maxDigits;
		filterTextPreserveCaretPosition(textBox.getText());
	}
		
	public void setMaxWholeDigits(int maxWholeDigits) {
		this.maxWholeDigits = maxWholeDigits;
	}

	public void setHasDecimalPoint(boolean hasDecimalpoint){
		this.hasDecimalpoint = hasDecimalpoint;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		textBox.setEnabled(enabled);
	}
	
	@Override
	protected void onAttach() {

		TextChangeHandler changeHandler = new TextChangeHandler();
		
		registerHandler(textBox.addChangeHandler(changeHandler));
		registerHandler(textBox.addValueChangeHandler(changeHandler));
		registerHandler(textBox.addKeyUpHandler(changeHandler));

		super.onAttach();
	}
	
	class TextChangeHandler implements ChangeHandler, KeyUpHandler, ValueChangeHandler<String> {
		@Override
		public void onChange(ChangeEvent event) {
			filterTextPreserveCaretPosition(textBox.getText());
			fireFormWidgetChanged();
		}
		@Override
		public void onKeyUp(KeyUpEvent event) {
			filterTextPreserveCaretPosition(textBox.getText());
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
				fireFormWidgetChanged(true);
			} else {
				fireFormWidgetChanged();
			}
		}
		@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			filterTextPreserveCaretPosition(textBox.getText());
			fireFormWidgetChanged();
		}
	}
	
	private void filterTextPreserveCaretPosition(String text) {
		int origCurPos = textBox.getCursorPos();
		boolean negativeSignRemoved = false;
		StringBuffer filteredSb = new StringBuffer();
		String original = textBox.getText();
			
    	int decimalpointAt = -1;
    	if(negativeNumEnabled && text.charAt(0) == '-'){
    		text = text.substring(1, text.length());
    		negativeSignRemoved = true;
    	}
    	for(int i=0; i < text.length(); i++) {
    		char ch = text.charAt(i);
    		if (isNumber(ch)) {
    			
    			if((decimalpointAt ==-1 && maxWholeDigits == -1)
    					|| (decimalpointAt ==-1 && i < maxWholeDigits)
    					|| (decimalpointAt != -1 && maxFractDigits==-1) 
    					|| (decimalpointAt != -1 && i-decimalpointAt<=maxFractDigits)) {
    				filteredSb.append(ch);
    			} else {
    				origCurPos = decreaseIfBigger(origCurPos, filteredSb.length());
    			}
    			
    		} else if (hasDecimalpoint && maxFractDigits!=0 && decimalpointAt == -1 && (ch == decimalpoint || ch==decimalpoint2)) {
    			decimalpointAt=i;
    			filteredSb.append(ch);
    		} else {
	    		// one char was removed
				origCurPos = decreaseIfBigger(origCurPos, filteredSb.length());
    		}
     	}
    	if(negativeSignRemoved){
    		filteredSb.insert(0, "-");
    	}
    	if (filteredSb.toString().equals(original))
    		return;
    	
		textBox.setText(filteredSb.toString());
		setCurPosSafe(origCurPos);
		textBox.setFocus(true);
    }
	
	private void setCurPosSafe(int curPos) {
		if (curPos < 0)
			curPos = 0;
		if (curPos > (textBox.getText().length()))
			curPos = textBox.getText().length();
    	textBox.setCursorPos(curPos);
	}
	
	private int decreaseIfBigger(int origCurPos, int currentFilteredTextLength) {
   		if (origCurPos >= currentFilteredTextLength)
			origCurPos--;
   		return origCurPos;
	}

    private boolean isNumber(char ch) {
    	if (   ch == '0'
    		|| ch == '1'
    		|| ch == '2'
    		|| ch == '3'
    		|| ch == '4'
        	|| ch == '5'
            || ch == '6'
            || ch == '7'
            || ch == '8'
            || ch == '9')
    		return true;

    	return false;
    }

    @Override
    public boolean handlesDouble() {
    	return true;
    }
    
    @Override
    public boolean handlesLong() {
    	return true;
    }
    
    @Override
    public Long getLongValue() {
       	try {
			return Long.parseLong(textBox.getText());
		} catch (NumberFormatException e) {
			return null;
		}
	}
    
    @Override
    public void setLongValue(Long value) {
    	hasDecimalpoint = false;
      	if (value == null){
      		textBox.setText("");
      	} else {       	
      		textBox.setText(value.toString());
      	}
    }
    
    @Override
    public void setStringValue(String value) {
    	textBox.setText(value);
    	filterTextPreserveCaretPosition(textBox.getText());
    }
    
    @Override
    public String getStringValue() {
    	return textBox.getValue();
    }
    
    @Override
    public Double getDoubleValue() {
    	try {
			return Double.parseDouble(textBox.getText());
		} catch (NumberFormatException e) {
			try {
				return Double.parseDouble(textBox.getText().replace(',', '.'));
			} catch (Exception e2) {
				return null;
			}
		}
    }
    
    @Override
    public void setDoubleValue(Double value) {
       	hasDecimalpoint = true;
       	if (value == null){
      		textBox.setText("");
      	} else {       	
      		textBox.setText(value.toString());
      	}
   }

	public void setNegativeNumEnabled() {
		this.negativeNumEnabled  = true;
	}
	
	@Override
	public boolean handlesString() {
		return true;
	}
    
}
