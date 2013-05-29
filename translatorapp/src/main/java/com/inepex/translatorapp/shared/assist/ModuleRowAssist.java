package com.inepex.translatorapp.shared.assist;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.descriptor.fdesc.ListFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;
import com.inepex.translatorapp.shared.kvo.ModuleRowConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;

public class ModuleRowAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"moduleRow_id";"";"Id";"Id"
//"moduleRow_key";"";"Key";"Key"
//"moduleRow_description";"";"Description";"Description"
//"moduleRow_module";"";"Module";"Module"
//"moduleRow_values";"";"Values";"Values"
//
		public static String moduleRow_id() { return /*hc:i18n_1*/"Id"/*hc*/;}
		public static String moduleRow_key() { return /*hc:i18n_2*/"Key"/*hc*/;}
		public static String moduleRow_description() { return /*hc:i18n_3*/"Description"/*hc*/;}
		public static String moduleRow_module() { return /*hc:i18n_4*/"Module"/*hc*/;}
		public static String moduleRow_values() { return /*hc:i18n_5*/"Values"/*hc*/;}
	}
	
	public ModuleRowAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(ModuleRowConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(ModuleRowConsts.descriptorName
			, new LongFDesc(ModuleRowConsts.k_id, /*hc:d1*/I18n.moduleRow_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(ModuleRowConsts.k_key, /*hc:d2*/I18n.moduleRow_key()/*hc*/)/*hc:d2_2*//*hc*/
					.mandatory()
			, new StringFDesc(ModuleRowConsts.k_description, /*hc:d3*/I18n.moduleRow_description()/*hc*/)/*hc:d2_3*//*hc*/
			, new RelationFDesc(ModuleRowConsts.k_module, /*hc:d4*/I18n.moduleRow_module()/*hc*/
										, ModuleConsts.descriptorName)/*hc:d2_4*//*hc*/
					.mandatory()
			, new ListFDesc(ModuleRowConsts.k_values, /*hc:d5*/I18n.moduleRow_values()/*hc*/,TranslatedValueConsts.descriptorName)/*hc:d2_5*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(ModuleRowConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(ModuleRowConsts.k_id, new ColRDesc(/*hc:tdr1_1*/true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(ModuleRowConsts.k_key, new ColRDesc(/*hc:tdr1_2*/true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(ModuleRowConsts.k_description, new ColRDesc(/*hc:tdr1_3*/true/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(ModuleRowConsts.k_module, new ColRDesc(/*hc:tdr1_4*/true/*hc*/)/*hc:tdr2_4*//*hc*/)
			.addChild(ModuleRowConsts.k_values, new ColRDesc(/*hc:tdr1_5*//*hc*/)/*hc:tdr2_5*//*hc*/)				
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ModuleRowConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(ModuleRowConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(ModuleRowConsts.k_key, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ModuleRowConsts.k_description, new WidgetRDesc(/*hc:f3*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ModuleRowConsts.k_module, new WidgetRDesc(/*hc:f4*/FWTypes.LISTBOX/*hc*/))
			.addChild(ModuleRowConsts.k_values, new WidgetRDesc(/*hc:f5*/FWTypes.RELATIONLIST/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(ModuleRowConsts.searchDescriptor
			, new LongFDesc(ModuleRowConsts.s_id, /*hc:ds1*/"Id"/*hc*/)
			, new StringFDesc(ModuleRowConsts.s_key, /*hc:ds2*/"Key"/*hc*/)
			, new RelationFDesc(ModuleRowConsts.s_module, /*hc:ds4*/"Module"/*hc*/
										, ModuleConsts.searchDescriptor)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(ModuleRowConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(ModuleRowConsts.s_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(ModuleRowConsts.s_key, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ModuleRowConsts.s_module, new WidgetRDesc(/*hc:fs4*/FWTypes.LISTBOX/*hc*/))

			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}