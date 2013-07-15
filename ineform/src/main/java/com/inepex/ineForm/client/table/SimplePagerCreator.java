package com.inepex.ineForm.client.table;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.Resources;
import com.google.gwt.user.cellview.client.SimplePager.Style;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.inepex.ineForm.client.IneFormProperties;

public class SimplePagerCreator {

	private static SimplePager.Resources resources;
	
	public static SimplePager create() {
		return new SimplePager(TextLocation.CENTER, getResources(), false, 0, true);
	}

	private static Resources getResources() {
		if(resources==null) {
			if(IneFormProperties.OLD_STYLE_COMPATIBILITY)
				resources = GWT.create(SimplePager.Resources.class);
			else
				resources = GWT.create(PagerResources.class);
			
			resources.simplePagerStyle().ensureInjected();
		}
		
		return resources;
	}
	
	
	public static interface PagerResources extends SimplePager.Resources, ClientBundle{
		
		@Override
	    ImageResource simplePagerFastForward();

	    @Override
	    ImageResource simplePagerFastForwardDisabled();

	    @Override
	    ImageResource simplePagerFirstPage();

	    @Override
	    ImageResource simplePagerFirstPageDisabled();

	    @Override
	    ImageResource simplePagerLastPage();

	    @Override
	    ImageResource simplePagerLastPageDisabled();

	    @Override
	    ImageResource simplePagerNextPage();

	    @Override
	    ImageResource simplePagerNextPageDisabled();

	    @Override
	    ImageResource simplePagerPreviousPage();

	    
	    @Override
	    ImageResource simplePagerPreviousPageDisabled();
		
		@Override
		@Source("SimplePager.css")
		public Style simplePagerStyle();
	}
	
	
	private SimplePagerCreator(){}
}
