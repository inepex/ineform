package com.inepex.ineForm.client.form.widgets.richtextarea;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RichTextArea;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.formunits.SimpleTableFormUnit;
import com.inepex.ineForm.client.form.widgets.StringFormWidget;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class RichTextAreaFW extends StringFormWidget {

    /**
     * @Deprecated use {@link SimpleTableFormUnit#WIDTH} and
     *             {@link WidgetRDesc#width(int)} instead
     */
    public static final String textBoxWidth = SimpleTableFormUnit.WIDTH;

    public static final String textBoxHeight = "textBoxHeight";

    Grid grid = new Grid(2, 1);
    final RichTextArea textArea = new RichTextArea();
    final RichTextToolbar toolbar = new RichTextToolbar(textArea);

    public RichTextAreaFW(FDesc fielddescriptor, WidgetRDesc renderDesc) {
        super(fielddescriptor);
        initWidget(grid);
        grid.setWidget(0, 0, toolbar);
        grid.setWidget(1, 0, textArea);

        textArea.addStyleName(ResourceHelper.ineformRes().style().richTextArea());

        if (renderDesc.hasProp(SimpleTableFormUnit.WIDTH)) {
            textArea.setWidth(renderDesc.getPropValue(SimpleTableFormUnit.WIDTH));
        } else {
            textArea.setWidth(IneFormProperties.DEFAULT_TextBoxWidth);
        }
        if (renderDesc.hasProp(textBoxHeight)) {
            textArea.setHeight(renderDesc.getPropValue(textBoxHeight));
        } else {
            textArea.setHeight(IneFormProperties.DEFAULT_TextAreaHeigth);
        }

    }

    @Override
    protected void onAttach() {
        registerHandler(textArea.addKeyUpHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent event) {
                fireFormWidgetChanged();
            }
        }));
        super.onAttach();
    }

    @Override
    public boolean isFocusable() {
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {
        textArea.setEnabled(enabled);
    }

    @Override
    public void setFocus(boolean focused) {
        textArea.setFocus(focused);
    }

    @Override
    public String getStringValue() {
        if (textArea.getHTML().length() == 0)
            return null;
        return textArea.getHTML();
    }

    @Override
    public void setStringValue(String value) {
        if (value == null)
            textArea.setHTML("");
        else
            textArea.setHTML(value);
    }

}
