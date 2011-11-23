package com.inepex.ineForm.client.form.factories;

import com.google.inject.Provider;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.client.form.widgets.CaptchaFW;
import com.inepex.ineForm.client.form.widgets.CheckBoxFW;
import com.inepex.ineForm.client.form.widgets.DummyFW;
import com.inepex.ineForm.client.form.widgets.EnumLabelFW;
import com.inepex.ineForm.client.form.widgets.EnumListFW;
import com.inepex.ineForm.client.form.widgets.FormWidget;
import com.inepex.ineForm.client.form.widgets.IneTableFW;
import com.inepex.ineForm.client.form.widgets.LabelBoolFW;
import com.inepex.ineForm.client.form.widgets.LabelFW;
import com.inepex.ineForm.client.form.widgets.LabelledFW;
import com.inepex.ineForm.client.form.widgets.ListBoxFW;
import com.inepex.ineForm.client.form.widgets.NumberTextBoxFW;
import com.inepex.ineForm.client.form.widgets.PasswordTextBoxFW;
import com.inepex.ineForm.client.form.widgets.PhoneFW;
import com.inepex.ineForm.client.form.widgets.RadioBoolFW;
import com.inepex.ineForm.client.form.widgets.RadioEnumSelectorFW;
import com.inepex.ineForm.client.form.widgets.RelationFW;
import com.inepex.ineForm.client.form.widgets.RelationListFW;
import com.inepex.ineForm.client.form.widgets.StringListBoxFw;
import com.inepex.ineForm.client.form.widgets.StringListFw;
import com.inepex.ineForm.client.form.widgets.SuggestBoxFw;
import com.inepex.ineForm.client.form.widgets.TextAreaFW;
import com.inepex.ineForm.client.form.widgets.TextBoxFW;
import com.inepex.ineForm.client.form.widgets.ThreeWayBoolFw;
import com.inepex.ineForm.client.form.widgets.chooser.ChooserFw;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFW;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOReadOnlyFW;
import com.inepex.ineForm.client.form.widgets.customkvo.OdFinder;
import com.inepex.ineForm.client.form.widgets.datetime.DateTimeFW;
import com.inepex.ineForm.client.form.widgets.richtextarea.RichTextAreaFW;
import com.inepex.ineForm.client.form.widgets.upload.FileUploadFw;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ListFDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;

public class DefaultFormWidgetFactory implements FormWidgetFactory {

