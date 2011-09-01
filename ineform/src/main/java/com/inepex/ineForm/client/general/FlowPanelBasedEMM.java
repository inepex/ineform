package com.inepex.ineForm.client.general;

import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.inepex.ineForm.client.resources.ResourceHelper;

public class FlowPanelBasedEMM extends FlowPanel implements ErrorMessageManagerInterface {
	
	private HTML html_error;
	
	boolean shouldRemove = false;
	boolean shouldHide = false;
	
	public FlowPanelBasedEMM() {
		this.html_error=new HTML("");
		
		setStyleName(ResourceHelper.getRes().style().fpb_errorMessageHolder_dontShowError());
	}
	
	/**
	 * clear and hide error messages
	 */
	@Override
	public void clearErrorMsg() {
		setStyleName(ResourceHelper.getRes().style().fpb_errorMessageHolder_dontShowError());
		html_error.setHTML("");
		
		if(shouldRemove) {
			getElement().removeChild(html_error.getElement());
			shouldRemove=false;
		}
		
		if (shouldHide){
			setVisible(false);
		}
	}
	
	/**
	 * adding new lines to error messages
	 */
	@Override
	public void addErrorMsg(List<String> errorlist) {
		if(errorlist!=null && errorlist.size()>0) {
			setStyleName(ResourceHelper.getRes().style().fpb_errorMessageHolder_showError());
			StringBuffer sb = new StringBuffer(html_error.getHTML());
			
			boolean first=sb.length()==0;
			for(String s : errorlist) {
				if(!first) sb.append("<br />");
				else first=false;
				
				sb.append(s);
			}
			
			html_error.setHTML(sb.toString());
			getElement().appendChild(html_error.getElement());
			shouldRemove=true;
			if (shouldHide)
				setVisible(true);
		}
	}

	public boolean isShouldHide() {
		return shouldHide;
	}

	public void setShouldHide(boolean shouldHide) {
		this.shouldHide = shouldHide;
		setVisible(false);
	}
	

}
