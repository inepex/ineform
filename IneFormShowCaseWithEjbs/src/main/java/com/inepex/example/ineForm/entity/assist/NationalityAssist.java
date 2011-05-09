package com.inepex.example.ineForm.entity.assist;
import com.inepex.example.ineForm.entity.kvo.NationalityKVO;
import com.inepex.example.ineForm.entity.kvo.search.NationalitySearchKVO;
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

public class NationalityAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"nationality_id";"";"Id";"Id"
//"nationality_name";"";"Name";"Name"
//"nationality_description";"";"Description";"Description"
//
		public final static String nationality_id = /*hc:i18n_1*/"Id"/*hc*/;
		public final static String nationality_name = /*hc:i18n_2*/"Name"/*hc*/;
		public final static String nationality_description = /*hc:i18n_3*/"Description"/*hc*/;
	}
	
	public NationalityAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	public void registerExtraDescriptors(DescriptorStore descStore) {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(NationalityKVO.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(NationalityKVO.descriptorName
			, new LongFDesc(NationalityKVO.k_id, /*hc:d1*/I18n.nationality_id/*hc*/)/*hc:d2_1*//*hc*/
			, new StringFDesc(NationalityKVO.k_name, /*hc:d2*/I18n.nationality_name/*hc*/)/*hc:d2_2*//*hc*/
			, new StringFDesc(NationalityKVO.k_description, /*hc:d3*/I18n.nationality_description/*hc*/)/*hc:d2_3*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(NationalityKVO.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(NationalityKVO.k_id, new ColRDesc(/*hc:tdr1_1*/100, true/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(NationalityKVO.k_name, new ColRDesc(/*hc:tdr1_2*/100, true/*hc*/)/*hc:tdr2_2*/.prop(ColRDesc.DEFAULTSORT)/*hc*/)
			.addChild(NationalityKVO.k_description, new ColRDesc(/*hc:tdr1_3*/100, true/*hc*/)/*hc:tdr2_3*//*hc*/)
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(NationalityKVO.descriptorName/*hc:frd_props*/
			
			/*hc*/);
			
		formRDesc.getRootNode()
			.addChild(NationalityKVO.k_id, new WidgetRDesc(/*hc:f1*/FWTypes.LABEL/*hc*/))
			.addChild(NationalityKVO.k_name, new WidgetRDesc(/*hc:f2*/FWTypes.TEXTBOX/*hc*/))
			.addChild(NationalityKVO.k_description, new WidgetRDesc(/*hc:f3*/FWTypes.TEXTBOX/*hc*/))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(NationalitySearchKVO.descriptorName
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(NationalitySearchKVO.descriptorName);
			
		searchFormRDesc.getRootNode().dummy()
			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		//displayname field
		String key = "";
		key = NationalityKVO.k_name;
		
		return key;
	}
}
