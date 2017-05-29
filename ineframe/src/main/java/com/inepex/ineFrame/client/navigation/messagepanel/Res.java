package com.inepex.ineFrame.client.navigation.messagepanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * resources and style for this package (only this package!)
 * 
 * @author SoTi
 *
 */
interface Res extends ClientBundle {

    public static class INST {
        static Res INST;

        public static Res get() {
            if (INST == null) {
                INST = GWT.create(Res.class);
                INST.style().ensureInjected();
            }
            return INST;
        }
    }

    @Source("MessagePanelStyle.gss")
        MessagePanelStyle
            style();

    ImageResource system_alert_icon();

    ImageResource system_info_icon();

}
