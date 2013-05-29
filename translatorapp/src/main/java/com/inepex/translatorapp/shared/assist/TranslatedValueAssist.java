package com.inepex.translatorapp.shared.assist;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
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
import com.inepex.translatorapp.shared.kvo.LangConsts;
import com.inepex.translatorapp.shared.kvo.ModuleRowConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;
import com.inepex.translatorapp.shared.kvo.UserConsts;

public class TranslatedValueAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"translatedValue_id";"";"Id";"Id"
//"translatedValue_lastModTime";"";"LastModTime";"LastModTime"
//"translatedValue_lastModUser";"";"LastModUser";"LastModUser"
//"translatedValue_value";"";"Value";"Value"
//"translatedValue_lang";"";"Lang";"Lang"
//"translatedValue_row";"";"Row";"Row"
//
		public static String translatedValue_id() { return /*hc:i18n_1*/"Id"/*hc*/;}
		public static String translatedValue_lastModTime() { return /*hc:i18n_2*/"LastModTime"/*hc*/;}
		public static String translatedValue_lastModUser() { return /*hc:i18n_3*/"LastModUser"/*hc*/;}
		public static String translatedValue_value() { return /*hc:i18n_4*/"Value"/*hc*/;}
		public static String translatedValue_lang() { return /*hc:i18n_5*/"Lang"/*hc*/;}
		public static String translatedValue_row() { return /*hc:i18n_6*/"Row"/*hc*/;}
	}
	
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
			, new LongFDesc(TranslatedValueConsts.k_id, /*hc:d1*/I18n.translatedValue_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new LongFDesc(TranslatedValueConsts.k_lastModTime, /*hc:d2*/I18n.translatedValue_lastModTime()/*hc*/)/*hc:d2_2*//*hc*/
			, new RelationFDesc(TranslatedValueConsts.k_lastModUser, /*hc:d3*/I18n.translatedValue_lastModUser()/*hc*/
										, UserConsts.descriptorName)/*hc:d2_3*//*hc*/
			, new StringFDesc(TranslatedValueConsts.k_value, /*hc:d4*/I18n.translatedValue_value()/*hc*/)/*hc:d2_4*//*hc*/
			, new RelationFDesc(TranslatedValueConsts.k_lang, /*hc:d5*/I18n.translatedValue_lang()/*hc*/
										, LangConsts.descriptorName)/*hc:d2_5*//*hc*/
					.mandatory()
			, new RelationFDesc(TranslatedValueConsts.k_row, /*hc:d6*/I18n.translatedValue_row()/*hc*/
										, ModuleRowConsts.descriptorName)/*hc:d2_6*//*hc*/
					.mandatory()
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(TranslatedValueConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(TranslatedValueConsts.k_id, new ColRDesc(/*hc:tdr1_1*/true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(TranslatedValueConsts.k_lastModTime, new ColRDesc(/*hc:tdr1_2*/true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(TranslatedValueConsts.k_lastModUser, new ColRDesc(/*hc:tdr1_3*/true/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(TranslatedValueConsts.k_value, new ColRDesc(/*hc:tdr1_4*/true/*hc*/)/*hc:tdr2_4*//*hc*/)
			.addChild(TranslatedValueConsts.k_lang, new ColRDesc(/*hc:tdr1_5*/true/*hc*/)/*hc:tdr2_5*//*hc*/)
			.addChild(TranslatedValueConsts.k_row, new ColRDesc(/*hc:tdr1_6*/true/*hc*/)/*hc:tdr2_6*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(TranslatedValueConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(TranslatedValueConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(TranslatedValueConsts.k_lastModTime, new WidgetRDesc(/*hc:f2*/FWTypes.NUMBERTEXTBOX/*hc*/))
			.addChild(TranslatedValueConsts.k_lastModUser, new WidgetRDesc(/*hc:f3*/FWTypes.LISTBOX/*hc*/))
			.addChild(TranslatedValueConsts.k_value, new WidgetRDesc(/*hc:f4*/FWTypes.TEXTBOX/*hc*/))
			.addChild(TranslatedValueConsts.k_lang, new WidgetRDesc(/*hc:f5*/FWTypes.LISTBOX/*hc*/))
			.addChild(TranslatedValueConsts.k_row, new WidgetRDesc(/*hc:f6*/FWTypes.LISTBOX/*hc*/))
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