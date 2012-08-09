package com.inepex.ineForm.shared.descriptorext;

@SuppressWarnings("serial")
public class ColRDesc extends TableRDescBase {

	public static final int DEF_CROP_WITH=30;
	public static final String HALIGN = "HALIGN";
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
	
	private int columnWidth = -1;
	private boolean sortable = false;
	private int cropWidth=DEF_CROP_WITH;
	
	public ColRDesc() {
	}
		
	public ColRDesc(boolean sortable) {
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

	public boolean hasColumnWidth() {
		return columnWidth!=-1;
	}
	
	public String getColumnWidthAsString() {
		return columnWidth+"px";
	}
	
	public ColRDesc setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
		return this;
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

	public int getCropWidth() {
		return cropWidth;
	}
	
	public int getColumnWidth() {
		return columnWidth;
	}
	
	/**
	 * Sets how many characters is the maximum that can be rendered in a specific column. Default is DEF_CROP_WIDTH.
	 * If set to 0, than the string will not be cropped at all.
	 * 
	 * @param customCorpsWidth
	 * @return
	 */
	public ColRDesc cropWidth(int customCorpsWidth) {
		this.cropWidth = customCorpsWidth;
		return this;
	}
	
	public boolean hasHAlign() {
		return hasProp(HALIGN);
	}
	
	public ColRDesc hAlign(ColRDescHAlign hAlign) {
		addProp(HALIGN, hAlign.toString());
		return this;
	}
	
	public ColRDescHAlign getHAlign() {
		return ColRDescHAlign.valueOf(getPropValue(HALIGN));
	}
}

