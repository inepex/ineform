package com.inepex.ineForm.client.general;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.IneFormProperties;

public class IneListbox implements IsWidget {

    private final FlowPanel mainPanel = new FlowPanel();
    private final ListBox listBox = new ListBox();

    public IneListbox() {
        mainPanel.add(listBox);

        if (IneFormProperties.IN_OLD_STYLE_COMPATIBILITY_MODE)
            mainPanel.addStyleName(GeneralRes.INST.get().GeneralStyle().OLD_listBoxExtraDiv());
        else
            mainPanel.addStyleName(GeneralRes.INST.get().GeneralStyle().listBoxExtraDiv());

        setWidthOfCosturction(IneFormProperties.DEFAULT_ListBoxWidth_inPx);
    }

    public ListBox getListBox() {
        return listBox;
    }

    public void setWidthOfCosturction(int widhtInPx) {
        if (IneFormProperties.IN_OLD_STYLE_COMPATIBILITY_MODE) {
            mainPanel.getElement().setAttribute(
                "style",
                "width: " + widhtInPx + "px; " + "background-position: right -5px;");
        } else {
            mainPanel.getElement().setAttribute(
                "style",
                "width: " + widhtInPx + "px; " + "background-position: right 0px;");
        }

        listBox.getElement().getStyle().setWidth(widhtInPx + 24, Unit.PX);
    }

    @Override
    public Widget asWidget() {
        return mainPanel;
    }
}
