package com.inepex.ineForm.client.form.widgets.customkvo;

import java.util.List;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;

public class CustomKVOFWReadOnlyView extends HandlerAwareComposite {

	private Grid grid = new Grid();
	
	List<CustomKVORow> rows;
	boolean showHeader;
	boolean showType;
	
	boolean rendered = false;
	
	@Inject
	public CustomKVOFWReadOnlyView() {
		initWidget(grid);
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		show();
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
		
		if (showHeader) createHeader(showType);
		
		int i = (showHeader) ? 1 : 0;
		for (CustomKVORow row : rows){
			showRow(i, row, showType);
			i++;
		}
		rendered = true;
	}
	
	private void createHeader(boolean showType) {
		grid.setText(0, 0, IneFormI18n.customKVO_key());
		if (showType){
			grid.setText(0, 1, IneFormI18n.customKVO_type());
			grid.setText(0, 2, IneFormI18n.customKVO_value());
		} else {
			grid.setText(0, 1, IneFormI18n.customKVO_value());	
		}
		
		grid.getCellFormatter().setStyleName(0, 0, ResourceHelper.getRes().style().customKVOHeader());
		if (showType){
			grid.getCellFormatter().setStyleName(0, 1, ResourceHelper.getRes().style().customKVOHeaderType());
			grid.getCellFormatter().setStyleName(0, 2, ResourceHelper.getRes().style().customKVOHeader());
		} else {
			grid.getCellFormatter().setStyleName(0, 1, ResourceHelper.getRes().style().customKVOHeader());	
		}
	}
	private void showRow(int rowNr, CustomKVORow row, boolean showType){
		grid.setText(rowNr, 0, row.getKey());
		if (showType) {
			grid.setText(rowNr, 1, row.getType().toString());
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
