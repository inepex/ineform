package com.inepex.ineForm.client.form.widgets.assist;
import com.inepex.ineForm.client.form.widgets.kvo.NationalityKVO;
import com.inepex.ineForm.client.form.widgets.kvo.search.NationalitySearchKVO;
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

public class NationalityAssist {

	private static FormRDesc formRDesc = null;
	private static FormRDesc searchFormRDesc = null;	
	private static TableRDesc tableRDesc = null;
	private static TableRDesc customTableRDesc = null;
	private static ObjectDesc objectDesc = null;
	private static ObjectDesc searchObjectDesc = null;
	private static ValidatorDesc validatorDesc = null;
	
	public static void registerDescriptors(DescriptorStore descStore) {
		descStore.registerDescriptors(getObjectDesc(), getTableRDesc(), getFormRDesc(), getValidatorDesc());
		descStore.registerDescriptors(getSearchObjectDesc(), null, getSearchFormRDesc(), null);
		descStore.addNamedTypedDesc(NationalityKVO.descriptorName, "custom", getCustomTableRDesc());
		
		/*hc:extraDescriptors*/
		/*hc*/
	}
	
	public static ValidatorDesc getValidatorDesc() {
		if(validatorDesc!=null) return validatorDesc;
		
		validatorDesc = new ValidatorDesc(NationalityKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
		
		return validatorDesc;
	}

	public static ObjectDesc getObjectDesc() {
		if (objectDesc != null)
			return objectDesc;
			
		objectDesc = new ObjectDesc(NationalityKVO.descriptorName
			, new LongFDesc(NationalityKVO.k_id, /*hc:d1*/"Id"/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(NationalityKVO.k_name, /*hc:d2*/"Name"/*hc*/)/*hc:d2_2*//*hc*/
	
	
		);
		return objectDesc;
	}

	public static TableRDesc getTableRDesc() {
		if (tableRDesc == null){
			tableRDesc = new TableRDesc(NationalityKVO.descriptorName);
			
			tableRDesc.getRootNode()
				.addChild(NationalityKVO.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
				.addChild(NationalityKVO.k_name, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*//*hc*/)
				;
		}
		return tableRDesc;
	}
	
	public static TableRDesc getCustomTableRDesc() {
		if (customTableRDesc == null){
			customTableRDesc = new TableRDesc(NationalityKVO.descriptorName);
			
			customTableRDesc.getRootNode()
				.addChild(NationalityKVO.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
				.addChild(NationalityKVO.k_name, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*//*hc*/)
				.addChild("customfield", new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
				;
		}
		return customTableRDesc;
	}
	
	public static FormRDesc getFormRDesc() {
		if (formRDesc == null){
			formRDesc = new FormRDesc(NationalityKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
			formRDesc.getRootNode()
				.addChild(NationalityKVO.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
				.addChild(NationalityKVO.k_name, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
				;
		}
		return formRDesc;
	}
	
	public static ObjectDesc getSearchObjectDesc() {
		if (searchObjectDesc != null)
			return searchObjectDesc;
			
		searchObjectDesc = new ObjectDesc(NationalitySearchKVO.descriptorName
		);
		return searchObjectDesc;
	}
	
	public static FormRDesc getSearchFormRDesc() {
		if (searchFormRDesc == null){
			searchFormRDesc = new FormRDesc(NationalitySearchKVO.descriptorName);
			
			searchFormRDesc.getRootNode().dummy()
				;
		}
		return searchFormRDesc;
	}	
	
	public static String getOrderKey(){
		//displayname field
		String key = "";
		key = NationalityKVO.k_name;
		
		return key;
	}
}
