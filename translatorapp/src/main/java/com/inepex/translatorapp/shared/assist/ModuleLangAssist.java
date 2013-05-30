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
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.kvo.LangConsts;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;
import com.inepex.translatorapp.shared.kvo.ModuleLangConsts;

public class ModuleLangAssist extends Assist {
	
	public ModuleLangAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(ModuleLangConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(ModuleLangConsts.descriptorName
			, new LongFDesc(ModuleLangConsts.k_id, /*hc:d1*/translatorappI18n.moduleLang_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new RelationFDesc(ModuleLangConsts.k_lang, /*hc:d2*/translatorappI18n.moduleLang_lang()/*hc*/
										, LangConsts.descriptorName)/*hc:d2_2*//*hc*/
					.mandatory()
			, new RelationFDesc(ModuleLangConsts.k_module, /*hc:d4*/translatorappI18n.moduleLang_module()/*hc*/
										, ModuleConsts.descriptorName)/*hc:d2_4*//*hc*/
					.mandatory()
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(ModuleLangConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(ModuleLangConsts.k_id, new ColRDesc(/*hc:tdr1_1*/true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(ModuleLangConsts.k_lang, new ColRDesc(/*hc:tdr1_2*/true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(ModuleLangConsts.k_module, new ColRDesc(/*hc:tdr1_4*/true/*hc*/)/*hc:tdr2_4*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ModuleLangConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(ModuleLangConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(ModuleLangConsts.k_lang, new WidgetRDesc(/*hc:f2*/FWTypes.LISTBOX/*hc*/))
			.addChild(ModuleLangConsts.k_module, new WidgetRDesc(/*hc:f4*/FWTypes.LISTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(ModuleLangConsts.searchDescriptor
			, new LongFDesc(ModuleLangConsts.s_id, /*hc:ds1*/"Id"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(ModuleLangConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(ModuleLangConsts.s_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}