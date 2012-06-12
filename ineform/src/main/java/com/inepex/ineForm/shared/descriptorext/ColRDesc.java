package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineom.shared.descriptor.Prop;

public class ColRDesc extends TableRDescBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5327126872180935006L;

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
		super();
		this.columnWidth = columnWidth;
		this.sortable = sortable;
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
	
	public ColRDesc prop(Prop prop) {
		props.put(prop.getName(), prop);
		return this;
	}
	
	public ColRDesc prop(String prop) {
		try {
			Prop p_prop = Prop.fromString(prop);
			return prop(p_prop);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
		return this.prop(AS_DATE);
	}
	
	public ColRDesc setMaxDisplayedFractialDigits(int digitCount){
		return this.prop(AS_FRACTIALDIGITCOUNT+":"+digitCount);
	}
	
	public ColRDesc asShortDate(){
		return this.prop(AS_SHORTDATE);
	}
	
	public ColRDesc setDisplayName(String name) {
		return this.setDisplayName(name, ColRDesc.class);
	}
	
	public ColRDesc asDateWithSec() {
		return this.prop(AS_DATE_WITHSEC);
	}

	public Integer getCustomCorpsWidth() {
		return customCorpsWidth;
	}
	
	public ColRDesc customCorpsWidth(Integer customCorpsWidth) {
		this.customCorpsWidth = customCorpsWidth;
		return this;
	}
	
}
