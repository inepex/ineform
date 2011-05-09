package com.inepex.example.ineForm.entity.assist;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelKVO;
import com.inepex.example.ineForm.entity.kvo.NationalityKVO;
import com.inepex.example.ineForm.entity.kvo.search.ContactNatRelSearchKVO;
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
	public void registerExtraDescriptors(DescriptorStore descStore) {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(ContactNatRelKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(ContactNatRelKVO.descriptorName
			, new LongFDesc(ContactNatRelKVO.k_id, /*hc:d1*/"Id"/*hc*/)/*hc:d2_1*//*hc*/
			, new RelationFDesc(ContactNatRelKVO.k_nationality, /*hc:d3*/"Nationality"/*hc*/
										, NationalityKVO.descriptorName)/*hc:d2_3*//*hc*/
			, new LongFDesc(ContactNatRelKVO.k_orderNum, /*hc:d4*/"OrderNum"/*hc*/)/*hc:d2_4*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(ContactNatRelKVO.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(ContactNatRelKVO.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(ContactNatRelKVO.k_nationality, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(ContactNatRelKVO.k_orderNum, new ColRDesc(/*hc:tdr1_4*/100, true/*hc*/)/*hc:tdr2_4*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ContactNatRelKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(ContactNatRelKVO.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(ContactNatRelKVO.k_nationality, new WidgetRDesc(/*hc:f3*/FWTypes.LISTBOX/*hc*/))
			.addChild(ContactNatRelKVO.k_orderNum, new WidgetRDesc(/*hc:f4*/FWTypes.NUMBERTEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(ContactNatRelSearchKVO.descriptorName
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(ContactNatRelSearchKVO.descriptorName);
			
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
