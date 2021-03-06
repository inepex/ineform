package com.inepex.example.ineForm.entity.assist;
import com.inepex.example.ineForm.entity.kvo.ContactAddresDetailConsts;
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

public class ContactAddresDetailAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"contactAddresDetail_id";"";"Id";"Id"
//"contactAddresDetail_city";"";"City";"City"
//"contactAddresDetail_country";"";"Country";"Country"
//
		public final static String contactAddresDetail_id = /*hc:i18n_1*/"Id"/*hc*/;
		public final static String contactAddresDetail_city = /*hc:i18n_2*/"City"/*hc*/;
		public final static String contactAddresDetail_country = /*hc:i18n_3*/"Country"/*hc*/;
	}
	
	public ContactAddresDetailAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(ContactAddresDetailConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(ContactAddresDetailConsts.descriptorName
			, new LongFDesc(ContactAddresDetailConsts.k_id, /*hc:d1*/"Id"/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(ContactAddresDetailConsts.k_city, /*hc:d2*/"City"/*hc*/)/*hc:d2_2*//*hc*/
			, new StringFDesc(ContactAddresDetailConsts.k_country, /*hc:d3*/"Country"/*hc*/)/*hc:d2_3*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(ContactAddresDetailConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(ContactAddresDetailConsts.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(ContactAddresDetailConsts.k_city, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(ContactAddresDetailConsts.k_country, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ContactAddresDetailConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(ContactAddresDetailConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(ContactAddresDetailConsts.k_city, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactAddresDetailConsts.k_country, new WidgetRDesc(/*hc:f3*/FWTypes.TEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(ContactAddresDetailConsts.searchDescriptor
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(ContactAddresDetailConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		key = ContactAddresDetailConsts.k_city;
		
		return key;
	}
}
