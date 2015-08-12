package com.inepex.ineForm.client.form.prop;

import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.PropFDesc;

public class PropReadOnlyFW extends PropFWBase {

    private final PropFWReadOnlyView readOnlyView = new PropFWReadOnlyView();

    public PropReadOnlyFW(PropFDesc fieldDescriptor, WidgetRDesc widgetRDesc) {
        super(fieldDescriptor, widgetRDesc);
        initWidget(readOnlyView);
    }

    @Override
    protected void beforeParsed() {
        readOnlyView.clear();
    }

    @Override
    protected void onParsed() {
        readOnlyView.init(rows, isShowHeader, isShowType);
        readOnlyView.show();
    }

    @Override
    public boolean isReadOnlyWidget() {
        return true;
    }

}
