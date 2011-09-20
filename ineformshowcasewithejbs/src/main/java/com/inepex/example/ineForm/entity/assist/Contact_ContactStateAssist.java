package com.inepex.example.ineForm.entity.assist;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateConsts;
import com.inepex.example.ineForm.enums.ContactState;
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
import com.inepex.ineom.shared.descriptor.ValidatorDesc;

public class Contact_ContactStateAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"contact_ContactState_id";"";"Id";"Id"
//"contact_ContactState_state";"";"State";"State"
//"contact_ContactState_orderNum";"";"OrderNum";"OrderNum"
//
		public final static String contact_ContactState_id = /*hc:i18n_1*/"Id"/*hc*/;
		public final static String contact_ContactState_state = /*hc:i18n_3*/"State"/*hc*/;
		public final static String contact_ContactState_orderNum = /*hc:i18n_4*/"OrderNum"/*hc*/;
	}
	
	public Contact_ContactStateAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(Contact_ContactStateConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(Contact_ContactStateConsts.descriptorName
			, new LongFDesc(Contact_ContactStateConsts.k_id, /*hc:d1*/I18n.contact_ContactState_id/*hc*/)/*hc:d2_1*//*hc*/
			, new LongFDesc(Contact_ContactStateConsts.k_state, /*hc:d3*/I18n.contact_ContactState_state/*hc*/)/*hc:d2_3*//*hc*/
			, new LongFDesc(Contact_ContactStateConsts.k_orderNum, /*hc:d4*/I18n.contact_ContactState_orderNum/*hc*/)/*hc:d2_4*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(Contact_ContactStateConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(Contact_ContactStateConsts.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(Contact_ContactStateConsts.k_state, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/.prop(EnumListFW.enumValues + ":" + ContactState.getValuesAsString()))
			.addChild(Contact_ContactStateConsts.k_orderNum, new ColRDesc(/*hc:tdr1_4*/100, true/*hc*/)/*hc:tdr2_4*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(Contact_ContactStateConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(Contact_ContactStateConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(Contact_ContactStateConsts.k_state, new WidgetRDesc(/*hc:f3*/FWTypes.ENUMLISTBOX/*hc*/, EnumListFW.enumValues + ":" + ContactState.getValuesAsString()))
			.addChild(Contact_ContactStateConsts.k_orderNum, new WidgetRDesc(/*hc:f4*/FWTypes.NUMBERTEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(Contact_ContactStateConsts.searchDescriptor
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(Contact_ContactStateConsts.searchDescriptor);
			
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
