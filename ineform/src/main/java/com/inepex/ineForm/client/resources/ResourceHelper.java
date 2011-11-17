package com.inepex.ineForm.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTable.Resources;
import com.google.gwt.user.client.ui.Image;

public class ResourceHelper {

	private static IneResources imageResources = null;
	private static IneFormResources ifResources = null;
	private static Resources cellTableResources = null;
	
	//image helper
	public static String getImageAsString(ImageResource resource) {
		Image img = new Image();
		img.setResource(resource);
		return img.getElement().getString();
	}
	
	//ineform resources
	public static IneFormResources ineformRes() {
		if (ifResources == null) {
			ifResources = GWT.create(IneFormResources.class);
			ifResources.style().ensureInjected();
		}
		return  ifResources;
	}
	
	public static void setIfResources(IneFormResources ifResources) {
		ResourceHelper.ifResources = ifResources;
	}
	
	
	//celltable resources
	public static Resources cellTableResources() {
		if(cellTableResources==null)
			cellTableResources = GWT.create(IneCellTableResources.class);
		
		return cellTableResources;
	}
	
	public static void setCellTableResources(Resources cellTableResources) {
		ResourceHelper.cellTableResources = cellTableResources;
	}
	
	public interface IneCellTableResources extends Resources {
        /**
         * The styles used in this widget.
         */
        @Source("com/inepex/ineForm/client/STYLES/IneCellTable.css")
        CellTable.Style cellTableStyle();
	}
	
	//image resources
	public static void setImageResources(IneResources imageResources) {
		ResourceHelper.imageResources = imageResources;
	}
	
	public static IneResources imageResources() {
		if(imageResources==null)
			imageResources=GWT.create(IneResources.class);
		
		return imageResources;
	}
}
