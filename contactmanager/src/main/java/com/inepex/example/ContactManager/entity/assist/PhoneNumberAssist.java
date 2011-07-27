package com.inepex.example.ContactManager.entity.assist;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberKVO;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberTypeKVO;
import com.inepex.example.ContactManager.entity.kvo.search.PhoneNumberSearchKVO;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.LongFDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.descriptor.StringFDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;

public class PhoneNumberAssist extends Assist {
	
	public PhoneNumberAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(PhoneNumberKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(PhoneNumberKVO.descriptorName
			, new LongFDesc(PhoneNumberKVO.k_id, /*hc:d1*/CMI18n.phoneNumber_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(PhoneNumberKVO.k_number, /*hc:d2*/CMI18n.phoneNumber_number()/*hc*/)/*hc:d2_2*//*hc*/
					.mandatory()
			, new RelationFDesc(PhoneNumberKVO.k_type, /*hc:d3*/CMI18n.phoneNumber_type()/*hc*/
										, PhoneNumberTypeKVO.descriptorName)/*hc:d2_3*//*hc*/
					.mandatory()
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(PhoneNumberKVO.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(PhoneNumberKVO.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(PhoneNumberKVO.k_number, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(PhoneNumberKVO.k_type, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(PhoneNumberKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(PhoneNumberKVO.k_number, new WidgetRDesc(/*hc:f2*/FWTypes.PHONE/*hc*/))
			.addChild(PhoneNumberKVO.k_type, new WidgetRDesc(/*hc:f3*/FWTypes.LISTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(PhoneNumberSearchKVO.descriptorName
			, new LongFDesc(PhoneNumberSearchKVO.k_id, /*hc:ds1*/"Id"/*hc*/)
			, new StringFDesc(PhoneNumberSearchKVO.k_number, /*hc:ds2*/"Number"/*hc*/)
			, new RelationFDesc(PhoneNumberSearchKVO.k_type, /*hc:ds3*/"Type"/*hc*/
										, PhoneNumberTypeKVO.descriptorName)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(PhoneNumberSearchKVO.descriptorName);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(PhoneNumberSearchKVO.k_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(PhoneNumberSearchKVO.k_number, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(PhoneNumberSearchKVO.k_type, new WidgetRDesc(/*hc:fs3*/FWTypes.LISTBOX/*hc*/))

			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
