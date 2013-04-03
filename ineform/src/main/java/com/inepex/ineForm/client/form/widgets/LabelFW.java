package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.InlineHTML;
import com.inepex.ineFrame.shared.util.date.DateProvider;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class LabelFW extends StringFormWidget {
	
	public final static String NULLALTERTEXT = "nullAlterText";

	protected Object storedObject = null;
	
	final InlineHTML label = new InlineHTML();
	
	final boolean showLongAsDate;
	final String nullAlterText;
	DateProvider dateProvider;
	
	public LabelFW(FDesc fielddescriptor, boolean showLongAsDate, String nullAlterText, DateProvider dateProvider) {
		super(fielddescriptor);
		this.dateProvider = dateProvider;
		
		if(nullAlterText==null) 
			this.nullAlterText="";
		else 
			this.nullAlterText=nullAlterText;
		
		this.showLongAsDate = showLongAsDate;
		initWidget(label);
	}
	
	@Override
	public String getStringValue() {
		return label.getHTML();
	}

	@Override
	public boolean isFocusable() {
		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {

	}

	@Override
	public void setFocus(boolean focused) {

	}

	@Override
	public void setStringValue(String value) {
		if(value==null || value.length()==0)
			label.setHTML(nullAlterText);
		else 
			label.setHTML(value);
	}
	
	@Override
	public void setLongValue(Long value) {
		if (value == null) {
			label.setHTML(nullAlterText);
			return;
		}
			
		if (showLongAsDate) {
			label.setHTML(dateProvider.getDate(value).toString());
		} else {
			super.setLongValue(value);
		}
	}
	
	@Override
	public boolean isReadOnlyWidget() {
		return true;
	}
	
	@Override
	public boolean handlesList() {
		return true;
	}
	
	@Override
	public boolean handlesRelation() {
		return true;
	}
	
	@Override
	public IneList getListValue() {
		return (IneList) storedObject;
	}

	@Override
	public Relation getRelationValue() {
		return (Relation) storedObject;
	}
	
	@Override
	public void setListValue(IneList value) {
		storedObject = value;
		if(value==null || value.getRelationList() == null || value.getRelationList().size() == 0)
			return;
		StringBuilder sb = new StringBuilder();
		for (Relation rel : value.getRelationList()) {
			sb.append(rel.getDisplayName());
			sb.append("<br />");
		}
		label.setHTML(sb.substring(0, sb.length()-6));
	}
	
	@Override
	public void setRelationValue(Relation value) {
		storedObject = value;
		if (value == null) {
			label.setHTML(nullAlterText);
			return;
		}
			
		setStringValue(value.getDisplayName());
	}

}
