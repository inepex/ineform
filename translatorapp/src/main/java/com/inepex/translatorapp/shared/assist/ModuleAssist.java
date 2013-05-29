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
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;
import com.inepex.translatorapp.shared.kvo.ModuleLangConsts;
import com.inepex.translatorapp.shared.kvo.ModuleRowConsts;

public class ModuleAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"module_id";"";"Id";"Id"
//"module_name";"";"Name";"Name"
//"module_rows";"";"Rows";"Rows"
//"module_langs";"";"Langs";"Langs"
//
		public static String module_id() { return /*hc:i18n_1*/"Id"/*hc*/;}
		public static String module_name() { return /*hc:i18n_2*/"Name"/*hc*/;}
		public static String module_rows() { return /*hc:i18n_3*/"Rows"/*hc*/;}
		public static String module_langs() { return /*hc:i18n_4*/"Langs"/*hc*/;}
	}
	
	public ModuleAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(ModuleConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(ModuleConsts.descriptorName
			, new LongFDesc(ModuleConsts.k_id, /*hc:d1*/I18n.module_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(ModuleConsts.k_name, /*hc:d2*/I18n.module_name()/*hc*/)/*hc:d2_2*//*hc*/
					.mandatory()
			, new ListFDesc(ModuleConsts.k_rows, /*hc:d3*/I18n.module_rows()/*hc*/,ModuleRowConsts.descriptorName)/*hc:d2_3*//*hc*/
			, new ListFDesc(ModuleConsts.k_langs, /*hc:d4*/I18n.module_langs()/*hc*/,ModuleLangConsts.descriptorName)/*hc:d2_4*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(ModuleConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(ModuleConsts.k_id, new ColRDesc(/*hc:tdr1_1*/true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(ModuleConsts.k_name, new ColRDesc(/*hc:tdr1_2*/true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(ModuleConsts.k_rows, new ColRDesc(/*hc:tdr1_3*//*hc*/)/*hc:tdr2_3*//*hc*/)				
			.addChild(ModuleConsts.k_langs, new ColRDesc(/*hc:tdr1_4*//*hc*/)/*hc:tdr2_4*//*hc*/)				
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ModuleConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(ModuleConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(ModuleConsts.k_name, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ModuleConsts.k_rows, new WidgetRDesc(/*hc:f3*/FWTypes.RELATIONLIST/*hc*/))
			.addChild(ModuleConsts.k_langs, new WidgetRDesc(/*hc:f4*/FWTypes.RELATIONLIST/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(ModuleConsts.searchDescriptor
			, new LongFDesc(ModuleConsts.s_id, /*hc:ds1*/"Id"/*hc*/)
			, new StringFDesc(ModuleConsts.s_name, /*hc:ds2*/"Name"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(ModuleConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(ModuleConsts.s_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(ModuleConsts.s_name, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}