package com.inepex.example.ineForm.entity.assist;
import com.inepex.example.ineForm.entity.kvo.ContactTypeConsts;
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

public class ContactTypeAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"contactType_id";"";"Id";"Id"
//"contactType_typeName";"";"TypeName";"TypeName"
//"contactType_description";"";"Description";"Description"
//
		public final static String contactType_id = /*hc:i18n_1*/"Id"/*hc*/;
		public final static String contactType_typeName = /*hc:i18n_2*/"TypeName"/*hc*/;
		public final static String contactType_description = /*hc:i18n_3*/"Description"/*hc*/;
	}
	
	public ContactTypeAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(ContactTypeConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(ContactTypeConsts.descriptorName
			, new LongFDesc(ContactTypeConsts.k_id, /*hc:d1*/"Id"/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(ContactTypeConsts.k_typeName, /*hc:d2*/"TypeName"/*hc*/)/*hc:d2_2*//*hc*/
			, new StringFDesc(ContactTypeConsts.k_description, /*hc:d3*/"Description"/*hc*/)/*hc:d2_3*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(ContactTypeConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(ContactTypeConsts.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(ContactTypeConsts.k_typeName, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(ContactTypeConsts.k_description, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ContactTypeConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(ContactTypeConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(ContactTypeConsts.k_typeName, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactTypeConsts.k_description, new WidgetRDesc(/*hc:f3*/FWTypes.TEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(ContactTypeConsts.searchDescriptor
			, new StringFDesc(ContactTypeConsts.s_typeName, /*hc:ds2*/"TypeName"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(ContactTypeConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(ContactTypeConsts.s_typeName, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
