package com.inepex.ineFrame.client.RESOURCES;

import com.google.gwt.resources.client.CssResource;

public interface IneFrameStyle extends CssResource {
 
	String pageContent();
	
	String menu();
	String submenu();
	
	@ClassName("current_page_item")
	String current_page_item();
	
}

