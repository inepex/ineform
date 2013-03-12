package com.inepex.ineForm.client.form.events;

import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface CheckBoxValueChangeListener {
	public void onCheckBoxValueChanged(String columnOfCheckbox, boolean newValue, AssistedObject newState);
}
