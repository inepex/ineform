package com.inepex.ineForm.client.general;

import java.util.List;

import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.inepex.ineForm.client.resources.ResourceHelper;

public class SimpleTableErrorMessageManager implements ErrorMessageManagerInterface {
	
	private HTML html_error;
	private Element holder;
	boolean shouldRemove=false;
	boolean shouldHide = false;
	
	public SimpleTableErrorMessageManager(Element holder) {
		this.html_error=new HTML("");
		this.holder=holder;
		holder.setClassName(ResourceHelper.getRes().style().errorMessageHolder_dontShowError());
	}
	
	/**
	 * clear and hide error messages
	 */
	@Override
	public void clearErrorMsg() {
		holder.setClassName(ResourceHelper.getRes().style().errorMessageHolder_dontShowError());
		html_error.setHTML("");
		
		if(shouldRemove) {
			holder.removeChild(html_error.getElement());
			shouldRemove=false;
		}
		
		if (shouldHide){
			holder.getStyle().setVisibility(Visibility.HIDDEN);
		}
	}
	
	/**
	 * adding new lines to error messages
	 */
	@Override
	public void addErrorMsg(List<String> errorlist) {
		if(errorlist!=null && errorlist.size()>0) {
			holder.setClassName(ResourceHelper.getRes().style().errorMessageHolder_showError());
			StringBuffer sb = new StringBuffer(html_error.getHTML());
			
			boolean first=sb.length()==0;
			for(String s : errorlist) {
				if(!first) sb.append("<br />");
				else first=false;
				
				sb.append(s);
			}
			
			html_error.setHTML(sb.toString());
			holder.appendChild(html_error.getElement());
			shouldRemove=true;
			if (shouldHide)	holder.getStyle().setVisibility(Visibility.VISIBLE);
		}
	}

	public boolean isShouldHide() {
		return shouldHide;
	}

	public void setShouldHide(boolean shouldHide) {
		this.shouldHide = shouldHide;
		holder.getStyle().setVisibility(Visibility.HIDDEN);
	}
	
	
}

