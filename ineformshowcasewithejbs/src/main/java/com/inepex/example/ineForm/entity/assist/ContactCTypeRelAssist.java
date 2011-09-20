package com.inepex.example.ineForm.entity.assist;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelConsts;
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
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;

public class ContactCTypeRelAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"contactCTypeRel_id";"";"Id";"Id"
//"contactCTypeRel_contactType";"";"ContactType";"ContactType"
//"contactCTypeRel_orderNum";"";"OrderNum";"OrderNum"
//
		public final static String contactCTypeRel_id = /*hc:i18n_1*/"Id"/*hc*/;
		public final static String contactCTypeRel_contactType = /*hc:i18n_3*/"ContactType"/*hc*/;
		public final static String contactCTypeRel_orderNum = /*hc:i18n_4*/"OrderNum"/*hc*/;
	}
	
	public ContactCTypeRelAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(ContactCTypeRelConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(ContactCTypeRelConsts.descriptorName
			, new LongFDesc(ContactCTypeRelConsts.k_id, /*hc:d1*/"Id"/*hc*/)/*hc:d2_1*//*hc*/
			, new RelationFDesc(ContactCTypeRelConsts.k_contactType, /*hc:d3*/"ContactType"/*hc*/
										, ContactTypeConsts.descriptorName)/*hc:d2_3*//*hc*/
			, new LongFDesc(ContactCTypeRelConsts.k_orderNum, /*hc:d4*/"OrderNum"/*hc*/)/*hc:d2_4*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(ContactCTypeRelConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(ContactCTypeRelConsts.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(ContactCTypeRelConsts.k_contactType, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(ContactCTypeRelConsts.k_orderNum, new ColRDesc(/*hc:tdr1_4*/100, true/*hc*/)/*hc:tdr2_4*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ContactCTypeRelConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(ContactCTypeRelConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(ContactCTypeRelConsts.k_contactType, new WidgetRDesc(/*hc:f3*/FWTypes.LISTBOX/*hc*/))
			.addChild(ContactCTypeRelConsts.k_orderNum, new WidgetRDesc(/*hc:f4*/FWTypes.NUMBERTEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(ContactCTypeRelConsts.searchDescriptor
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(ContactCTypeRelConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
