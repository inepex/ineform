package com.inepex.translatorapp.shared.assist;
import com.inepex.ineForm.client.form.widgets.RelationListFW;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.ColRDescHAlign;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.descriptor.fdesc.ListFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;
import com.inepex.translatorapp.shared.kvo.ModuleRowConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;

public class ModuleRowAssist extends Assist {
	
	public static String engVal = "engVal_col";
	public static String engModTime = "engModtime_col";
	
	public ModuleRowAssist(DescriptorStore descStore) {
		super(descStore);
	}
	
	@Override
	protected void registerExtraDescriptors() {
	}
	
	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(ModuleRowConsts.descriptorName, new String[] {
			
		});
	}

	@Override
	public ObjectDesc getObjectDesc() {
		ObjectDesc objDesc = new ObjectDesc(ModuleRowConsts.descriptorName
			, new LongFDesc(ModuleRowConsts.k_id, translatorappI18n.moduleRow_id())
			, new StringFDesc(ModuleRowConsts.k_key, translatorappI18n.moduleRow_key())
					.alphanum().mandatory()
			, new StringFDesc(ModuleRowConsts.k_description, translatorappI18n.moduleRow_description())
					.alphanumOrSpace()
			, new RelationFDesc(ModuleRowConsts.k_module, translatorappI18n.moduleRow_module()
										, ModuleConsts.descriptorName)
					.mandatory()
			, new ListFDesc(ModuleRowConsts.k_values, translatorappI18n.moduleRow_values(),
					TranslatedValueConsts.descriptorName)
		);
		
		objDesc.setDefaultOrderKey(getOrderKey());
		return objDesc;
	}

	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(ModuleRowConsts.descriptorName);
			
		tableRDesc.getRootNode()
			.addChild(ModuleRowConsts.k_id, new ColRDesc())
			.addChild(ModuleRowConsts.k_module, new ColRDesc()
					.cropWidth(30)
					.setColumnWidth(80))
			.addChild(ModuleRowConsts.k_key, new ColRDesc()
					.cropWidth(50)
					.setColumnWidth(150)
					.hAlign(ColRDescHAlign.LEFT))
			.addChild(ModuleRowConsts.k_description, new ColRDesc()
					.cropWidth(200)
					.setColumnWidth(500)
					.hAlign(ColRDescHAlign.LEFT))
			.addChild(engVal, new ColRDesc()
					.setColumnWidth(500)
					.hAlign(ColRDescHAlign.LEFT)
					.setDisplayName(translatorappI18n.modRow_engVal()))
			.addChild(engModTime, new ColRDesc()
					.setColumnWidth(100)
					.setDisplayName(translatorappI18n.modRow_engModTime()))
			;
		return tableRDesc;
	}
	
	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(ModuleRowConsts.descriptorName
			);
			
		formRDesc.getRootNode()
			.addChild(ModuleRowConsts.k_id, new WidgetRDesc(FWTypes.LABEL))
			.addChild(ModuleRowConsts.k_module, new WidgetRDesc(FWTypes.LISTBOX))
			.addChild(ModuleRowConsts.k_key, new WidgetRDesc(FWTypes.TEXTBOX))
			.addChild(ModuleRowConsts.k_description, new WidgetRDesc(FWTypes.TEXTAREA))
			.addChild(ModuleRowConsts.k_values, new WidgetRDesc(FWTypes.RELATIONLIST, RelationListFW.FIXSIZED))
			;
		return formRDesc;
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {			
		return new ObjectDesc(ModuleRowConsts.searchDescriptor
			, new LongFDesc(ModuleRowConsts.s_id, "Id")
			, new StringFDesc(ModuleRowConsts.s_key, "Key")
			, new RelationFDesc(ModuleRowConsts.s_module, "Module"
										, ModuleConsts.searchDescriptor)
		);
	}
	
	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(ModuleRowConsts.searchDescriptor);
			
		searchFormRDesc.getRootNode().dummy()
			.addChild(ModuleRowConsts.s_id, new WidgetRDesc(FWTypes.LABEL))
			.addChild(ModuleRowConsts.s_key, new WidgetRDesc(FWTypes.TEXTBOX))
			.addChild(ModuleRowConsts.s_module, new WidgetRDesc(FWTypes.LISTBOX))

			;
		return searchFormRDesc;
	}

	public static String getOrderKey(){
		return ModuleRowConsts.k_key;
	}
}