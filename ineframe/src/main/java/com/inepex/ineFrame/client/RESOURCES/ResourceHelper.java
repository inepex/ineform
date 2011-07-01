package com.inepex.ineFrame.client.RESOURCES;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

public class ResourceHelper {
	
	static IneFrameResources ifResources = null;
	
	public static String getImageAsString(ImageResource resource) {
		Image img = new Image();
		img.setResource(resource);
		return img.getElement().getString();
	}
	
	public static IneFrameResources getRes() {
		if (ifResources == null) {
			ifResources = GWT.create(IneFrameResources.class);
			ifResources.style().ensureInjected();
		}
		return  ifResources;
	}
}
