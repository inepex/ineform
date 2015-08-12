package com.inepex.ineForm.client.general;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface GeneralRes extends ClientBundle {

    public static class INST {
        private static GeneralRes INST;

        public static GeneralRes get() {
            if (INST == null) {
                INST = GWT.create(GeneralRes.class);
                INST.GeneralStyle().ensureInjected();
            }
            return INST;
        }
    }

    GeneralStyle GeneralStyle();

    @ImageOptions(repeatStyle = RepeatStyle.None)
    ImageResource checkBoxBG();

    @ImageOptions(repeatStyle = RepeatStyle.None)
    ImageResource checkBoxBG_old();

    @ImageOptions(repeatStyle = RepeatStyle.None)
    ImageResource select_icon();
}
