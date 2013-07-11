package com.inepex.ineForm.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.DataGrid.Style;

public class IneDataGridResources implements DataGrid.Resources{

	DataGridStyleAdapter adapter;
	DataGrid.Resources origRes;
	
	public IneDataGridResources() {
		adapter= new DataGridStyleAdapter();
		origRes = GWT.create(DataGrid.Resources.class);
	}
	
	@Override
	public ImageResource dataGridLoading() {
		return origRes.dataGridLoading();
	}

	@Override
	public ImageResource dataGridSortAscending() {
		return origRes.dataGridSortAscending();
	}

	@Override
	public ImageResource dataGridSortDescending() {
		return origRes.dataGridSortDescending();
	}

	@Override
	public Style dataGridStyle() {
		return adapter;
	}

}
