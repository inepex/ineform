package com.inepex.ineForm.client.general;

import com.google.gwt.user.client.ui.DialogBox;

public class IneDialogBox extends DialogBox {

    public IneDialogBox() {
        super();
        setStyle();
    }

    public IneDialogBox(boolean autoHide, boolean modal, Caption captionWidget) {
        super(autoHide, modal, captionWidget);
        setStyle();
    }

    public IneDialogBox(boolean autoHide, boolean modal) {
        super(autoHide, modal);
        setStyle();
    }

    public IneDialogBox(boolean autoHide) {
        super(autoHide);
        setStyle();
    }

    public IneDialogBox(Caption captionWidget) {
        super(captionWidget);
        setStyle();
    }

    private void setStyle() {
        setStyleName(GeneralRes.INST.get().GeneralStyle().ineDialogBox());
    }

}