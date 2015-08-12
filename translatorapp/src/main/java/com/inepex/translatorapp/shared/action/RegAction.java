package com.inepex.translatorapp.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.inepex.ineForm.shared.dispatch.ObjectManipulationActionResult;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class RegAction implements Action<ObjectManipulationActionResult> {

    private AssistedObject regKvo;

    public RegAction() {}

    public RegAction(AssistedObject regKvo) {
        this.regKvo = regKvo;
    }

    public AssistedObject getRegKvo() {
        return regKvo;
    }
}
