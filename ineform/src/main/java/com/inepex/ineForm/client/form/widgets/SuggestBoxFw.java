package com.inepex.ineForm.client.form.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.datamanipulator.ValueRangeResultCallback;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;

public class SuggestBoxFw extends DenyingFormWidget {

    private final Map<String, Relation> stringToRelation = new HashMap<String, Relation>();
    private final Map<Long, Relation> idToRelation = new HashMap<Long, Relation>();

    private ValueRangeProvider valueRangeProvider;

    private MultiWordSuggestOracle suggestOracle = new MultiWordSuggestOracle();
    private SuggestBox suggestBox = new SuggestBox(suggestOracle);

    Relation value;

    boolean isFwEnabled = true;

    public SuggestBoxFw(FormContext formContext, FDesc fielddescriptor) {
        super(fielddescriptor);
        if (!(fieldDescriptor instanceof RelationFDesc))
            throw new IllegalArgumentException();

        initWidget(suggestBox);
        this.valueRangeProvider = formContext.valueRangeProvider;
        loadDataFromValueRangeProvider();

    }

    private void loadDataFromValueRangeProvider() {
        if (valueRangeProvider != null) {
            suggestBox.getTextBox().setEnabled(false);
            valueRangeProvider
                .getRelationValueRange(fieldDescriptor, new ValueRangeResultCallback() {
                    @Override
                    public void onValueRangeResultReady(List<Relation> relationList) {
                        if (relationList != null) {
                            if (isFwEnabled)
                                suggestBox.getTextBox().setEnabled(true);
                            setValueRange(relationList);
                        }
                    }
                });
        }
    }

    public void setValueRange(List<Relation> valueRange) {
        suggestOracle.clear();
        stringToRelation.clear();

        if (valueRange != null) {
            for (Relation relation : valueRange) {
                suggestOracle.add(relation.getDisplayName());
                stringToRelation.put(relation.getDisplayName(), relation);
                idToRelation.put(relation.getId(), relation);
            }
        }
        if (value != null && idToRelation.containsKey(value.getId())) {
            suggestBox.setText(idToRelation.get(value.getId()).getDisplayName());
        }
    }

    @Override
    public Relation getRelationValue() {
        if (!suggestBox.getText().equals("")
            && stringToRelation.containsKey(suggestBox.getText())) {
            return stringToRelation.get(suggestBox.getText());
        } else
            return null;
    }

    @Override
    public void setRelationValue(Relation value) {
        if (value != null)
            this.value = value;

        if (value != null && idToRelation.containsKey(value.getId())) {
            suggestBox.setText(idToRelation.get(value.getId()).getDisplayName());
        }
    }

    @Override
    public boolean handlesRelation() {
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {
        isFwEnabled = enabled;
        suggestBox.getTextBox().setEnabled(enabled);
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        registerHandler(suggestBox.addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                SuggestBoxFw.this.fireFormWidgetChanged();
            }
        }));
    }

}
