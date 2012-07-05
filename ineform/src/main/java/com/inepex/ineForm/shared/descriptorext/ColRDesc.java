package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineom.shared.descriptor.DescriptorBase;

@SuppressWarnings("serial")
public class ColRDesc extends TableRDescBase {

	public static final String AS_DATE = "AS_DATE";
	public static final String AS_DATE_WITHSEC = "AS_DATE_WITHSEC";
	public static final String AS_SHORTDATE = "AS_SHORTDATE";
	public static final String DEFAULTSORT = "DEFAULTSORT";
	public static final String DEFAULTSORTREVERSE = "DEFAULTSORTREVERSE";
	public static final String AS_FRACTIALDIGITCOUNT = "AS_SHOWFRACTIAL";
	public static final String AS_GROUPTHOUSANDS = "AS_GROUPTHOUSANDS";
	public static final String AS_FORMATTEDDOUBLE = "AS_FORMATTEDDOUBLE";
	
	public static final String EXCEL_DATETIMEFORMAT = "EXCEL_DATETIMEFORMAT";
	public static final String EXCEL_NUMBERFORMAT = "EXCEL_NUMBERFORMAT";
	
	private int columnWidth = 100;
	
	private boolean sortable = false;

	private Integer customCorpsWidth;
	
	public ColRDesc() {
	}
	
	public ColRDesc(int columnWidth) {
		this.columnWidth = columnWidth;
	}
		
	public ColRDesc(int columnWidth, boolean sortable) {
		this.columnWidth = columnWidth;
		this.sortable = sortable;
	}
	
	@Override
	public ColRDesc addProp(String name, String value) {
		super.addProp(name, value);
		return this;
	}
	
	@Override
	public ColRDesc addProp(String prop) {
		super.addProp(prop);
		return this;
	}

	public int getColumnWidth() {
		return columnWidth;
	}
	
	public String getColumnWidthAsString() {
		return columnWidth+"px";
	}
	
	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}

	public boolean isSortable() {
		return sortable;
	}

	public ColRDesc setSortable(boolean sortable) {
		this.sortable = sortable;
		return this;
	}
	
	public ColRDesc defaultSortColumn() {
		this.sortable=true;
		addProp(DEFAULTSORT);
		return this;
	}
	
	public ColRDesc asDate(){
		addProp(AS_DATE);
		return this;
	}
	
	public ColRDesc setMaxDisplayedFractialDigits(int digitCount){
		addProp(AS_FRACTIALDIGITCOUNT, ""+digitCount);
		return this;
	}
	
	public ColRDesc asShortDate(){
		addProp(AS_SHORTDATE);
		return this;
	}
	
	public ColRDesc setDisplayName(String name) {
		return setDisplayName(name, ColRDesc.class);
	}
	
	public ColRDesc asDateWithSec() {
		addProp(AS_DATE_WITHSEC);
		return this;
	}

	public Integer getCustomCorpsWidth() {
		return customCorpsWidth;
	}
	
	public ColRDesc customCorpsWidth(Integer customCorpsWidth) {
		this.customCorpsWidth = customCorpsWidth;
		return this;
	}
	
}
