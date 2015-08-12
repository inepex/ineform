package com.inepex.ineForm.client.form.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.general.IneButton;
import com.inepex.ineForm.client.general.IneButton.IneButtonType;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class StringListFw extends DenyingFormWidget {

    final public static String SEPARATOR = ";;;";

    final FlowPanel textBoxFlowPanel = new FlowPanel();
    final FlowPanel mainFlowPanel = new FlowPanel();
    final IneButton addButton = new IneButton(IneButtonType.CONTROL, IneFormI18n.ADD());

    final List<TextBox> textBoxList = new ArrayList<TextBox>();
    final ValueChangeHandler<String> ch;

    public StringListFw(FDesc fielddescriptor) {
        super(fielddescriptor);
        mainFlowPanel.add(textBoxFlowPanel);
        mainFlowPanel.add(addButton);

        initWidget(mainFlowPanel);

        ch = new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                fireFormWidgetChanged();
            }
        };
    }

    @Override
    protected void onAttach() {
        registerHandler(addButton.addClickHandler(new AddButtonClickHandler()));
        super.onAttach();
    }

    class AddButtonClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            addTextBox("");
            fireFormWidgetChanged();
        }
    }

    @Override
    public boolean handlesString() {
        return true;
    }

    @Override
    public void setStringValue(String value) {
        if (value == null)
            return;
        String[] texts = value.split(SEPARATOR);
        for (String string : texts) {
            addTextBox(string);
        }
    }

    @Override
    public String getStringValue() {
        StringBuilder sb = new StringBuilder();

        for (TextBox tBox : textBoxList) {
            if (tBox.getText().isEmpty())
                continue;
            sb.append(SEPARATOR).append(tBox.getText());
        }

        return sb.length() > 0 ? sb.substring(SEPARATOR.length()) : "";

    }

    private void addTextBox(String text) {
        FlowPanel panel = new FlowPanel();
        TextBox tBox = new TextBox();
        tBox.setWidth(IneFormProperties.DEFAULT_TextBoxWidth);
        registerHandler(tBox.addValueChangeHandler(ch));
        panel.add(tBox);

        if (text != null)
            tBox.setText(text);

        textBoxList.add(tBox);
        textBoxFlowPanel.add(panel);
    }

}
