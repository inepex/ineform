package com.inepex.ineForm.client.form.formunits;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.EventBus;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.shared.descriptorext.FormRDescBase;
import com.inepex.ineom.shared.descriptor.Node;

/**
 * DONT forget to invoke setSkin method
 * 
 */
public class UIBinderFormUnit extends AbstractFormUnit {

    private final ValueRangeProvider valueRangeProvider;
    private final EventBus eventBus;

    private Map<String, Node<FormRDescBase>> selectedFields;

    public UIBinderFormUnit(
        FormContext fromDeps,
        String objectDescriptorsName,
        List<Node<FormRDescBase>> selectedFields) {
        super(objectDescriptorsName, fromDeps.descStore);

        this.valueRangeProvider = fromDeps.valueRangeProvider;
        this.eventBus = fromDeps.eventBus;
        this.selectedFields = new HashMap<String, Node<FormRDescBase>>();
        for (Node<FormRDescBase> node : selectedFields) {
            this.selectedFields.put(node.getNodeId(), node);
        }
    }

    public void setSkin(UIBinderFormUnitSkin skin) {
        skin.renderSkin(this);
        selectedFields.clear();
        selectedFields = null;
        add(skin);
    }

    @Override
    public void setFWVisible(String key, boolean visible) {
        if (widgetsByKey.get(key) != null)
            widgetsByKey.get(key).setVisible(visible);
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public ValueRangeProvider getValueRangeProvider() {
        return valueRangeProvider;
    }

    public Map<String, Node<FormRDescBase>> getSelectedFields() {
        return selectedFields;
    }
}
