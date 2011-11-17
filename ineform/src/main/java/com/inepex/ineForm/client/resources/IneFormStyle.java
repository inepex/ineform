package com.inepex.ineForm.client.resources;

import com.google.gwt.resources.client.CssResource;

public interface IneFormStyle extends CssResource {
 
	String clickable();
	
	String selectorPanel();
	String selector();

	String errorMessageHolder_dontShowError(); 
	String errorMessageHolder_showError(); 

	String ineTable();
	
	//simpleTableFormUnit's stuffs
	String simpleTableFormUnit();
	String cellTitle();   
	String cellContent();
	String mandatorySign();
	String evenCellContent();
	String oddCellContent();
	
	String flashingBorder(); 

	//form widgets
	String ChooserFw();
	String richTextArea();
	String displayInline();
	
	String fpb_errorMessageHolder_dontShowError();
	String fpb_errorMessageHolder_showError();
	
	String customKVOHeader();
	String customKVOHeaderType();
	String listBoxExtraDiv();
}

