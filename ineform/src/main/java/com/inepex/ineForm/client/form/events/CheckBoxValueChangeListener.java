package com.inepex.ineForm.client.form.events;

import com.inepex.ineForm.client.table.AbstractIneTable;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface CheckBoxValueChangeListener {
    public void onCheckBoxValueChanged(
        AbstractIneTable soureTable,
        String columnOfCheckbox,
        Boolean newValue,
        AssistedObject newState);
}
