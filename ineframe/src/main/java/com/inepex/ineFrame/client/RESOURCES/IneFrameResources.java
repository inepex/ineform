package com.inepex.ineFrame.client.RESOURCES;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface IneFrameResources extends ClientBundle {

	@Source("BundledStyle.css")
	IneFrameStyle style();
	
	@Source("menu.png")
	@ImageOptions(repeatStyle=RepeatStyle.Horizontal)
	ImageResource menu();
	
	@Source("menu_hover.png")
	@ImageOptions(repeatStyle=RepeatStyle.Horizontal)
	ImageResource menu_hover();

	@Source("menu_on.png")
	@ImageOptions(repeatStyle=RepeatStyle.Horizontal)
	ImageResource menu_on();
	
	@Source("submenu.png")
	@ImageOptions(repeatStyle=RepeatStyle.Horizontal)
	ImageResource submenu();
	
	@Source("submenu_li.png")
	@ImageOptions(repeatStyle=RepeatStyle.Horizontal)
	ImageResource submenu_li();
	
	@Source("submenu_on.png")
	@ImageOptions(repeatStyle=RepeatStyle.Horizontal)
	ImageResource submenu_on();
	
	@Source("logo.png")
	ImageResource logo();
	
	@Source("settings.png")
	ImageResource settings();

	@Source("selector_arrow_bg.gif")
	@ImageOptions(repeatStyle=RepeatStyle.Horizontal)
	ImageResource selector_arrow_bg();
	
	@Source("selector_item_bg.gif")
	@ImageOptions(repeatStyle=RepeatStyle.Horizontal)
	ImageResource selector_item_bg();
	
	@Source("selector_up_arrow.gif")
	ImageResource selector_up_arrow();
	
	@Source("selector_down_arrow.gif")
	ImageResource selector_down_arrow();
	
	ImageResource headerDrawerIcon();
	ImageResource headerBackIcon();
	ImageResource headerBreadcrumbIcon();
}
