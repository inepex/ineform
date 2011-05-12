package com.inepex.ineForm.client.form.events;

import com.google.gwt.event.shared.EventHandler;
import com.inepex.ineom.shared.kvo.AssistedObject;

public class FilledWithDataEvent extends FormLifecycleEventBase<FilledWithDataEvent.Handler> {

	private static Type<FilledWithDataEvent.Handler> TYPE;

	public static interface Handler extends EventHandler {
		void onFilledWithData(FilledWithDataEvent event);
	}
	
	private final AssistedObject newData;

	public FilledWithDataEvent(AssistedObject newData) {
		this.newData=newData;
	}

	public static Type<FilledWithDataEvent.Handler> getType() {
		if (TYPE == null) {
			TYPE = new Type<FilledWithDataEvent.Handler>();
		}
		return TYPE;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
	    return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
	    handler.onFilledWithData(this);
	}

	public AssistedObject getNewData() {
		return newData;
	}
}
