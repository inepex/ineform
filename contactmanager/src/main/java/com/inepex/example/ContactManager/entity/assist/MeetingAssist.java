package com.inepex.example.ContactManager.entity.assist;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.CompanyConsts;
import com.inepex.example.ContactManager.entity.kvo.ContactConsts;
import com.inepex.example.ContactManager.entity.kvo.MeetingConsts;
import com.inepex.example.ContactManager.entity.kvo.UserConsts;
import com.inepex.example.ContactManager.shared.MeetingType;
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
import com.inepex.ineom.shared.descriptor.DescriptorStore.Marker;

public class MeetingAssist extends Assist {
	
	public static final String roFRD="roFRD";
	
	public MeetingAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
		descStore.addNamedTypedDesc(Marker.registered, MeetingConsts.descriptorName, roFRD, getROFormRDesc());
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(MeetingConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(MeetingConsts.descriptorName
			, new LongFDesc(MeetingConsts.k_id, /*hc:d1*/CMI18n.meeting_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new LongFDesc(MeetingConsts.k_meetingTimestamp, /*hc:d2*/CMI18n.meeting_meetingTimestamp()/*hc*/)/*hc:d2_2*//*hc*/
					.mandatory()
			, new RelationFDesc(MeetingConsts.k_user, /*hc:d3*/CMI18n.meeting_user()/*hc*/
										, UserConsts.descriptorName)/*hc:d2_3*//*hc*/
					.mandatory()
			, new RelationFDesc(MeetingConsts.k_company, /*hc:d4*/CMI18n.meeting_company()/*hc*/
										, CompanyConsts.descriptorName)/*hc:d2_4*//*hc*/
					.mandatory()
			, new RelationFDesc(MeetingConsts.k_contact, /*hc:d5*/CMI18n.meeting_contact()/*hc*/
										, ContactConsts.descriptorName)/*hc:d2_5*//*hc*/
					.mandatory()
			, new LongFDesc(MeetingConsts.k_meetingType, /*hc:d6*/CMI18n.meeting_meetingType()/*hc*/)/*hc:d2_6*//*hc*/
					.mandatory()
			, new StringFDesc(MeetingConsts.k_description, /*hc:d7*/CMI18n.meeting_description()/*hc*/)/*hc:d2_7*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(MeetingConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(MeetingConsts.k_meetingTimestamp, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/).asDate()/*hc:tdr2_2*//*hc*/)
			.addChild(MeetingConsts.k_user, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(MeetingConsts.k_company, new ColRDesc(/*hc:tdr1_4*/100, true/*hc*/)/*hc:tdr2_4*//*hc*/)
			.addChild(MeetingConsts.k_contact, new ColRDesc(/*hc:tdr1_5*/100, true/*hc*/)/*hc:tdr2_5*//*hc*/)
			.addChild(MeetingConsts.k_meetingType, new ColRDesc(/*hc:tdr1_6*/100, true/*hc*/)/*hc:tdr2_6*//*hc*/.prop(EnumListFW.enumValues + ":" + MeetingType.getValuesAsString()))
			.addChild(MeetingConsts.k_description, new ColRDesc(/*hc:tdr1_7*/100, true/*hc*/)/*hc:tdr2_7*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(MeetingConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(MeetingConsts.k_meetingTimestamp, new WidgetRDesc(/*hc:f2*/FWTypes.DATEBOX, "nowroundtomin:10", "date:sc", "hourmin:s(10)"/*hc*/))
			.addChild(MeetingConsts.k_user, new WidgetRDesc(/*hc:f3*/FWTypes.LISTBOX/*hc*/))
			.addChild(MeetingConsts.k_company, new WidgetRDesc(/*hc:f4*/FWTypes.LISTBOX/*hc*/))
			.addChild(MeetingConsts.k_contact, new WidgetRDesc(/*hc:f5*/FWTypes.LISTBOX/*hc*/))
			.addChild(MeetingConsts.k_meetingType, new WidgetRDesc(/*hc:f6*/FWTypes.ENUMLISTBOX/*hc*/, EnumListFW.enumValues + ":" + MeetingType.getValuesAsString()))
			.addChild(MeetingConsts.k_description, new WidgetRDesc(/*hc:f7*/FWTypes.TEXTAREA/*hc*/))
			;
		return formRDesc;
	}
	
	public FormRDesc getROFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(MeetingConsts.descriptorName);
			
		formRDesc.getRootNode()
			.addChild(MeetingConsts.k_meetingTimestamp, new WidgetRDesc(/*hc:f2*/FWTypes.DATEBOX, "date", "hourmin"/*hc*/))
			.addChild(MeetingConsts.k_user, new WidgetRDesc(/*hc:f3*/FWTypes.LABEL/*hc*/))
			.addChild(MeetingConsts.k_company, new WidgetRDesc(/*hc:f4*/FWTypes.LABEL/*hc*/))
			.addChild(MeetingConsts.k_contact, new WidgetRDesc(/*hc:f5*/FWTypes.LABEL/*hc*/))
			.addChild(MeetingConsts.k_meetingType, new WidgetRDesc(/*hc:f6*/FWTypes.ENUMLABEL/*hc*/, EnumListFW.enumValues + ":" + MeetingType.getValuesAsString()))
			.addChild(MeetingConsts.k_description, new WidgetRDesc(/*hc:f7*/FWTypes.LABEL/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(MeetingConsts.searchDescriptor
			, new LongFDesc(MeetingConsts.s_id, /*hc:ds1*/"Id"/*hc*/)
			, new LongFDesc(MeetingConsts.s_meetingTimestamp, /*hc:ds2*/"MeetingTimestamp"/*hc*/)
			, new RelationFDesc(MeetingConsts.s_user, /*hc:ds3*/"User"/*hc*/
										, UserConsts.searchDescriptor)
			, new RelationFDesc(MeetingConsts.s_company, /*hc:ds4*/"Company"/*hc*/
										, CompanyConsts.searchDescriptor)
			, new RelationFDesc(MeetingConsts.s_contact, /*hc:ds5*/"Contact"/*hc*/
										, ContactConsts.searchDescriptor)
			, new LongFDesc(MeetingConsts.s_meetingType, /*hc:ds6*/"MeetingType"/*hc*/)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(MeetingConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(MeetingConsts.s_id, new WidgetRDesc(/*hc:fs1*/FWTypes.LABEL/*hc*/))
			.addChild(MeetingConsts.s_meetingTimestamp, new WidgetRDesc(/*hc:fs2*/FWTypes.NUMBERTEXTBOX/*hc*/))
			.addChild(MeetingConsts.s_user, new WidgetRDesc(/*hc:fs3*/FWTypes.LISTBOX/*hc*/))

			.addChild(MeetingConsts.s_company, new WidgetRDesc(/*hc:fs4*/FWTypes.LISTBOX/*hc*/))

			.addChild(MeetingConsts.s_contact, new WidgetRDesc(/*hc:fs5*/FWTypes.LISTBOX/*hc*/))

			.addChild(MeetingConsts.s_meetingType, new WidgetRDesc(/*hc:f6*/FWTypes.ENUMLISTBOX/*hc*/,  EnumListFW.enumValues + ":" + MeetingType.getValuesAsString()))
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
