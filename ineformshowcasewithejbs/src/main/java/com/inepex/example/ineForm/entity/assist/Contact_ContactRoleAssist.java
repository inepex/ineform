package com.inepex.example.ineForm.entity.assist;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleKVO;
import com.inepex.example.ineForm.entity.kvo.search.Contact_ContactRoleSearchKVO;
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

public class Contact_ContactRoleAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"contact_ContactRole_id";"";"Id";"Id"
//"contact_ContactRole_role";"";"Role";"Role"
//"contact_ContactRole_orderNum";"";"OrderNum";"OrderNum"
//
		public final static String contact_ContactRole_id = /*hc:i18n_1*/"Id"/*hc*/;
		public final static String contact_ContactRole_role = /*hc:i18n_3*/"Role"/*hc*/;
		public final static String contact_ContactRole_orderNum = /*hc:i18n_4*/"OrderNum"/*hc*/;
	}
	
	public Contact_ContactRoleAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(Contact_ContactRoleKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(Contact_ContactRoleKVO.descriptorName
			, new LongFDesc(Contact_ContactRoleKVO.k_id, /*hc:d1*/I18n.contact_ContactRole_id/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(Contact_ContactRoleKVO.k_role, /*hc:d3*/I18n.contact_ContactRole_role/*hc*/)/*hc:d2_3*//*hc*/
			, new LongFDesc(Contact_ContactRoleKVO.k_orderNum, /*hc:d4*/I18n.contact_ContactRole_orderNum/*hc*/)/*hc:d2_4*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(Contact_ContactRoleKVO.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(Contact_ContactRoleKVO.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(Contact_ContactRoleKVO.k_role, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(Contact_ContactRoleKVO.k_orderNum, new ColRDesc(/*hc:tdr1_4*/100, true/*hc*/)/*hc:tdr2_4*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(Contact_ContactRoleKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(Contact_ContactRoleKVO.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(Contact_ContactRoleKVO.k_role, new WidgetRDesc(/*hc:f3*/FWTypes.TEXTBOX/*hc*/))
			.addChild(Contact_ContactRoleKVO.k_orderNum, new WidgetRDesc(/*hc:f4*/FWTypes.NUMBERTEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(Contact_ContactRoleSearchKVO.descriptorName
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(Contact_ContactRoleSearchKVO.descriptorName);
			
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
