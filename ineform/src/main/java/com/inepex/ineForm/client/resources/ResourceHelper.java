package com.inepex.ineForm.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

public class ResourceHelper {
	
	static IneFormResources ifResources = null;
	
	public static String getImageAsString(ImageResource resource) {
		Image img = new Image();
		img.setResource(resource);
		return img.getElement().getString();
	}
	
	public static IneFormResources getRes() {
		if (ifResources == null) {
			ifResources = GWT.create(IneFormResources.class);
			ifResources.style().ensureInjected();
		}
		return  ifResources;
	}
	
}
