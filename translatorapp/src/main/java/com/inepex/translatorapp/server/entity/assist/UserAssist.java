package com.inepex.translatorapp.server.entity.assist;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.descriptor.fdesc.ListFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.server.entity.kvo.LangConsts;
import com.inepex.translatorapp.server.entity.kvo.UserConsts;

public class UserAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"user_id";"";"Id";"Id"
//"user_email";"";"Email";"Email"
//"user_roles";"";"Roles";"Roles"
//"user_translates";"";"Translates";"Translates"
//
		public static String user_id() { return /*hc:i18n_1*/"Id"/*hc*/;}
		public static String user_email() { return /*hc:i18n_2*/"Email"/*hc*/;}
		public static String user_roles() { return /*hc:i18n_4*/"Roles"/*hc*/;}
		public static String user_translates() { return /*hc:i18n_5*/"Translates"/*hc*/;}
	}
	
	public UserAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(UserConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(UserConsts.descriptorName
			, new LongFDesc(UserConsts.k_id, /*hc:d1*/I18n.user_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(UserConsts.k_email, /*hc:d2*/I18n.user_email()/*hc*/)/*hc:d2_2*//*hc*/
					.mandatory()
			, new StringFDesc(UserConsts.k_roles, /*hc:d4*/I18n.user_roles()/*hc*/)/*hc:d2_4*//*hc*/
			, new ListFDesc(UserConsts.k_translates, /*hc:d5*/I18n.user_translates()/*hc*/,LangConsts.descriptorName)/*hc:d2_5*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(UserConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(UserConsts.k_id, new ColRDesc(/*hc:tdr1_1*/true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(UserConsts.k_email, new ColRDesc(/*hc:tdr1_2*/true/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(UserConsts.k_roles, new ColRDesc(/*hc:tdr1_4*/true/*hc*/)/*hc:tdr2_4*//*hc*/)
			.addChild(UserConsts.k_translates, new ColRDesc(/*hc:tdr1_5*//*hc*/)/*hc:tdr2_5*//*hc*/)				
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(UserConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(UserConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(UserConsts.k_email, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(UserConsts.k_roles, new WidgetRDesc(/*hc:f4*/FWTypes.TEXTBOX/*hc*/))
			.addChild(UserConsts.k_translates, new WidgetRDesc(/*hc:f5*/FWTypes.RELATIONLIST/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(UserConsts.searchDescriptor
			, new LongFDesc(UserConsts.s_id, /*hc:ds1*/"Id"/*hc*/)
			, new StringFDesc(UserConsts.s_email, /*hc:ds2*/"Email"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(UserConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(UserConsts.s_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(UserConsts.s_email, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}