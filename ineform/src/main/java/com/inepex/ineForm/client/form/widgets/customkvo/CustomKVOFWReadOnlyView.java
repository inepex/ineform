package com.inepex.ineForm.client.form.widgets.customkvo;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.types.ODFieldType;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;

public class CustomKVOFWReadOnlyView extends HandlerAwareComposite {

	private final Grid grid = new Grid();
	
	private List<CustomKVORow> rows = new ArrayList<CustomKVORow>();
	private boolean showHeader = true;
	private boolean showType = false;
	private boolean rendered = false;
	
	@Inject
	public CustomKVOFWReadOnlyView() {
		initWidget(grid);
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		show();
	}
	
	public void clear(){
		grid.clear(true);
		grid.resize(0, 0);
	}
	
	public void init(List<CustomKVORow> rows, boolean showHeader, boolean showType){
		this.rows = rows;
		this.showHeader = showHeader;
		this.showType = showType;
		rendered = false;
	}
	
	public void show(){
		if (rendered) return;
		int rowNr= rows.size();
		if (showHeader) rowNr++;
		int columns = 2;
		if (showType) columns++;
		grid.clear();
		grid.resize(rowNr, columns);
		
		if (showHeader) createHeader();
		
		int i = (showHeader) ? 1 : 0;
		for (CustomKVORow row : rows){
			showRow(i, row);
			i++;
		}
		rendered = true;
	}
	
	private void createHeader() {
		grid.setWidget(0, 0, new Label(IneFormI18n.customKVO_key()));
		if (showType){
			grid.setWidget(0, 1, new Label(IneFormI18n.customKVO_type()));
			grid.setWidget(0, 2, new Label(IneFormI18n.customKVO_value()));
		} else {
			grid.setWidget(0, 1, new Label(IneFormI18n.customKVO_value()));	
		}
		
		grid.getCellFormatter().setStyleName(0, 0, ResourceHelper.ineformRes().style().customKVOHeaderRO());
		if (showType){
			grid.getCellFormatter().setStyleName(0, 1, ResourceHelper.ineformRes().style().customKVOHeaderTypeRO());
			grid.getCellFormatter().setStyleName(0, 2, ResourceHelper.ineformRes().style().customKVOHeaderRO());
		} else {
			grid.getCellFormatter().setStyleName(0, 1, ResourceHelper.ineformRes().style().customKVOHeaderRO());	
		}
	}
	private void showRow(int rowNr, CustomKVORow row){
		grid.setText(rowNr, 0, row.getKey());
		if (showType) {
			grid.setText(rowNr, 1, ODFieldType.getODFieldTypeName(row.getType()));
			grid.setText(rowNr, 2, row.getValue());
		} else {
			grid.setText(rowNr, 1, row.getValue());
		}
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}


}
