package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;


public abstract class Assist {

	protected DescriptorStore descStore;
	
	public Assist(DescriptorStore descStore) {
		this.descStore = descStore;
	}
	
	public final void registerDescriptors() {
		descStore.registerDescriptors(getObjectDesc(), getTableRDesc(), getFormRDesc(), getValidatorDesc());
		ObjectDesc searchObjectDesc = getSearchObjectDesc();
		if (searchObjectDesc != null)
			descStore.registerDescriptors(searchObjectDesc, null, getSearchFormRDesc(), null);
		
		registerExtraDescriptors();
	}
	
	protected abstract void registerExtraDescriptors();

	public abstract ObjectDesc getObjectDesc();

	public abstract TableRDesc getTableRDesc();

	public abstract FormRDesc getFormRDesc();

	public abstract ValidatorDesc getValidatorDesc();
	
	public abstract ObjectDesc getSearchObjectDesc();

	public abstract FormRDesc getSearchFormRDesc();

}