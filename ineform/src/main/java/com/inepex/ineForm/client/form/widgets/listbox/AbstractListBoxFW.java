package com.inepex.ineForm.client.form.widgets.listbox;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineForm.client.general.IneListbox;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class AbstractListBoxFW extends DenyingFormWidget {
    public static class Params {
        public static String notSelectedText = "notSelectedText";
    }

    private final IneListbox listBox = new IneListbox();

    public final static String DEFAULT_notSelectedText = "--";

    protected String notSelectedText = DEFAULT_notSelectedText;

    protected boolean allowsNull = true;

    protected WidgetRDesc wrDesc;

    public AbstractListBoxFW(FDesc fieldDesc, WidgetRDesc wrDesc) {
        super(fieldDesc);
        this.wrDesc = wrDesc;

        initWidget(listBox.asWidget());

        setAllowsNull(fieldDesc.isNullable());
        if (wrDesc.hasProp(Params.notSelectedText)) {
            notSelectedText = wrDesc.getPropValue(Params.notSelectedText);
        }
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        registerHandler(listBox.getListBox().addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                fireFormWidgetChanged(true);
            }
        }));
    }

    public void setAllowsNull(boolean allowsNull) {
        this.allowsNull = allowsNull;
        if (allowsNull)
            addNullItem();
    }

    protected void addNullItem() {
        listBox.getListBox().addItem(notSelectedText, notSelectedText);
    }

    @Override
    public void setEnabled(boolean enabled) {
        listBox.getListBox().setEnabled(enabled);
    }

    @Override
    public void setFocus(boolean focused) {
        listBox.getListBox().setFocus(focused);
    }

    public ListBox getListBox() {
        return listBox.getListBox();
    }

}
