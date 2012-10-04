package com.inepex.example.ContactManager.entity.assist;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressConsts;
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

public class EmailAddressAssist extends Assist {
	
	public EmailAddressAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(EmailAddressConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(EmailAddressConsts.descriptorName
			, new LongFDesc(EmailAddressConsts.k_id, /*hc:d1*/CMI18n.emailAddress_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(EmailAddressConsts.k_email, /*hc:d2*/CMI18n.emailAddress_email()/*hc*/)/*hc:d2_2*//*hc*/
					.email()
					.mandatory()
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(EmailAddressConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(EmailAddressConsts.k_id, new ColRDesc(/*hc:tdr1_1*/true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(EmailAddressConsts.k_email, new ColRDesc(/*hc:tdr1_2*/true/*hc*/)/*hc:tdr2_2*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(EmailAddressConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(EmailAddressConsts.k_email, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(EmailAddressConsts.searchDescriptor
			, new LongFDesc(EmailAddressConsts.s_id, /*hc:ds1*/"Id"/*hc*/)
			, new StringFDesc(EmailAddressConsts.s_email, /*hc:ds2*/"Email"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(EmailAddressConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(EmailAddressConsts.s_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(EmailAddressConsts.s_email, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
