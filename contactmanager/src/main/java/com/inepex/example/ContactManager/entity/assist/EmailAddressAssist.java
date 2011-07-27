package com.inepex.example.ContactManager.entity.assist;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressKVO;
import com.inepex.example.ContactManager.entity.kvo.search.EmailAddressSearchKVO;
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

public class EmailAddressAssist extends Assist {
	
	public EmailAddressAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(EmailAddressKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(EmailAddressKVO.descriptorName
			, new LongFDesc(EmailAddressKVO.k_id, /*hc:d1*/CMI18n.emailAddress_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(EmailAddressKVO.k_email, /*hc:d2*/CMI18n.emailAddress_email()/*hc*/)/*hc:d2_2*//*hc*/
					.email()
					.mandatory()
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(EmailAddressKVO.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(EmailAddressKVO.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(EmailAddressKVO.k_email, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(EmailAddressKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(EmailAddressKVO.k_email, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(EmailAddressSearchKVO.descriptorName
			, new LongFDesc(EmailAddressSearchKVO.k_id, /*hc:ds1*/"Id"/*hc*/)
			, new StringFDesc(EmailAddressSearchKVO.k_email, /*hc:ds2*/"Email"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(EmailAddressSearchKVO.descriptorName);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(EmailAddressSearchKVO.k_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(EmailAddressSearchKVO.k_email, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
