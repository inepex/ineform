package com.inepex.example.ineForm.entity.assist;
import com.inepex.example.ineForm.entity.kvo.ContactAddresDetailKVO;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelKVO;
import com.inepex.example.ineForm.entity.kvo.ContactKVO;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelKVO;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleKVO;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateKVO;
import com.inepex.example.ineForm.entity.kvo.search.ContactSearchKVO;
import com.inepex.example.ineForm.enums.ContactRole;
import com.inepex.example.ineForm.enums.ContactState;
import com.inepex.ineForm.client.form.widgets.chooser.ChooserFw;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineForm.shared.types.PanelWidgetT;
import com.inepex.ineom.shared.descriptor.BooleanFDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.ListFDesc;
import com.inepex.ineom.shared.descriptor.LongFDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.descriptor.StringFDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.kvo.IFConsts;

public class ContactAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"contact_id";"";"Id";"Id"
//"contact_firstName";"";"FirstName";"FirstName"
//"contact_lastName";"";"LastName";"LastName"
//"contact_address";"";"Address";"Address"
//"contact_createDate";"";"CreateDate";"CreateDate"
//"contact_numOfAccess";"";"NumOfAccess";"NumOfAccess"
//"contact_contactTypes";"";"ContactTypes";"ContactTypes"
//"contact_profilePhoto";"";"ProfilePhoto";"ProfilePhoto"
//"contact_nationalities";"";"Nationalities";"Nationalities"
//"contact_addressDetail";"";"AddressDetail";"AddressDetail"
//"contact_happy";"";"Happy";"Happy"
//"contact_roles";"";"Roles";"Roles"
//"contact_states";"";"States";"States"
//
		public final static String contact_id = /*hc:i18n_1*/"Id"/*hc*/;
		public final static String contact_firstName = /*hc:i18n_2*/"FirstName"/*hc*/;
		public final static String contact_lastName = /*hc:i18n_3*/"LastName"/*hc*/;
		public final static String contact_address = /*hc:i18n_4*/"Address"/*hc*/;
		public final static String contact_createDate = /*hc:i18n_5*/"CreateDate"/*hc*/;
		public final static String contact_numOfAccess = /*hc:i18n_6*/"NumOfAccess"/*hc*/;
		public final static String contact_contactTypes = /*hc:i18n_7*/"ContactTypes"/*hc*/;
		public final static String contact_profilePhoto = /*hc:i18n_8*/"ProfilePhoto"/*hc*/;
		public final static String contact_nationalities = /*hc:i18n_9*/"Nationalities"/*hc*/;
		public final static String contact_addressDetail = /*hc:i18n_10*/"AddressDetail"/*hc*/;
		public final static String contact_happy = /*hc:i18n_11*/"Happy"/*hc*/;
		public final static String contact_roles = /*hc:i18n_12*/"Roles"/*hc*/;
		public final static String contact_states = /*hc:i18n_13*/"States"/*hc*/;
	}
	
	public ContactAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(ContactKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(ContactKVO.descriptorName
			, new LongFDesc(ContactKVO.k_id, /*hc:d1*/"Id"/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(ContactKVO.k_firstName, /*hc:d2*/"FirstName"/*hc*/)/*hc:d2_2*//*hc*/
			, new StringFDesc(ContactKVO.k_lastName, /*hc:d3*/"LastName"/*hc*/)/*hc:d2_3*//*hc*/
			, new StringFDesc(ContactKVO.k_address, /*hc:d4*/"Address"/*hc*/)/*hc:d2_4*//*hc*/
			, new LongFDesc(ContactKVO.k_createDate, /*hc:d5*/"CreateDate"/*hc*/)/*hc:d2_5*//*hc*/
			, new LongFDesc(ContactKVO.k_numOfAccess, /*hc:d6*/"NumOfAccess"/*hc*/)/*hc:d2_6*//*hc*/
			, new ListFDesc(ContactKVO.k_contactTypes, /*hc:d7*/"ContactTypes"/*hc*/,ContactCTypeRelKVO.descriptorName, "secondLevelJoin:contactType")/*hc:d2_7*//*hc*/
			, new StringFDesc(ContactKVO.k_profilePhoto, /*hc:d8*/"ProfilePhoto"/*hc*/)/*hc:d2_8*//*hc*/
			, new ListFDesc(ContactKVO.k_nationalities, /*hc:d9*/"Nationalities"/*hc*/,ContactNatRelKVO.descriptorName, "secondLevelJoin:nationality")/*hc:d2_9*//*hc*/
			, new RelationFDesc(ContactKVO.k_addressDetail, /*hc:d10*/"AddressDetail"/*hc*/
										, ContactAddresDetailKVO.descriptorName)/*hc:d2_10*//*hc*/
			, new BooleanFDesc(ContactKVO.k_happy, /*hc:d11*/"Happy"/*hc*/)/*hc:d2_11*//*hc*/
			, new ListFDesc(ContactKVO.k_roles, /*hc:d12*/I18n.contact_roles/*hc*/,Contact_ContactRoleKVO.descriptorName, "secondLevelJoin:role")/*hc:d2_12*//*hc*/
			, new ListFDesc(ContactKVO.k_states, /*hc:d13*/I18n.contact_states/*hc*/,Contact_ContactStateKVO.descriptorName, "secondLevelJoin:state")/*hc:d2_13*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(ContactKVO.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(ContactKVO.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(ContactKVO.k_firstName, new ColRDesc(/*hc:tdr1_2*/200, true/*hc*/)/*hc:tdr2_2*/.prop(ColRDesc.DEFAULTSORT)/*hc*/)
			.addChild(ContactKVO.k_lastName, new ColRDesc(/*hc:tdr1_3*/50, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(ContactKVO.k_address, new ColRDesc(/*hc:tdr1_4*/50, true/*hc*/)/*hc:tdr2_4*//*hc*/)
			.addChild(ContactKVO.k_createDate, new ColRDesc(/*hc:tdr1_5*/100, true/*hc*/)/*hc:tdr2_5*/.asDate()/*hc*/)
			.addChild(ContactKVO.k_numOfAccess, new ColRDesc(/*hc:tdr1_6*/100, true/*hc*/)/*hc:tdr2_6*//*hc*/)
			.addChild(ContactKVO.k_contactTypes, new ColRDesc(/*hc:tdr1_7*/100/*hc*/)/*hc:tdr2_7*//*hc*/)				
			.addChild(ContactKVO.k_profilePhoto, new ColRDesc(/*hc:tdr1_8*/100, true/*hc*/)/*hc:tdr2_8*//*hc*/)
			.addChild(ContactKVO.k_nationalities, new ColRDesc(/*hc:tdr1_9*/100/*hc*/)/*hc:tdr2_9*//*hc*/)				
			.addChild(ContactKVO.k_addressDetail, new ColRDesc(/*hc:tdr1_10*/100, true/*hc*/)/*hc:tdr2_10*//*hc*/)
			.addChild(ContactKVO.k_happy, new ColRDesc(/*hc:tdr1_11*/30, true/*hc*/)/*hc:tdr2_11*//*hc*/)
			.addChild(ContactKVO.k_roles, new ColRDesc(/*hc:tdr1_12*/100/*hc*/)/*hc:tdr2_12*//*hc*/)				
			.addChild(ContactKVO.k_states, new ColRDesc(/*hc:tdr1_13*/100/*hc*/)/*hc:tdr2_13*//*hc*/)				
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ContactKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(ContactKVO.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(ContactKVO.k_firstName, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactKVO.k_lastName, new WidgetRDesc(/*hc:f3*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactKVO.k_address, new WidgetRDesc(/*hc:f4*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactKVO.k_createDate, new WidgetRDesc(/*hc:f5*/FWTypes.NUMBERTEXTBOX/*hc*/))
			.addChild(ContactKVO.k_numOfAccess, new WidgetRDesc(/*hc:f6*/FWTypes.NUMBERTEXTBOX/*hc*/))
			.addChild(ContactKVO.k_contactTypes, new WidgetRDesc(/*hc:f7*/FWTypes.CHOOSER/*hc*/))
			.addChild(ContactKVO.k_profilePhoto, new WidgetRDesc(/*hc:f8*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactKVO.k_nationalities, new WidgetRDesc(/*hc:f9*/FWTypes.CHOOSER/*hc*/))
			.addChild(ContactKVO.k_addressDetail, new WidgetRDesc(/*hc:f10*/FWTypes.RELATEDFORM/*hc*/))
			.addChild(ContactKVO.k_happy, new WidgetRDesc(/*hc:f11*/FWTypes.CHECKBOX/*hc*/))
			.addChild(ContactKVO.k_roles, new WidgetRDesc(/*hc:f12*/FWTypes.CHOOSER, ChooserFw.stringChooser, IFConsts.enumValues + ":" + ContactRole.getValuesAsString()/*hc*/))
			.addChild(ContactKVO.k_states, new WidgetRDesc(/*hc:f13*/FWTypes.CHOOSER, ChooserFw.enumChooser, IFConsts.enumValues + ":" + ContactState.getValuesAsString()/*hc*/))
			;
		return formRDesc;
	}
	
	public FormRDesc getWizardFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ContactKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
		.addChildGC(new PanelWidgetRDesc(PanelWidgetT.STEPPERPANEL))
			.addChild(ContactKVO.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(ContactKVO.k_firstName, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactKVO.k_lastName, new WidgetRDesc(/*hc:f3*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactKVO.k_address, new WidgetRDesc(/*hc:f4*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactKVO.k_createDate, new WidgetRDesc(/*hc:f5*/FWTypes.NUMBERTEXTBOX/*hc*/))
			.addChild(ContactKVO.k_numOfAccess, new WidgetRDesc(/*hc:f6*/FWTypes.NUMBERTEXTBOX/*hc*/))
			.addChild(ContactKVO.k_contactTypes, new WidgetRDesc(/*hc:f7*/FWTypes.CHOOSER/*hc*/))
			.addChild(ContactKVO.k_profilePhoto, new WidgetRDesc(/*hc:f8*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactKVO.k_nationalities, new WidgetRDesc(/*hc:f9*/FWTypes.CHOOSER/*hc*/))
			.addChild(ContactKVO.k_addressDetail, new WidgetRDesc(/*hc:f10*/FWTypes.RELATEDFORM/*hc*/))
			.addChild(ContactKVO.k_happy, new WidgetRDesc(/*hc:f11*/FWTypes.CHECKBOX/*hc*/))
			.addChild(ContactKVO.k_roles, new WidgetRDesc(/*hc:f12*/FWTypes.CHOOSER, ChooserFw.stringChooser, IFConsts.enumValues + ":" + ContactRole.getValuesAsString()/*hc*/))
			.addChild(ContactKVO.k_states, new WidgetRDesc(/*hc:f13*/FWTypes.CHOOSER, ChooserFw.enumChooser, IFConsts.enumValues + ":" + ContactState.getValuesAsString()/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(ContactSearchKVO.descriptorName
			, new StringFDesc(ContactSearchKVO.k_firstName, /*hc:ds2*/"FirstName"/*hc*/)
			, new StringFDesc(ContactSearchKVO.k_lastName, /*hc:ds3*/"LastName"/*hc*/)
			, new ListFDesc(ContactSearchKVO.k_contactTypes, /*hc:ds7*/"ContactTypes"/*hc*/
										, ContactCTypeRelKVO.descriptorName)
			, new ListFDesc(ContactSearchKVO.k_nationalities, /*hc:ds9*/"Nationalities"/*hc*/
										, ContactNatRelKVO.descriptorName)
			, new BooleanFDesc(ContactSearchKVO.k_happy, /*hc:ds11*/"Happy"/*hc*/)
			, new ListFDesc(ContactSearchKVO.k_roles, /*hc:ds12*/"Roles"/*hc*/
										, Contact_ContactRoleKVO.descriptorName)
			, new ListFDesc(ContactSearchKVO.k_states, /*hc:ds13*/"States"/*hc*/
										, Contact_ContactStateKVO.descriptorName)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(ContactSearchKVO.descriptorName);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(ContactSearchKVO.k_firstName, new WidgetRDesc(/*hc:fs2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactSearchKVO.k_lastName, new WidgetRDesc(/*hc:fs3*/FWTypes.TEXTBOX/*hc*/))
			.addChild(ContactSearchKVO.k_contactTypes, new WidgetRDesc(/*hc:fs7*/FWTypes.RELATIONLIST/*hc*/))
			.addChild(ContactSearchKVO.k_nationalities, new WidgetRDesc(/*hc:fs9*/FWTypes.RELATIONLIST/*hc*/))
			.addChild(ContactSearchKVO.k_happy, new WidgetRDesc(/*hc:fs11*/FWTypes.CHECKBOX/*hc*/))
			.addChild(ContactSearchKVO.k_roles, new WidgetRDesc(/*hc:fs12*/FWTypes.RELATIONLIST/*hc*/))
			.addChild(ContactSearchKVO.k_states, new WidgetRDesc(/*hc:fs13*/FWTypes.RELATIONLIST/*hc*/))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
