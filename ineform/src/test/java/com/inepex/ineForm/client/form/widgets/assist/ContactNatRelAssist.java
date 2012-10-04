package com.inepex.ineForm.client.form.widgets.assist;
import com.inepex.ineForm.client.form.widgets.kvo.ContactNatRelKVO;
import com.inepex.ineForm.client.form.widgets.kvo.NationalityKVO;
import com.inepex.ineForm.client.form.widgets.kvo.search.ContactNatRelSearchKVO;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore.Marker;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;

public class ContactNatRelAssist {

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
		
		validatorDesc = new ValidatorDesc(ContactNatRelKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
		
		return validatorDesc;
	}

	public static ObjectDesc getObjectDesc() {
		if (objectDesc != null)
			return objectDesc;
			
		objectDesc = new ObjectDesc(ContactNatRelKVO.descriptorName
			, new LongFDesc(ContactNatRelKVO.k_id, /*hc:d1*/"Id"/*hc*/)/*hc:d2_1*//*hc*/
			, new RelationFDesc(ContactNatRelKVO.k_nationality, /*hc:d3*/"Nationality"/*hc*/
										, NationalityKVO.descriptorName)/*hc:d2_3*//*hc*/
			, new LongFDesc(ContactNatRelKVO.k_orderNum, /*hc:d4*/"OrderNum"/*hc*/)/*hc:d2_4*//*hc*/
	
	
		);
		return objectDesc;
	}

	public static TableRDesc getTableRDesc() {
		if (tableRDesc == null){
			tableRDesc = new TableRDesc(ContactNatRelKVO.descriptorName);
			
			tableRDesc.getRootNode()
				.addChild(ContactNatRelKVO.k_id, new ColRDesc(/*hc:tdr1_1*/true/*hc*/)/*hc:tdr2_1*//*hc*/)
				.addChild(ContactNatRelKVO.k_nationality, new ColRDesc(/*hc:tdr1_3*/true/*hc*/)/*hc:tdr2_3*//*hc*/)
				.addChild(ContactNatRelKVO.k_orderNum, new ColRDesc(/*hc:tdr1_4*/true/*hc*/)/*hc:tdr2_4*//*hc*/)
				;
		}
		return tableRDesc;
	}
	
	public static FormRDesc getFormRDesc() {
		if (formRDesc == null){
			formRDesc = new FormRDesc(ContactNatRelKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
			formRDesc.getRootNode()
				.addChild(ContactNatRelKVO.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
				.addChild(ContactNatRelKVO.k_nationality, new WidgetRDesc(/*hc:f3*/FWTypes.LISTBOX/*hc*/))
				.addChild(ContactNatRelKVO.k_orderNum, new WidgetRDesc(/*hc:f4*/FWTypes.NUMBERTEXTBOX/*hc*/))
				;
		}
		return formRDesc;
	}
	
	public static ObjectDesc getSearchObjectDesc() {
		if (searchObjectDesc != null)
			return searchObjectDesc;
			
		searchObjectDesc = new ObjectDesc(ContactNatRelSearchKVO.descriptorName
		);
		return searchObjectDesc;
	}
	
	public static FormRDesc getSearchFormRDesc() {
		if (searchFormRDesc == null){
			searchFormRDesc = new FormRDesc(ContactNatRelSearchKVO.descriptorName);
			
			searchFormRDesc.getRootNode().dummy()
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
