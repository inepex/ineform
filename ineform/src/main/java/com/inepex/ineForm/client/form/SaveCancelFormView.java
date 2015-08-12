package com.inepex.ineForm.client.form;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.form.events.AfterUnsuccessfulSaveEvent;
import com.inepex.ineForm.client.form.events.SavedEvent;

public interface SaveCancelFormView extends IsWidget {

    public static interface Delegate {
        void saveClicked();

        void cancelClicked();

        void deleteClicked();

        HandlerRegistration addFormSavedHandlerFromView(SavedEvent.Handler handler);

        HandlerRegistration addFormAfterUnsuccesfulSaveHandlerFromView(
            AfterUnsuccessfulSaveEvent.Handler handler);
    }

    public void setDelegate(SaveCancelFormView.Delegate delegate);

    public void setFormWidget(Widget widget);

    /**
     * to override default value
     */
    public void setSaveButtonText(String saveButtonText);

    /**
     * to override default value
     */
    public void setCancelButtonText(String cancelButtonText);

    /**
     * to override default value
     */
    public void setSaveBtnStyle(String style);

    public void addSaveBtnStyle(String style);

    /**
     * to override default value
     */
    public void setCancelBtnStyle(String style);

    public void addCancelBtnStyle(String style);

    public void setSaveButtonVisible(boolean visible);

    public void setCancelButtonVisible(boolean visible);

    public void setFormValidationSuccess(boolean isSuccess);

    public void dataReseted();

    public void forceLoadingOnSaveBtn(boolean loading);
}
