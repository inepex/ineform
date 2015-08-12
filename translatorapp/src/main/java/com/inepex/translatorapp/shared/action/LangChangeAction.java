package com.inepex.translatorapp.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.inepex.ineom.shared.dispatch.GenericActionResult;

public class LangChangeAction implements Action<GenericActionResult> {

    private Boolean currentState;
    private Long moduleId;
    private Long langId;

    public LangChangeAction() {}

    public LangChangeAction(Boolean currentState, Long moduleId, Long langId) {
        this.currentState = currentState;
        this.moduleId = moduleId;
        this.langId = langId;
    }

    public Boolean getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Boolean currentState) {
        this.currentState = currentState;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }
}
