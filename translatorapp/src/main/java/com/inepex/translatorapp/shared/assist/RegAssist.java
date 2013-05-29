package com.inepex.translatorapp.shared.assist;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.kvo.RegConsts;

public class RegAssist extends Assist {
	
	public RegAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(RegConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(RegConsts.descriptorName
			, new LongFDesc(RegConsts.k_id, /*hc:d1*/translatorappI18n.reg_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(RegConsts.k_email, /*hc:d2*/translatorappI18n.reg_email()/*hc*/)
				.email().mandatory()/*hc:d2_2*//*hc*/
			, new StringFDesc(RegConsts.k_password, /*hc:d3*/translatorappI18n.reg_password()/*hc*/)
				.mandatory()/*hc:d2_3*//*hc*/
			, new StringFDesc(RegConsts.k_passwordAgain, /*hc:d4*/translatorappI18n.reg_passwordAgain()/*hc*/)
				.mandatory()/*hc:d2_4*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(RegConsts.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(RegConsts.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(RegConsts.k_email, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(RegConsts.k_password, new WidgetRDesc(/*hc:f3*/FWTypes.PASSWORDTEXTBOX/*hc*/))
			.addChild(RegConsts.k_passwordAgain, new WidgetRDesc(/*hc:f4*/FWTypes.PASSWORDTEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		return new TableRDesc(RegConsts.descriptorName);
	}
	
	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(RegConsts.searchDescriptor);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		return new FormRDesc(RegConsts.searchDescriptor);
	}

	public static String getOrderKey(){
		return "";
	}
}