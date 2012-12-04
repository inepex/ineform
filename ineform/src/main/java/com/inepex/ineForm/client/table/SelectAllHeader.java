package com.inepex.ineForm.client.table;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasValue;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

/**
 * @author David Mason
 * @author sebi
 */
public class SelectAllHeader extends Header<Boolean> implements HasValue<Boolean> {

	private boolean checked;
	private HandlerManager handlerManager;
	private IneTable table;

	public SelectAllHeader(IneTable table) {
		super(new CheckboxCell());
		this.table=table;
	}

	@Override
	public Boolean getValue() {
		return checked;
	}

	@Override
	public void onBrowserEvent(Context context, Element elem,
			NativeEvent nativeEvent) {
		int eventType = Event.as(nativeEvent).getTypeInt();
		if (eventType == Event.ONCHANGE) {
			nativeEvent.preventDefault();
			setValue(!checked, true);
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Boolean> handler) {
		return ensureHandlerManager().addHandler(ValueChangeEvent.getType(),
				handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		ensureHandlerManager().fireEvent(event);
	}

	@Override
	public void setValue(Boolean value) {
		setValue(value, false);
	}

	@Override
	public void setValue(Boolean value, boolean fireEvents) {
		checked = value;
		table.redrawHeaders();
		
		for(AssistedObject ao : table.getCellTable().getVisibleItems())
			table.getSelectionModel().setSelected(ao, value);
		
		if (fireEvents) {
			ValueChangeEvent.fire(this, value);
		}
	}

	private HandlerManager ensureHandlerManager() {
		if (handlerManager == null) {
			handlerManager = new HandlerManager(this);
		}
		return handlerManager;
	}
}