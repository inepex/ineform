package com.inepex.translatorapp.shared.assist;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.kvo.LangConsts;
import com.inepex.translatorapp.shared.kvo.ModuleRowConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;
import com.inepex.translatorapp.shared.kvo.UserConsts;

public class TranslatedValueAssist extends Assist {
	
	public TranslatedValueAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(TranslatedValueConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(TranslatedValueConsts.descriptorName
			, new LongFDesc(TranslatedValueConsts.k_id, /*hc:d1*/translatorappI18n.translatedValue_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new LongFDesc(TranslatedValueConsts.k_lastModTime, /*hc:d2*/translatorappI18n.translatedValue_lastModTime()/*hc*/)/*hc:d2_2*//*hc*/
			, new RelationFDesc(TranslatedValueConsts.k_lastModUser, /*hc:d3*/translatorappI18n.translatedValue_lastModUser()/*hc*/
										, UserConsts.descriptorName)/*hc:d2_3*//*hc*/
			, new StringFDesc(TranslatedValueConsts.k_value, /*hc:d4*/translatorappI18n.translatedValue_value()/*hc*/)/*hc:d2_4*//*hc*/
			, new RelationFDesc(TranslatedValueConsts.k_lang, /*hc:d5*/translatorappI18n.translatedValue_lang()/*hc*/
										, LangConsts.descriptorName)/*hc:d2_5*//*hc*/
					.mandatory()
			, new RelationFDesc(TranslatedValueConsts.k_row, /*hc:d6*/translatorappI18n.translatedValue_row()/*hc*/
										, ModuleRowConsts.descriptorName)/*hc:d2_6*//*hc*/
					.mandatory()
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		return new TableRDesc(TranslatedValueConsts.descriptorName);
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(TranslatedValueConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(TranslatedValueConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(TranslatedValueConsts.k_lang, new WidgetRDesc(/*hc:f5*/FWTypes.LABEL/*hc*/))
			.addChild(TranslatedValueConsts.k_lastModTime, new WidgetRDesc(/*hc:f2*/FWTypes.LABEL, FWTypes.p_asDate/*hc*/))
			.addChild(TranslatedValueConsts.k_lastModUser, new WidgetRDesc(/*hc:f3*/FWTypes.LABEL/*hc*/))
			.addChild(TranslatedValueConsts.k_value, new WidgetRDesc(/*hc:f4*/FWTypes.TEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(TranslatedValueConsts.searchDescriptor
			, new LongFDesc(TranslatedValueConsts.s_id, /*hc:ds1*/"Id"/*hc*/)
			, new RelationFDesc(TranslatedValueConsts.s_lang, /*hc:ds5*/"Lang"/*hc*/
										, LangConsts.searchDescriptor)
			, new RelationFDesc(TranslatedValueConsts.s_row, /*hc:ds6*/"Row"/*hc*/
										, ModuleRowConsts.searchDescriptor)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(TranslatedValueConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(TranslatedValueConsts.s_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(TranslatedValueConsts.s_lang, new WidgetRDesc(/*hc:fs5*/FWTypes.LISTBOX/*hc*/))

			.addChild(TranslatedValueConsts.s_row, new WidgetRDesc(/*hc:fs6*/FWTypes.LISTBOX/*hc*/))

			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}