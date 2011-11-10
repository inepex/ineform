package com.inepex.ineForm.client.table;

import com.google.gwt.event.shared.EventBus;
import com.inepex.ineForm.client.datamanipulator.events.KeyValueObjectListModifiedEvent;
import com.inepex.ineForm.client.datamanipulator.events.KeyValueObjectListModifiedEventHandler;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

/**
 * IneTable with auto-reload on KeyValueObjectListModifiedEvents
 *
 */
public class IneTableWithHandler extends IneTable {
		
	
	private class ListModifiedHandler implements KeyValueObjectListModifiedEventHandler {
		@Override
		public void onObjectListModified(KeyValueObjectListModifiedEvent event) {
			if (event.getListType().equals(objectDescriptorName)) {
			}
		}
	}
	
	private final EventBus eventBus;
	
	public IneTableWithHandler(DescriptorStore descStore,
			EventBus eventBus,
			String objectDescriptorName,
			TableRDesc tableRenderDescriptor,
			IneDataConnector dataConnector,
			AssistedObjectTableFieldRenderer fieldRenderer) {
		super(descStore, objectDescriptorName, tableRenderDescriptor, dataConnector, fieldRenderer);
		this.eventBus = eventBus;
	}
	
	@Override
	protected void onAttach() {
		//TODO getting eventBus from defaultEventBus not a good idea, instead should get from constructor
		registerHandler(eventBus.addHandler(KeyValueObjectListModifiedEvent.TYPE, new ListModifiedHandler()));
		super.onAttach();
	}

}
