package com.inepex.example.ineForm.dto.assist;
import com.inepex.example.ineForm.dto.kvo.ContactDtoKVO;
import com.inepex.example.ineForm.dto.kvo.search.ContactDtoSearchKVO;
import com.inepex.example.ineForm.entity.kvo.ContactAddresDetailKVO;
import com.inepex.example.ineForm.enums.SpecialContactType;
import com.inepex.ineForm.client.form.widgets.EnumListFW;
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

public class ContactDtoAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"contactDto_name";"";"Name";"Name"
//"contactDto_relatedRandomValue";"";"RelatedRandomValue";"RelatedRandomValue"
//"contactDto_verySecretParameter";"";"VerySecretParameter";"VerySecretParameter"
//"contactDto_specCont";"";"SpecCont";"SpecCont"
//"contactDto_contactDetails";"";"ContactDetails";"ContactDetails"
//
		public final static String contactDto_name = /*hc:i18n_2*/"Name"/*hc*/;
		public final static String contactDto_relatedRandomValue = /*hc:i18n_3*/"RelatedRandomValue"/*hc*/;
		public final static String contactDto_verySecretParameter = /*hc:i18n_4*/"VerySecretParameter"/*hc*/;
		public final static String contactDto_specCont = /*hc:i18n_5*/"SpecCont"/*hc*/;
		public final static String contactDto_contactDetails = /*hc:i18n_6*/"ContactDetails"/*hc*/;
	}
	
	public ContactDtoAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	public void registerExtraDescriptors(DescriptorStore descStore) {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(ContactDtoKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
			
		return new ObjectDesc(ContactDtoKVO.descriptorName
			, new StringFDesc(ContactDtoKVO.k_name, /*hc:d2*/"Name"/*hc*/)/*hc:d2_2*//*hc*/
			, new LongFDesc(ContactDtoKVO.k_relatedRandomValue, /*hc:d3*/"RelatedRandomValue"/*hc*/)/*hc:d2_3*//*hc*/
			, new StringFDesc(ContactDtoKVO.k_verySecretParameter, /*hc:d4*/"VerySecretParameter"/*hc*/)/*hc:d2_4*//*hc*/
			, new LongFDesc(ContactDtoKVO.k_specCont, /*hc:d5*/"SpecCont"/*hc*/)/*hc:d2_5*//*hc*/
			, new RelationFDesc(ContactDtoKVO.k_contactDetails, /*hc:d6*/"ContactDetails"/*hc*/
										, ContactAddresDetailKVO.descriptorName)/*hc:d2_6*//*hc*/
	
	
		);
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(ContactDtoKVO.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(ContactDtoKVO.k_name, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(ContactDtoKVO.k_relatedRandomValue, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(ContactDtoKVO.k_verySecretParameter, new ColRDesc(/*hc:tdr1_4*/100, true/*hc*/)/*hc:tdr2_4*//*hc*/)
			.addChild(ContactDtoKVO.k_specCont, new ColRDesc(/*hc:tdr1_5*/100, true/*hc*/)/*hc:tdr2_5*//*hc*/.prop(EnumListFW.enumValues + ":" + SpecialContactType.getValuesAsString()))
			.addChild(ContactDtoKVO.k_contactDetails, new ColRDesc(/*hc:tdr1_6*/100, true/*hc*/)/*hc:tdr2_6*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ContactDtoKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(ContactDtoKVO.k_name, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactDtoKVO.k_relatedRandomValue, new WidgetRDesc(/*hc:f3*/FWTypes.NUMBERTEXTBOX/*hc*/))
			.addChild(ContactDtoKVO.k_verySecretParameter, new WidgetRDesc(/*hc:f4*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactDtoKVO.k_specCont, new WidgetRDesc(/*hc:f5*/FWTypes.ENUMLISTBOX/*hc*/, EnumListFW.enumValues + ":" + SpecialContactType.getValuesAsString()))
			.addChild(ContactDtoKVO.k_contactDetails, new WidgetRDesc(/*hc:f6*/FWTypes.LISTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(ContactDtoSearchKVO.descriptorName
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(ContactDtoSearchKVO.descriptorName);
			
		searchFormRDesc.getRootNode().dummy()
			;
		return searchFormRDesc;
	}

	public String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
