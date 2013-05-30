package com.inepex.translatorapp.shared.assist;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.descriptor.fdesc.BooleanFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.util.SharedUtil;
import com.inepex.translatorapp.shared.kvo.TranslateTableRowConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;

public class TranslateTableRowAssist extends Assist {

	public static class I18n{
// copy these to .csv
//"translateTableRow_id";"";"Id";"Id"
//"translateTableRow_recent";"";"Recent";"Recent"
//"translateTableRow_outDated";"";"OutDated";"OutDated"
//"translateTableRow_description";"";"Description";"Description"
//"translateTableRow_engVal";"";"EngVal";"EngVal"
//"translateTableRow_translatedValue";"";"TranslatedValue";"TranslatedValue"
//
		public static String translateTableRow_id() { return /*hc:i18n_1*/"Id"/*hc*/;}
		public static String translateTableRow_recent() { return /*hc:i18n_2*/"Recent"/*hc*/;}
		public static String translateTableRow_outDated() { return /*hc:i18n_3*/"OutDated"/*hc*/;}
		public static String translateTableRow_description() { return /*hc:i18n_4*/"Description"/*hc*/;}
		public static String translateTableRow_engVal() { return /*hc:i18n_5*/"EngVal"/*hc*/;}
		public static String translateTableRow_translatedValue() { return /*hc:i18n_6*/"TranslatedValue"/*hc*/;}
	}
	
	public TranslateTableRowAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(TranslateTableRowConsts.descriptorName, new String[] {/*hc:vd1*/
			
		/*hc*/});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(TranslateTableRowConsts.descriptorName
			, new LongFDesc(TranslateTableRowConsts.k_id, /*hc:d1*/I18n.translateTableRow_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new BooleanFDesc(TranslateTableRowConsts.k_recent, /*hc:d2*/I18n.translateTableRow_recent()/*hc*/)/*hc:d2_2*//*hc*/
			, new BooleanFDesc(TranslateTableRowConsts.k_outDated, /*hc:d3*/I18n.translateTableRow_outDated()/*hc*/)/*hc:d2_3*//*hc*/
			, new StringFDesc(TranslateTableRowConsts.k_description, /*hc:d4*/I18n.translateTableRow_description()/*hc*/)/*hc:d2_4*//*hc*/
			, new StringFDesc(TranslateTableRowConsts.k_engVal, /*hc:d5*/I18n.translateTableRow_engVal()/*hc*/)/*hc:d2_5*//*hc*/
			, new RelationFDesc(TranslateTableRowConsts.k_translatedValue, /*hc:d6*/I18n.translateTableRow_translatedValue()/*hc*/
										, TranslatedValueConsts.descriptorName)/*hc:d2_6*//*hc*/
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(TranslateTableRowConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(TranslateTableRowConsts.k_id, new ColRDesc(/*hc:tdr1_1*/false/*hc*/)/*hc:tdr2_1*//*hc*/)
			.addChild(TranslateTableRowConsts.k_recent, new ColRDesc(/*hc:tdr1_2*/false/*hc*/)/*hc:tdr2_2*//*hc*/)
			.addChild(TranslateTableRowConsts.k_outDated, new ColRDesc(/*hc:tdr1_3*/false/*hc*/)/*hc:tdr2_3*//*hc*/)
			.addChild(TranslateTableRowConsts.k_description, new ColRDesc(/*hc:tdr1_4*/false/*hc*/)/*hc:tdr2_4*//*hc*/)
			.addChild(TranslateTableRowConsts.k_engVal, new ColRDesc(/*hc:tdr1_5*/false/*hc*/)/*hc:tdr2_5*//*hc*/)
			.addChild(tv(TranslatedValueConsts.k_lang), new ColRDesc(/*hc:tdr1_6*/false/*hc*/)/*hc:tdr2_6*//*hc*/)
			.addChild(tv(TranslatedValueConsts.k_value), new ColRDesc(/*hc:tdr1_6*/false/*hc*/)/*hc:tdr2_6*//*hc*/)
			;
		return tableRDesc;
	}
	
	public static String tv(String key) {
		return SharedUtil.Str(TranslateTableRowConsts.k_translatedValue, key);
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		return new FormRDesc(TranslateTableRowConsts.descriptorName);
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(TranslateTableRowConsts.searchDescriptor);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		return new FormRDesc(TranslateTableRowConsts.searchDescriptor);
	}

	public static String getOrderKey(){
		return "";
	}
}