package com.inepex.ineForm.client.form.widgets.customkvo;

import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;


public class CustomKVOReadOnlyFW extends CustomKVOFWBase {

	private final CustomKVOFWReadOnlyView readOnlyView = new CustomKVOFWReadOnlyView();
	
	public CustomKVOReadOnlyFW(RelationFDesc fieldDescriptor, WidgetRDesc widgetRDesc, OdFinder odFinder) {
		super(fieldDescriptor, widgetRDesc, odFinder);
		initWidget(readOnlyView);
	}

	@Override
	protected void beforeRelationParsed() {
		readOnlyView.clear();
	}

	@Override
	protected void onRelationParsed() {
		readOnlyView.init(rows, isShowHeader, isShowType);
		readOnlyView.show();
	}
	
	@Override
	public boolean isReadOnlyWidget() {
		return true;
	}

}
