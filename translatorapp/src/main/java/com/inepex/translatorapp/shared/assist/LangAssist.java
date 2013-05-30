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
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.kvo.LangConsts;

public class LangAssist extends Assist {
	
	public LangAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(LangConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(LangConsts.descriptorName
			, new LongFDesc(LangConsts.k_id, /*hc:d1*/translatorappI18n.lang_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(LangConsts.k_isoName, /*hc:d2*/translatorappI18n.lang_isoName()/*hc*/)/*hc:d2_2*//*hc*/
					.mandatory()
			, new StringFDesc(LangConsts.k_countryCode, /*hc:d2*/""/*hc*/)/*hc:d2_2*//*hc*/
				.mandatory()
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(LangConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(LangConsts.k_id, new ColRDesc(/*hc:tdr1_1*/true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(LangConsts.k_isoName, new ColRDesc(/*hc:tdr1_2*/true/*hc*/)/*hc:tdr2_2*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(LangConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(LangConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(LangConsts.k_isoName, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(LangConsts.searchDescriptor
			, new LongFDesc(LangConsts.s_id, /*hc:ds1*/"Id"/*hc*/)
			, new StringFDesc(LangConsts.s_isoName, /*hc:ds2*/"IsoName"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(LangConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(LangConsts.s_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(LangConsts.s_isoName, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}