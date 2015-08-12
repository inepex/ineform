package com.inepex.ineForm.client.form.widgets.datetime;

import com.google.gwt.user.client.ui.Widget;

interface DateTimeFieldInterface {
    boolean isFocusable();

    boolean isTextBox();

    /**
     * in the field's opinion should all textbox be empty
     */
    boolean isEmpty();

    /**
     * in the filed's opinion should the FW's value be null
     */
    boolean isNull();

    boolean isInReadOnlyMode();

    Widget asWidget();

    void refresh(boolean empty, boolean initialValue);

    void setFocus(boolean focused);

    void setEnabled(boolean enabled);

}