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
import com.inepex.translatorapp.shared.kvo.UserConsts;
import com.inepex.translatorapp.shared.kvo.UserLangConsts;

public class UserLangAssist extends Assist {
	
	public UserLangAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(UserLangConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(UserLangConsts.descriptorName
			, new LongFDesc(UserLangConsts.k_id, /*hc:d1*/translatorappI18n.userLang_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new RelationFDesc(UserLangConsts.k_lang, /*hc:d2*/translatorappI18n.userLang_lang()/*hc*/
										, LangConsts.descriptorName)/*hc:d2_2*//*hc*/
					.mandatory()
			, new RelationFDesc(UserLangConsts.k_user, /*hc:d3*/translatorappI18n.userLang_user()/*hc*/
										, UserConsts.descriptorName)/*hc:d2_3*//*hc*/
					.mandatory()
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(UserLangConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(UserLangConsts.k_id, new ColRDesc(/*hc:tdr1_1*/true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(UserLangConsts.k_lang, new ColRDesc(/*hc:tdr1_2*/true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(UserLangConsts.k_user, new ColRDesc(/*hc:tdr1_3*/true/*hc*/)/*hc:tdr2_3*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(UserLangConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(UserLangConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(UserLangConsts.k_lang, new WidgetRDesc(/*hc:f2*/FWTypes.LISTBOX/*hc*/))
			.addChild(UserLangConsts.k_user, new WidgetRDesc(/*hc:f3*/FWTypes.LISTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(UserLangConsts.searchDescriptor
			, new LongFDesc(UserLangConsts.s_id, /*hc:ds1*/"Id"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(UserLangConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(UserLangConsts.s_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}