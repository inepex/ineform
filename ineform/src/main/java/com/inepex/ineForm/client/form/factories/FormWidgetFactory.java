package com.inepex.ineForm.client.form.factories;

import com.google.inject.Provider;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.client.form.prop.PropFW;
import com.inepex.ineForm.client.form.widgets.FormWidget;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

/**
 * FormWidgetFactory create the widgets of the display... use DefaultFormWidgetFactory or create a new one
 *
 */
public interface FormWidgetFactory {
	FormWidget createWidget(FormContext formCtx
			 , AbstractFormUnit form
			 , FDesc fieldDesc
			 , WidgetRDesc wrDesc
			 , Provider<PropFW.View> propView);
	
	FormWidget createDecorator(FormWidget formWidget, FDesc fieldDesc, WidgetRDesc wrDesc);
}
