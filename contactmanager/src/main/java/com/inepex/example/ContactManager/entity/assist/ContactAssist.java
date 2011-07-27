package com.inepex.example.ContactManager.entity.assist;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.CompanyKVO;
import com.inepex.example.ContactManager.entity.kvo.ContactKVO;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressKVO;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberKVO;
import com.inepex.example.ContactManager.entity.kvo.search.ContactSearchKVO;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.ListFDesc;
import com.inepex.ineom.shared.descriptor.LongFDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.descriptor.StringFDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;

public class ContactAssist extends Assist {
	
	public static final String roFRD = "roFRD";
	
	public ContactAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
		descStore.addNamedTypedDesc(ContactKVO.descriptorName, roFRD, getROFormRDesc());
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(ContactKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(ContactKVO.descriptorName
			, new LongFDesc(ContactKVO.k_id, /*hc:d1*/CMI18n.contact_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(ContactKVO.k_name, /*hc:d2*/CMI18n.contact_name()/*hc*/)/*hc:d2_2*//*hc*/
					.mandatory()
			, new ListFDesc(ContactKVO.k_phone, /*hc:d3*/CMI18n.contact_phone()/*hc*/,PhoneNumberKVO.descriptorName)/*hc:d2_3*//*hc*/
			, new ListFDesc(ContactKVO.k_email, /*hc:d4*/CMI18n.contact_email()/*hc*/,EmailAddressKVO.descriptorName)/*hc:d2_4*//*hc*/
			, new RelationFDesc(ContactKVO.k_company, /*hc:d5*/CMI18n.contact_company()/*hc*/
										, CompanyKVO.descriptorName)/*hc:d2_5*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(ContactKVO.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(ContactKVO.k_name, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(ContactKVO.k_phone, new ColRDesc(/*hc:tdr1_3*/100/*hc*/)/*hc:tdr2_3*//*hc*/)				
			.addChild(ContactKVO.k_email, new ColRDesc(/*hc:tdr1_4*/100/*hc*/)/*hc:tdr2_4*//*hc*/)				
			;
		return tableRDesc;
	}
	
	public FormRDesc getROFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ContactKVO.descriptorName);
			
		formRDesc.getRootNode()
			.addChild(ContactKVO.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(ContactKVO.k_name, new WidgetRDesc(/*hc:f2*/FWTypes.LABEL/*hc*/))
			.addChild(ContactKVO.k_phone, new WidgetRDesc(/*hc:f3*/FWTypes.LABEL/*hc*/))
			.addChild(ContactKVO.k_email, new WidgetRDesc(/*hc:f4*/FWTypes.LABEL/*hc*/))
			;
		
		return formRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ContactKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(ContactKVO.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(ContactKVO.k_name, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactKVO.k_phone, new WidgetRDesc(/*hc:f3*/FWTypes.RELATIONLIST/*hc*/))
			.addChild(ContactKVO.k_email, new WidgetRDesc(/*hc:f4*/FWTypes.RELATIONLIST/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(ContactSearchKVO.descriptorName
			, new LongFDesc(ContactSearchKVO.k_id, /*hc:ds1*/"Id"/*hc*/)
			, new StringFDesc(ContactSearchKVO.k_name, /*hc:ds2*/"Name"/*hc*/)
			, new RelationFDesc(ContactSearchKVO.k_company, /*hc:ds5*/"Company"/*hc*/
										, CompanyKVO.descriptorName)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(ContactSearchKVO.descriptorName);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(ContactSearchKVO.k_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(ContactSearchKVO.k_name, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactSearchKVO.k_company, new WidgetRDesc(/*hc:fs5*/FWTypes.LISTBOX/*hc*/))

			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
