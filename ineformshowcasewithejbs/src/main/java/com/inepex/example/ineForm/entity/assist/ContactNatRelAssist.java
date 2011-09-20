package com.inepex.example.ineForm.entity.assist;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelConsts;
import com.inepex.example.ineForm.entity.kvo.NationalityConsts;
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

public class ContactNatRelAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"contactNatRel_id";"";"Id";"Id"
//"contactNatRel_nationality";"";"Nationality";"Nationality"
//"contactNatRel_orderNum";"";"OrderNum";"OrderNum"
//
		public final static String contactNatRel_id = /*hc:i18n_1*/"Id"/*hc*/;
		public final static String contactNatRel_nationality = /*hc:i18n_3*/"Nationality"/*hc*/;
		public final static String contactNatRel_orderNum = /*hc:i18n_4*/"OrderNum"/*hc*/;
	}
	
	public ContactNatRelAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(ContactNatRelConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(ContactNatRelConsts.descriptorName
			, new LongFDesc(ContactNatRelConsts.k_id, /*hc:d1*/"Id"/*hc*/)/*hc:d2_1*//*hc*/
			, new RelationFDesc(ContactNatRelConsts.k_nationality, /*hc:d3*/"Nationality"/*hc*/
										, NationalityConsts.descriptorName)/*hc:d2_3*//*hc*/
			, new LongFDesc(ContactNatRelConsts.k_orderNum, /*hc:d4*/"OrderNum"/*hc*/)/*hc:d2_4*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(ContactNatRelConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(ContactNatRelConsts.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(ContactNatRelConsts.k_nationality, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(ContactNatRelConsts.k_orderNum, new ColRDesc(/*hc:tdr1_4*/100, true/*hc*/)/*hc:tdr2_4*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ContactNatRelConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(ContactNatRelConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(ContactNatRelConsts.k_nationality, new WidgetRDesc(/*hc:f3*/FWTypes.LISTBOX/*hc*/))
			.addChild(ContactNatRelConsts.k_orderNum, new WidgetRDesc(/*hc:f4*/FWTypes.NUMBERTEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(ContactNatRelConsts.searchDescriptor
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(ContactNatRelConsts.searchDescriptor);
			
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
