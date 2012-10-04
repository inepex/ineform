package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeEvent;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeHandler;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class PhoneFW extends DenyingFormWidget {

	public final static String PART_SEPARATOR = "-";
	public final static String PLUS_SIGN = "+";
	
	public final static String COUNTRY_WIDTH = "22px";
	public final static String AREA_WIDTH = "35px";
	public final static String LOCAL_WIDTH = "80px";

	public final static int MAX_COUNTRY_LENGTH = 3;
	public final static int MAX_AREA_LENGTH = 4;
	public final static int MAX_LOCAL_LENGTH = 10;
	
	
	final FlowPanel mainPanel = new FlowPanel();
	final NumberTextBoxFW country = new NumberTextBoxFW(null);
	final NumberTextBoxFW area = new NumberTextBoxFW(null);
	final NumberTextBoxFW local = new NumberTextBoxFW(null);
		
	public PhoneFW(FDesc fielddescriptor) {
		super(fielddescriptor);
		mainPanel.add(new InlineLabel("(+"));
		mainPanel.add(country);
		mainPanel.add(new InlineLabel(") - "));
		mainPanel.add(area);
		mainPanel.add(new InlineLabel(" - "));
		mainPanel.add(local);
		initWidget(mainPanel);
		
		country.setWidth(COUNTRY_WIDTH);
		area.setWidth(AREA_WIDTH);
		local.setWidth(LOCAL_WIDTH);
		country.setMaxLength(MAX_COUNTRY_LENGTH);
		area.setMaxLength(MAX_AREA_LENGTH);
		local.setMaxLength(MAX_LOCAL_LENGTH);
	}

	@Override
	public boolean handlesString() {
		return true;
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(country.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
			@Override
			public void onFormWidgetChange(FormWidgetChangeEvent e) {
				PhoneFW.this.fireFormWidgetChanged();
			}
		}));
		
		registerHandler(area.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
			@Override
			public void onFormWidgetChange(FormWidgetChangeEvent e) {
				PhoneFW.this.fireFormWidgetChanged();
			}
		}));
		
		registerHandler(local.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
			@Override
			public void onFormWidgetChange(FormWidgetChangeEvent e) {
				PhoneFW.this.fireFormWidgetChanged();
			}
		}));
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		country.setEnabled(enabled);
		area.setEnabled(enabled);
		local.setEnabled(enabled);
	}
	
	@Override
	public void setStringValue(String value) {
		if(value==null || "".equals(value)) {
			country.setLongValue(null);
			area.setLongValue(null);
			local.setLongValue(null);
		} else {
			String[] parts = value.split("["+PART_SEPARATOR+"]");
			String p0 = parts[0].replace(PLUS_SIGN, "");
			String p1 = parts[1];
			String p2 = parts[2];
			
			try {
				country.setLongValue(
						p0.length() > 0 ? Long.parseLong(p0) : null);
				area.setLongValue(
						p1.length() > 0 ? Long.parseLong(p1) : null);
				local.setLongValue(
						p2.length() > 0 ? Long.parseLong(p2) : null);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getStringValue() {
		
		Long countryValue = country.getLongValue();
		Long areaValue = area.getLongValue();
		Long localValue = local.getLongValue();
		
		if(countryValue==null && areaValue==null && localValue==null) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(PLUS_SIGN)
			  .append(longToString(countryValue))
			  .append(PART_SEPARATOR)
			  .append(longToString(areaValue))
			  .append(PART_SEPARATOR)
			  .append(longToString(localValue));
			
			return sb.toString();
		}
	}
	
	private String longToString(Long l) {
		if(l == null)
			return "";
		else
			return l.toString();
	}
	

}
