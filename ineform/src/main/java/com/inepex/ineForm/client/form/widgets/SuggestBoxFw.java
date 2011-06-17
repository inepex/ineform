package com.inepex.ineForm.client.form.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.datamanipulator.ValueRangeResultCallback;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.kvo.Relation;

public class SuggestBoxFw extends DenyingFormWidget {

	private final Map<String, Relation> stringToRelation = new HashMap<String, Relation>();
	private final Map<Long, Relation> idToRelation = new HashMap<Long, Relation>();

	private ValueRangeProvider valueRangeProvider;
	private RelationFDesc relationFDesc = null;

	MultiWordSuggestOracle suggestOracle = new MultiWordSuggestOracle();
	SuggestBox suggestBox = new SuggestBox(suggestOracle);

	Relation value;

	public SuggestBoxFw(FormContext formContext, FDesc fielddescriptor) {
		super(fielddescriptor);
		initWidget(suggestBox);
		this.valueRangeProvider = formContext.valueRangeProvider;
		relationFDesc = (RelationFDesc) fieldDescriptor;
		loadDataFromValueRangeProvider();

	}

	private void loadDataFromValueRangeProvider() {
		if (valueRangeProvider != null) {
			suggestBox.getTextBox().setEnabled(false);
			valueRangeProvider.getRelationValueRange(fieldDescriptor, new ValueRangeResultCallback() {
				@Override
				public void onValueRangeResultReady(List<Relation> relationList) {
					if (relationList != null) {
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
		if (!suggestBox.getText().equals("") && stringToRelation.containsKey(suggestBox.getText())) {
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

}
