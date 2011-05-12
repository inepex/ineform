package com.inepex.ineForm.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface IneResources extends ClientBundle {
	IneResources INSTANCE = GWT.create(IneResources.class);

	ImageResource arrowLeft();
	ImageResource arrowLeft_disabled();

	ImageResource arrowRight();
	ImageResource arrowRight_disabled();
	
    ImageResource downArrow();
    ImageResource upArrow();
    
    ImageResource calendar();
    ImageResource calendar_disabled();
}
