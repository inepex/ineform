package com.inepex.ineForm.client.form.widgets.assist;
import com.inepex.ineForm.client.form.widgets.kvo.ContactKVO;
import com.inepex.ineForm.client.form.widgets.kvo.ContactNatRelKVO;
import com.inepex.ineForm.client.form.widgets.kvo.search.ContactSearchKVO;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.ListFDesc;
import com.inepex.ineom.shared.descriptor.LongFDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore.Marker;

public class ContactAssist {

	private static FormRDesc formRDesc = null;
	private static FormRDesc searchFormRDesc = null;	
	private static TableRDesc tableRDesc = null;
	private static ObjectDesc objectDesc = null;
	private static ObjectDesc searchObjectDesc = null;
	private static ValidatorDesc validatorDesc = null;
	
	public static void registerDescriptors(DescriptorStore descStore) {
		descStore.registerDescriptors(Marker.registered, getObjectDesc(), getTableRDesc(), getFormRDesc(), getValidatorDesc());
		descStore.registerDescriptors(Marker.registered, getSearchObjectDesc(), null, getSearchFormRDesc(), null);
		
		/*hc:extraDescriptors*/
		/*hc*/
	}
	
	public static ValidatorDesc getValidatorDesc() {
		if(validatorDesc!=null) return validatorDesc;
		
		validatorDesc = new ValidatorDesc(ContactKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
		
		return validatorDesc;
	}

	public static ObjectDesc getObjectDesc() {
		if (objectDesc != null)
			return objectDesc;
			
		objectDesc = new ObjectDesc(ContactKVO.descriptorName
			, new LongFDesc(ContactKVO.k_id, /*hc:d1*/"Id"/*hc*/)/*hc:d2_1*//*hc*/
			, new ListFDesc(ContactKVO.k_nationalities, /*hc:d2*/"Nationalities"/*hc*/,ContactNatRelKVO.descriptorName, "secondLevelJoin:nationality")/*hc:d2_2*//*hc*/
	
	
		);
		return objectDesc;
	}

	public static TableRDesc getTableRDesc() {
		if (tableRDesc == null){
			tableRDesc = new TableRDesc(ContactKVO.descriptorName);
			
			tableRDesc.getRootNode()
				.addChild(ContactKVO.k_id, new ColRDesc(/*hc:tdr1_1*/true/*hc*/)/*hc:tdr2_1*//*hc*/)
				.addChild(ContactKVO.k_nationalities, new ColRDesc(/*hc:tdr1_2*//*hc*/)/*hc:tdr2_2*//*hc*/)				
				;
		}
		return tableRDesc;
	}
	
	public static FormRDesc getFormRDesc() {
		if (formRDesc == null){
			formRDesc = new FormRDesc(ContactKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
			formRDesc.getRootNode()
				.addChild(ContactKVO.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
				.addChild(ContactKVO.k_nationalities, new WidgetRDesc(/*hc:f2*/FWTypes.RELATIONLIST/*hc*/))
				;
		}
		return formRDesc;
	}
	
	public static ObjectDesc getSearchObjectDesc() {
		if (searchObjectDesc != null)
			return searchObjectDesc;
			
		searchObjectDesc = new ObjectDesc(ContactSearchKVO.descriptorName
			, new ListFDesc(ContactSearchKVO.k_nationalities, /*hc:ds2*/"Nationalities"/*hc*/
										, ContactNatRelKVO.descriptorName)
		);
		return searchObjectDesc;
	}
	
	public static FormRDesc getSearchFormRDesc() {
		if (searchFormRDesc == null){
			searchFormRDesc = new FormRDesc(ContactSearchKVO.descriptorName);
			
			searchFormRDesc.getRootNode().dummy()
				.addChild(ContactSearchKVO.k_nationalities, new WidgetRDesc(/*hc:fs2*/FWTypes.RELATIONLIST/*hc*/))
				;
		}
		return searchFormRDesc;
	}	
	
	public static String getOrderKey(){
		//displayname field
		String key = "";
		return key;
	}
}
