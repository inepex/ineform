package com.inepex.example.ContactManager.entity.assist;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.CompanyKVO;
import com.inepex.example.ContactManager.entity.kvo.ContactKVO;
import com.inepex.example.ContactManager.entity.kvo.search.CompanySearchKVO;
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
import com.inepex.ineom.shared.descriptor.StringFDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;

public class CompanyAssist extends Assist {
	
	public static final String roFRD = "roFrd";
	
	public CompanyAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
		descStore.addNamedTypedDesc(CompanyKVO.descriptorName, roFRD, getROFormRDesc());
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(CompanyKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(CompanyKVO.descriptorName
			, new LongFDesc(CompanyKVO.k_id, /*hc:d1*/CMI18n.company_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(CompanyKVO.k_name, /*hc:d2*/CMI18n.company_name()/*hc*/)/*hc:d2_2*//*hc*/
					.mandatory()
			, new StringFDesc(CompanyKVO.k_phone, /*hc:d3*/CMI18n.company_phone()/*hc*/)/*hc:d2_3*//*hc*/
					.mandatory()
			, new StringFDesc(CompanyKVO.k_email, /*hc:d4*/CMI18n.company_email()/*hc*/)/*hc:d2_4*//*hc*/
					.email()
					.mandatory()
			, new StringFDesc(CompanyKVO.k_webPage, /*hc:d5*/CMI18n.company_webPage()/*hc*/)/*hc:d2_5*//*hc*/
					.mandatory()
			, new ListFDesc(CompanyKVO.k_contacts, /*hc:d6*/CMI18n.company_contacts()/*hc*/,ContactKVO.descriptorName)/*hc:d2_6*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(CompanyKVO.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(CompanyKVO.k_name, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(CompanyKVO.k_phone, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(CompanyKVO.k_email, new ColRDesc(/*hc:tdr1_4*/100, true/*hc*/)/*hc:tdr2_4*//*hc*/)
			.addChild(CompanyKVO.k_webPage, new ColRDesc(/*hc:tdr1_5*/100, true/*hc*/)/*hc:tdr2_5*//*hc*/)
			.addChild(CompanyKVO.k_contacts, new ColRDesc(/*hc:tdr1_6*/100, false/*hc*/)/*hc:tdr2_6*//*hc*/)				
			;
		return tableRDesc;
	}
	
	public FormRDesc getROFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(CompanyKVO.descriptorName);
			
		formRDesc.getRootNode()
				.addChild(CompanyKVO.k_phone, new WidgetRDesc(/*hc:f3*/FWTypes.LABEL/*hc*/))
				.addChild(CompanyKVO.k_email, new WidgetRDesc(/*hc:f4*/FWTypes.LABEL/*hc*/))
				.addChild(CompanyKVO.k_webPage, new WidgetRDesc(/*hc:f5*/FWTypes.LABEL/*hc*/))
				.addChild(CompanyKVO.k_contacts, new WidgetRDesc(/*hc:f6*/FWTypes.LABEL/*hc*/))
			;
		return formRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(CompanyKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(CompanyKVO.k_name, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(CompanyKVO.k_phone, new WidgetRDesc(/*hc:f3*/FWTypes.PHONE/*hc*/))
			.addChild(CompanyKVO.k_email, new WidgetRDesc(/*hc:f4*/FWTypes.TEXTBOX/*hc*/))
			.addChild(CompanyKVO.k_webPage, new WidgetRDesc(/*hc:f5*/FWTypes.TEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(CompanySearchKVO.descriptorName
			, new LongFDesc(CompanySearchKVO.k_id, /*hc:ds1*/"Id"/*hc*/)
			, new StringFDesc(CompanySearchKVO.k_name, /*hc:ds2*/"Name"/*hc*/)
			, new StringFDesc(CompanySearchKVO.k_phone, /*hc:ds3*/"Phone"/*hc*/)
			, new StringFDesc(CompanySearchKVO.k_email, /*hc:ds4*/"Email"/*hc*/)
			, new StringFDesc(CompanySearchKVO.k_webPage, /*hc:ds5*/"WebPage"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(CompanySearchKVO.descriptorName);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(CompanySearchKVO.k_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(CompanySearchKVO.k_name, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(CompanySearchKVO.k_phone, new WidgetRDesc(/*hc:fs3*/FWTypes.TEXTBOX/*hc*/))
			.addChild(CompanySearchKVO.k_email, new WidgetRDesc(/*hc:fs4*/FWTypes.TEXTBOX/*hc*/))
			.addChild(CompanySearchKVO.k_webPage, new WidgetRDesc(/*hc:fs5*/FWTypes.TEXTBOX/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
