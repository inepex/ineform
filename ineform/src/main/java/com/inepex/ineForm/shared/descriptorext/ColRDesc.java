package com.inepex.ineForm.shared.descriptorext;


@SuppressWarnings("serial")
public class ColRDesc extends TableRDescBase {

	public static final int DEF_CORPS_WITH=30;
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
	private int corpsWidth=DEF_CORPS_WITH;
	
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

	public int getCorpsWidth() {
		return corpsWidth;
	}
	
	public ColRDesc corpsWidth(int customCorpsWidth) {
		this.corpsWidth = customCorpsWidth;
		return this;
	}
	
}
