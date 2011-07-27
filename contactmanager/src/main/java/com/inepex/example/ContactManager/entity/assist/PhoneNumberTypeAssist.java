package com.inepex.example.ContactManager.entity.assist;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberTypeKVO;
import com.inepex.example.ContactManager.entity.kvo.search.PhoneNumberTypeSearchKVO;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.LongFDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.StringFDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;

public class PhoneNumberTypeAssist extends Assist {
	
	public PhoneNumberTypeAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(PhoneNumberTypeKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(PhoneNumberTypeKVO.descriptorName
			, new LongFDesc(PhoneNumberTypeKVO.k_id, /*hc:d1*/CMI18n.phoneNumberType_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(PhoneNumberTypeKVO.k_name, /*hc:d2*/CMI18n.phoneNumberType_name()/*hc*/)/*hc:d2_2*//*hc*/
					.mandatory()
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(PhoneNumberTypeKVO.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(PhoneNumberTypeKVO.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(PhoneNumberTypeKVO.k_name, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(PhoneNumberTypeKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(PhoneNumberTypeKVO.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(PhoneNumberTypeKVO.k_name, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(PhoneNumberTypeSearchKVO.descriptorName
			, new LongFDesc(PhoneNumberTypeSearchKVO.k_id, /*hc:ds1*/"Id"/*hc*/)
			, new StringFDesc(PhoneNumberTypeSearchKVO.k_name, /*hc:ds2*/"Name"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(PhoneNumberTypeSearchKVO.descriptorName);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(PhoneNumberTypeSearchKVO.k_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(PhoneNumberTypeSearchKVO.k_name, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
