package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;

public abstract class AssistBase extends Assist {

	private String descriptorName;
	private String searchDescriptorName;
	
	public AssistBase(String descriptorName, String searchDescriptorName, 
			DescriptorStore descStore) {
		super(descStore);
		this.descriptorName =  descriptorName;
		this.searchDescriptorName = searchDescriptorName;
	}
	
	@Override
	protected void registerExtraDescriptors() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public TableRDesc getTableRDesc() {
		TableRDesc tableRDesc = new TableRDesc(descriptorName);
		return tableRDesc;
	}

	@Override
	public FormRDesc getFormRDesc() {
		FormRDesc formRDesc = new FormRDesc(descriptorName);
		return formRDesc;
	}

	@Override
	public ValidatorDesc getValidatorDesc() {
		return new ValidatorDesc(descriptorName, new String[] {});
	}

	@Override
	public ObjectDesc getSearchObjectDesc() {
		return new ObjectDesc(searchDescriptorName);
	}

	@Override
	public FormRDesc getSearchFormRDesc() {
		FormRDesc searchFormRDesc = new FormRDesc(searchDescriptorName);
		return searchFormRDesc;
	}

	public String getDescriptorName() {
		return descriptorName;
	}

	public String getSearchDescriptorName() {
		return searchDescriptorName;
	}
	
	

}
