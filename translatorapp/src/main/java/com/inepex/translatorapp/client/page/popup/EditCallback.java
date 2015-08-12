package com.inepex.translatorapp.client.page.popup;

public interface EditCallback {
    public void onCancelled();

    public void onSave(String newTranslated);
}