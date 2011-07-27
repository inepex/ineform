package com.inepex.example.ContactManager.entity.assist;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.UserKVO;
import com.inepex.example.ContactManager.entity.kvo.search.UserSearchKVO;
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

public class UserAssist extends Assist {
	
	public UserAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(UserKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(UserKVO.descriptorName
			, new LongFDesc(UserKVO.k_id, /*hc:d1*/CMI18n.user_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(UserKVO.k_firstName, /*hc:d2*/CMI18n.user_firstName()/*hc*/)/*hc:d2_2*//*hc*/
					.mandatory()
			, new StringFDesc(UserKVO.k_lastName, /*hc:d3*/CMI18n.user_lastName()/*hc*/)/*hc:d2_3*//*hc*/
					.mandatory()
			, new StringFDesc(UserKVO.k_email, /*hc:d4*/CMI18n.user_email()/*hc*/)/*hc:d2_4*//*hc*/
					.mandatory()
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(UserKVO.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(UserKVO.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(UserKVO.k_firstName, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(UserKVO.k_lastName, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(UserKVO.k_email, new ColRDesc(/*hc:tdr1_4*/100, true/*hc*/)/*hc:tdr2_4*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(UserKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(UserKVO.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(UserKVO.k_firstName, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(UserKVO.k_lastName, new WidgetRDesc(/*hc:f3*/FWTypes.TEXTBOX/*hc*/))
			.addChild(UserKVO.k_email, new WidgetRDesc(/*hc:f4*/FWTypes.TEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(UserSearchKVO.descriptorName
			, new LongFDesc(UserSearchKVO.k_id, /*hc:ds1*/"Id"/*hc*/)
			, new StringFDesc(UserSearchKVO.k_firstName, /*hc:ds2*/"FirstName"/*hc*/)
			, new StringFDesc(UserSearchKVO.k_lastName, /*hc:ds3*/"LastName"/*hc*/)
			, new StringFDesc(UserSearchKVO.k_email, /*hc:ds4*/"Email"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(UserSearchKVO.descriptorName);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(UserSearchKVO.k_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(UserSearchKVO.k_firstName, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(UserSearchKVO.k_lastName, new WidgetRDesc(/*hc:fs3*/FWTypes.TEXTBOX/*hc*/))
			.addChild(UserSearchKVO.k_email, new WidgetRDesc(/*hc:fs4*/FWTypes.TEXTBOX/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
