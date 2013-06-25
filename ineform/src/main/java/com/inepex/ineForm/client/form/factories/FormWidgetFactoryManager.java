package com.inepex.ineForm.client.form.factories;

import com.google.inject.Provider;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.client.form.widgets.FormWidget;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFW.View;
import com.inepex.ineForm.shared.customkvoeditor.CustomOdFinder;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

/**
 * Composite class for using multiple formwidgetfactories
 * @author SoTi
 *
 */
public class FormWidgetFactoryManager implements FormWidgetFactory {

	private FormWidgetFactory[] factories;
	
	public FormWidgetFactoryManager(FormWidgetFactory ... factories) {
		this.factories = factories;
	}

	@Override
	public FormWidget createWidget(
			FormContext formCtx,
			AbstractFormUnit form,
			FDesc fieldDesc,
			WidgetRDesc wrDesc,
			CustomOdFinder odFinder,
			Provider<View> customKvoView) {
		
		FormWidget createdWidget = null;
		for (FormWidgetFactory factory : factories){
			createdWidget = factory.createWidget(formCtx, form, fieldDesc, wrDesc, odFinder, customKvoView);
			if (createdWidget != null) break;
		}
		
		return createdWidget;
	}

	@Override
	public FormWidget createDecorator(FormWidget formWidget, FDesc fieldDesc, WidgetRDesc wrDesc) {
		for (FormWidgetFactory factory : factories){
			if (formWidget != null) {
				formWidget = factory.createDecorator(formWidget, fieldDesc, wrDesc);
			}
		}
		
		return formWidget;
	}
	
	
}