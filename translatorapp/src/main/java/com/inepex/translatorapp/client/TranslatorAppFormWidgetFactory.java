package com.inepex.translatorapp.client;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.factories.FormWidgetFactory;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.client.form.prop.PropFW.View;
import com.inepex.ineForm.client.form.widgets.FormWidget;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.translatorapp.client.fw.TextBoxWithPopupEditorFw;

public class TranslatorAppFormWidgetFactory implements FormWidgetFactory {

	@Inject
	public TranslatorAppFormWidgetFactory() {
	}
	
	@Override
	public FormWidget createWidget(
			FormContext formCtx,
			AbstractFormUnit form,
			FDesc fieldDesc,
			WidgetRDesc wrDesc,
			Provider<View> propView) {
		FormWidget createdWidget = null;
		if (wrDesc.getFormWidgetType().equals(TranslatorAppFwTypes.TEXTBOXWITHPOPUPEDITOR)) {
			createdWidget = new TextBoxWithPopupEditorFw(fieldDesc, wrDesc);
		}
		return createdWidget;
	}

	@Override
	public FormWidget createDecorator(FormWidget formWidget, FDesc fieldDesc, WidgetRDesc wrDesc) {
		return formWidget;
	}

}
