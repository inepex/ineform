package com.inepex.ineForm.client.general;

import java.util.List;

import com.google.gwt.user.client.Timer;
import com.inepex.ineForm.client.form.widgets.FormWidget;

public class FlashingErrorMessageManager implements ErrorMessageManegerInterFace {
	
	private final FormWidget widget;
	
	public FlashingErrorMessageManager(FormWidget widget) {
		this.widget = widget;
	}

	@Override
	public void clearErrorMsg() {
	}

	@Override
	public void addErrorMsg(List<String> errorlist) {
		if(errorlist!=null && errorlist.size()>0) {
			Timer t = new Timer() {
				int i=0;
				
				@Override
				public void run() {
					if(i==6) {
						cancel();
						return;
					}
						
					if(i%2==0) {
						widget.addStyleName("flashingBorder");
					} else {
						widget.removeStyleName("flashingBorder");
					}
					i++;
				}
			};
			
			t.scheduleRepeating(100);
		}
	}

}
