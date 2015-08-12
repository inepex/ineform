package com.inepex.translatorapp.shared.action;

import net.customware.gwt.dispatch.shared.Action;

public class TestLangChangeAction implements Action<TestLangChangeResult> {

    private Boolean currentState;
    private Long moduleId;
    private Long langId;

    public TestLangChangeAction() {}

    public TestLangChangeAction(Boolean currentState, Long moduleId, Long langId) {
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
