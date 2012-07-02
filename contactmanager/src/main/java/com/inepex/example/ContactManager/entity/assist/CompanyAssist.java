package com.inepex.example.ContactManager.entity.assist;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.CompanyConsts;
import com.inepex.example.ContactManager.entity.kvo.ContactConsts;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.ListFDesc;
import com.inepex.ineom.shared.descriptor.LongFDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.descriptor.StringFDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore.Marker;

public class CompanyAssist extends Assist {
	
	public static final String roFRD = "roFrd";
	
	public CompanyAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
		descStore.addNamedTypedDesc(Marker.registered, CompanyConsts.descriptorName, roFRD, getROFormRDesc());
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(CompanyConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(CompanyConsts.descriptorName
			, new LongFDesc(CompanyConsts.k_id, /*hc:d1*/CMI18n.company_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(CompanyConsts.k_name, /*hc:d2*/CMI18n.company_name()/*hc*/)/*hc:d2_2*//*hc*/
					.mandatory()
			, new StringFDesc(CompanyConsts.k_phone, /*hc:d3*/CMI18n.company_phone()/*hc*/)/*hc:d2_3*//*hc*/
					.mandatory()
			, new StringFDesc(CompanyConsts.k_email, /*hc:d4*/CMI18n.company_email()/*hc*/)/*hc:d2_4*//*hc*/
					.email()
					.mandatory()
			, new RelationFDesc(CompanyConsts.k_extData, CMI18n.company_extdata(), IFConsts.customDescriptorName)
			, new StringFDesc(CompanyConsts.k_webPage, /*hc:d5*/CMI18n.company_webPage()/*hc*/)/*hc:d2_5*//*hc*/
					.mandatory()
			, new ListFDesc(CompanyConsts.k_contacts, /*hc:d6*/CMI18n.company_contacts()/*hc*/,ContactConsts.descriptorName)/*hc:d2_6*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(CompanyConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(CompanyConsts.k_name, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(CompanyConsts.k_phone, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(CompanyConsts.k_email, new ColRDesc(/*hc:tdr1_4*/100, true/*hc*/)/*hc:tdr2_4*//*hc*/)
			.addChild(CompanyConsts.k_webPage, new ColRDesc(/*hc:tdr1_5*/100, true/*hc*/)/*hc:tdr2_5*//*hc*/)
			.addChild(CompanyConsts.k_contacts, new ColRDesc(/*hc:tdr1_6*/100, false/*hc*/)/*hc:tdr2_6*//*hc*/)				
			;
		return tableRDesc;
	}
	
	public FormRDesc getROFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(CompanyConsts.descriptorName);
			
		formRDesc.getRootNode()
				.addChild(CompanyConsts.k_phone, new WidgetRDesc(/*hc:f3*/FWTypes.LABEL/*hc*/))
				.addChild(CompanyConsts.k_email, new WidgetRDesc(/*hc:f4*/FWTypes.LABEL/*hc*/))
				.addChild(CompanyConsts.k_webPage, new WidgetRDesc(/*hc:f5*/FWTypes.LABEL/*hc*/))
				.addChild(CompanyConsts.k_contacts, new WidgetRDesc(/*hc:f6*/FWTypes.LABEL/*hc*/))
				.addChild(CompanyConsts.k_extData, new WidgetRDesc(FWTypes.CUSTOMKVOREADONLY, "showHeader:false"))
			;
		return formRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(CompanyConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(CompanyConsts.k_name, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(CompanyConsts.k_phone, new WidgetRDesc(/*hc:f3*/FWTypes.PHONE/*hc*/))
			.addChild(CompanyConsts.k_email, new WidgetRDesc(/*hc:f4*/FWTypes.TEXTBOX/*hc*/))
			.addChild(CompanyConsts.k_webPage, new WidgetRDesc(/*hc:f5*/FWTypes.TEXTBOX/*hc*/))
			.addChild(CompanyConsts.k_extData, new WidgetRDesc(FWTypes.CUSTOMKVO))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(CompanyConsts.searchDescriptor
			, new LongFDesc(CompanyConsts.s_id, /*hc:ds1*/"Id"/*hc*/)
			, new StringFDesc(CompanyConsts.s_name, /*hc:ds2*/"Name"/*hc*/)
			, new StringFDesc(CompanyConsts.s_phone, /*hc:ds3*/"Phone"/*hc*/)
			, new StringFDesc(CompanyConsts.s_email, /*hc:ds4*/"Email"/*hc*/)
			, new StringFDesc(CompanyConsts.s_webPage, /*hc:ds5*/"WebPage"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(CompanyConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(CompanyConsts.s_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(CompanyConsts.s_name, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(CompanyConsts.s_phone, new WidgetRDesc(/*hc:fs3*/FWTypes.TEXTBOX/*hc*/))
			.addChild(CompanyConsts.s_email, new WidgetRDesc(/*hc:fs4*/FWTypes.TEXTBOX/*hc*/))
			.addChild(CompanyConsts.s_webPage, new WidgetRDesc(/*hc:fs5*/FWTypes.TEXTBOX/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
