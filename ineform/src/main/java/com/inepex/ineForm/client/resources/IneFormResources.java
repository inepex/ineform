package com.inepex.ineForm.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface IneFormResources extends ClientBundle {

    @Source("BundledStyle.css")
    IneFormStyle style();

    ImageResource form_error();

    ImageResource rowcommand_delete();

    ImageResource rowcommand_edit();

    ImageResource arrowLeft();

    ImageResource arrowRight();

    ImageResource downArrow();

    ImageResource upArrow();

    ImageResource calendar();
}
