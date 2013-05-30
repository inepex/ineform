package com.inepex.translatorapp.shared.assist;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.ColRDescHAlign;
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
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.kvo.TranslateTableRowConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;

public class TranslateTableRowAssist extends Assist {
	
	public static String flags = "flags";
	
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
			, new LongFDesc(TranslateTableRowConsts.k_id, /*hc:d1*/translatorappI18n.translateTableRow_id()/*hc*/)/*hc:d2_1*//*hc*/
			, new BooleanFDesc(TranslateTableRowConsts.k_recent, /*hc:d2*/translatorappI18n.translateTableRow_recent()/*hc*/)/*hc:d2_2*//*hc*/
			, new BooleanFDesc(TranslateTableRowConsts.k_outDated, /*hc:d3*/translatorappI18n.translateTableRow_outDated()/*hc*/)/*hc:d2_3*//*hc*/
			, new StringFDesc(TranslateTableRowConsts.k_description, /*hc:d4*/translatorappI18n.translateTableRow_description()/*hc*/)/*hc:d2_4*//*hc*/
			, new StringFDesc(TranslateTableRowConsts.k_engVal, /*hc:d5*/translatorappI18n.translateTableRow_engVal()/*hc*/)/*hc:d2_5*//*hc*/
			, new RelationFDesc(TranslateTableRowConsts.k_translatedValue, /*hc:d6*/translatorappI18n.translateTableRow_translatedValue()/*hc*/
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
			.addChild(flags, new ColRDesc(/*hc:tdr1_2*/false/*hc*/)
				 .setColumnWidth(60)/*hc:tdr2_2*//*hc*/)
			.addChild(TranslateTableRowConsts.k_description, new ColRDesc(/*hc:tdr1_4*/false/*hc*/)
				.addProp(ColRDesc.ESCAPEHTML).cropWidth(150).hAlign(ColRDescHAlign.LEFT)/*hc:tdr2_4*//*hc*/)
			.addChild(TranslateTableRowConsts.k_engVal, new ColRDesc(/*hc:tdr1_5*/false/*hc*/)
				.addProp(ColRDesc.ESCAPEHTML).cropWidth(150).hAlign(ColRDescHAlign.LEFT)/*hc:tdr2_5*//*hc*/)
			.addChild(tv(TranslatedValueConsts.k_lang), new ColRDesc(/*hc:tdr1_6*/false/*hc*/)
				.setColumnWidth(40)/*hc:tdr2_6*//*hc*/)
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