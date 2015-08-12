package com.inepex.translatorapp.shared.action;

import com.inepex.ineom.shared.dispatch.GenericActionResult;

public class TestLangChangeResult extends GenericActionResult {

    private Long willBeDeletedWithEmpty;
    private Long willBeDeletedWithText;

    public Long getWillBeDeletedWithEmpty() {
        return willBeDeletedWithEmpty;
    }

    public void setWillBeDeletedWithEmpty(Long willBeDeletedWithEmpty) {
        this.willBeDeletedWithEmpty = willBeDeletedWithEmpty;
    }

    public Long getWillBeDeletedWithText() {
        return willBeDeletedWithText;
    }

    public void setWillBeDeletedWithText(Long willBeDeletedWithText) {
        this.willBeDeletedWithText = willBeDeletedWithText;
    }
}