	/**
	 * create a widget using fieldDesc's and wrDesc's meta informations
	 */
	@Override
	public FormWidget createWidget(FormContext formCtx
								 , AbstractFormUnit form
								 , FDesc fieldDesc
								 , WidgetRDesc wrDesc
								 , OdFinder odFinder
								 , Provider<CustomKVOFW.View> customKvoView) {
		
		FormWidget createdWidget = null;
		
		FWTypes widgetType = wrDesc.getFormWidgetType();

		if (widgetType.equals(FWTypes.DONTRENDER))
			createdWidget = new DummyFW();
		
		else if (widgetType.equals(FWTypes.TEXTBOX))
			createdWidget = new TextBoxFW(fieldDesc, wrDesc);
		
		else if (widgetType.equals(FWTypes.CAPTCHA))
			createdWidget = new CaptchaFW(fieldDesc);

		else if (widgetType.equals(FWTypes.PASSWORDTEXTBOX))
			createdWidget = new PasswordTextBoxFW(fieldDesc, wrDesc);
		
		else if (widgetType.equals(FWTypes.TEXTAREA))
			createdWidget = new TextAreaFW(fieldDesc, wrDesc);
		
		else if (widgetType.equals(FWTypes.LABEL))
			createdWidget = new LabelFW(fieldDesc, wrDesc.hasProp(FWTypes.p_asDate), wrDesc.getPropValue(LabelFW.NULLALTERTEXT), formCtx.dateProvider);
		
		else if (widgetType.equals(FWTypes.ENUMLABEL)) {
			if (wrDesc.getPropValue(EnumLabelFW.enumValues) == null){
				throw new RuntimeException("No '" + EnumLabelFW.enumValues + "' propery found for EnumLabel: " + fieldDesc.getKey());
			} else createdWidget = new EnumLabelFW(fieldDesc, wrDesc.getPropValue(EnumLabelFW.enumValues));
		}
		
		else if (widgetType.equals(FWTypes.RADIOENUMSELECTOR)) {
			if (wrDesc.getPropValue(RadioEnumSelectorFW.enumValues) == null){
				throw new RuntimeException("No '" + RadioEnumSelectorFW.enumValues + "' propery found for EnumLabel: " + fieldDesc.getKey());
			} else createdWidget = new RadioEnumSelectorFW(fieldDesc, wrDesc.getPropValue(EnumLabelFW.enumValues), wrDesc);
		}
		
		else if (widgetType.equals(FWTypes.LISTBOX))
			createdWidget = new ListBoxFW(formCtx, fieldDesc, wrDesc);
		
		else if (widgetType.equals(FWTypes.DATEBOX))
			createdWidget = new DateTimeFW(formCtx.dateProvider, fieldDesc, wrDesc.getProps());

		else if (widgetType.equals(FWTypes.RELATEDFORM))
			createdWidget = new RelationFW(formCtx, (RelationFDesc) fieldDesc, wrDesc.getPropValue(RelationFW.FRD));
		
		else if (widgetType.equals(FWTypes.CUSTOMKVO))
			createdWidget= new CustomKVOFW((RelationFDesc) fieldDesc, wrDesc, odFinder,  customKvoView.get());

		else if (widgetType.equals(FWTypes.CUSTOMKVOREADONLY))
			createdWidget= new CustomKVOReadOnlyFW((RelationFDesc) fieldDesc, wrDesc, odFinder);
		
		else if (widgetType.equals(FWTypes.CHECKBOX))
			createdWidget = new CheckBoxFW(fieldDesc, wrDesc);

		else if (widgetType.equals(FWTypes.PHONE))
			createdWidget = new PhoneFW(fieldDesc);

		else if (widgetType.equals(FWTypes.STRINGLIST))
			createdWidget = new StringListFw(fieldDesc);

		else if (widgetType.equals(FWTypes.NUMBERTEXTBOX)){
			NumberTextBoxFW numberTextbox = new NumberTextBoxFW(fieldDesc);
			if (fieldDesc.getType() == IneT.DOUBLE) numberTextbox.setHasDecimalPoint(true);
			if(wrDesc.getPropValue(NumberTextBoxFW.FRACTIONALDIGITCONT)!=null) {
				numberTextbox.setMaxFractDigits(Integer.parseInt(wrDesc.getPropValue(NumberTextBoxFW.FRACTIONALDIGITCONT)));
			}
			if(wrDesc.getPropValue(NumberTextBoxFW.WHOLEDIGITCONT)!=null) {
				numberTextbox.setMaxWholeDigits(Integer.parseInt(wrDesc.getPropValue(NumberTextBoxFW.WHOLEDIGITCONT)));
			}			
			
			createdWidget = numberTextbox;
		} else if (widgetType.equals(FWTypes.RELATIONLIST)) {
			ListFDesc castedFieldDesc = (ListFDesc) fieldDesc; 
			createdWidget = new RelationListFW(formCtx, castedFieldDesc, castedFieldDesc.getRelatedDescriptorType());
		} else if (widgetType.equals(FWTypes.TABLE)) {
			ListFDesc castedFieldDesc = (ListFDesc) fieldDesc; 
			createdWidget = new IneTableFW(castedFieldDesc, formCtx, castedFieldDesc.getRelatedDescriptorType(),
					wrDesc.hasProp(IneTableFW.PROP_SINGLESELECT));
		} else if (widgetType.equals(FWTypes.ENUMLISTBOX)) {
			if (wrDesc.getPropValue(EnumListFW.enumValues) == null){
				throw new RuntimeException("No '" + EnumListFW.enumValues + "' propery found for EnumListBox: " + fieldDesc.getKey());
			} else
			createdWidget = new EnumListFW(fieldDesc, wrDesc, wrDesc.getPropValue(EnumListFW.enumValues));
		} else if (widgetType.equals(FWTypes.CHOOSER)){
			ListFDesc castedFieldDesc = (ListFDesc) fieldDesc; 
			createdWidget = new ChooserFw(formCtx
					, castedFieldDesc
					, wrDesc
					, castedFieldDesc.getRelatedDescriptorType());
		} else if (widgetType.equals(FWTypes.FILEUPLOAD)){
			boolean withImageFinder = wrDesc.hasProp(FileUploadFw.hasImageFinderKey);
			boolean withPreview = wrDesc.hasProp(FileUploadFw.hasPreviewKey);
			createdWidget = new FileUploadFw(
					fieldDesc
					, wrDesc
					, withImageFinder
					, withPreview);			
		} else if(wrDesc.getFormWidgetType().equals(FWTypes.RADIOBOOL)) {
			createdWidget=new RadioBoolFW(fieldDesc, wrDesc.getPropValue("true"), wrDesc.getPropValue("false"), wrDesc.hasProp(RadioBoolFW.VERTICAL));
		} else if(wrDesc.getFormWidgetType().equals(FWTypes.LABELBOOL)) {
			createdWidget=new LabelBoolFW(fieldDesc, wrDesc.getPropValue("true"), wrDesc.getPropValue("false"));
		} else if(wrDesc.getFormWidgetType().equals(FWTypes.THREEWAYBOOL)) {
			createdWidget=new ThreeWayBoolFw(fieldDesc, wrDesc.getPropValue("null"), wrDesc.getPropValue("true"), wrDesc.getPropValue("false"));
		} else if(wrDesc.getFormWidgetType().equals(FWTypes.RICHTEXTAREA)) {
			createdWidget = new RichTextAreaFW(fieldDesc, wrDesc);
		} else if(wrDesc.getFormWidgetType().equals(FWTypes.STRINGLISTBOX)) {
			createdWidget = new StringListBoxFw(fieldDesc, wrDesc);
		} else  if(wrDesc.getFormWidgetType().equals(FWTypes.SUGGESTBOX)) {
			createdWidget = new SuggestBoxFw(formCtx, fieldDesc);
		}
		
		//setting label or html after fw
		if(createdWidget!=null && wrDesc.hasProp(FWTypes.p_label)) {
			return new LabelledFW(createdWidget, wrDesc.getPropValue(FWTypes.p_label));
		}
		
		return createdWidget;
		
	}

}
