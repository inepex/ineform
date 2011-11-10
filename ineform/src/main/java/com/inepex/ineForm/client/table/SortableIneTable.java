package com.inepex.ineForm.client.table;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.user.cellview.client.Header;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class SortableIneTable extends IneTable {

	private String orderKey;
	private boolean descending = false;

	public SortableIneTable(DescriptorStore descStore,
			String objectDescriptorName,
			IneDataConnector dataProvider,
			AssistedObjectTableFieldRenderer fieldRenderer) {
		super(descStore, objectDescriptorName, dataProvider, fieldRenderer);
	}
	
	public SortableIneTable(DescriptorStore descriptorStore,
			String objectDescName, String tableRenderDescriptor,
			IneDataConnector connector, 
			AssistedObjectTableFieldRenderer fieldRenderer) {
		super(descriptorStore, objectDescName, tableRenderDescriptor, connector, fieldRenderer);
	}


	@Override
	protected Header<String> createHeader(
			boolean sortable
			, String text
			, final String key
			, boolean defaultSort
			, boolean defaultSortReverse) {
		
		if (!sortable) return new CustomTextHeader(text);
		
		final SortableHeader header = new SortableHeader(text);
		if (defaultSort) {
			orderKey = key;
			header.setSorted(true);
			header.setReverseSort(defaultSortReverse);
			dataConnector.setOrderKey(orderKey);
			dataConnector.setOrderDescending(defaultSortReverse);
		}
		header.setUpdater(new ValueUpdater<String>() {
			public void update(String value) {
				header.setSorted(true);
				header.toggleReverseSort();

				for (Header<String> otherHeader : headers.values()) {
					if (otherHeader instanceof SortableHeader) {
						if (otherHeader != header ){
							((SortableHeader) otherHeader).setSorted(false);
							((SortableHeader) otherHeader).setReverseSort(true);
						}
					}

				}
				cellTable.redrawHeaders();

				// Request sorted rows.
				orderKey = key;
				descending = header.getReverseSort();

				// Go to the first page of the newly-sorted results
				if(pager!=null) pager.firstPage();
				dataConnector.setOrderKey(orderKey);
				dataConnector.setOrderDescending(descending);
				dataConnector.update(true);
			}
		});
		return header;
	}

	public String getOrderKey() {
		return orderKey;
	}
	
	public boolean isDescending() {
		return descending;
	}
	
}
