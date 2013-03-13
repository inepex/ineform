package com.inepex.ineForm.client.form.events;

import com.inepex.ineForm.client.table.IneTable;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface CheckBoxValueChangeListener {
	public void onCheckBoxValueChanged(IneTable soureTable, String columnOfCheckbox, boolean newValue, AssistedObject newState);
}
