package com.inepex.ineForm.client.form.events;

import com.google.gwt.event.shared.EventHandler;

public class BeforeCancelEvent extends FormLifecycleEventBase<BeforeCancelEvent.Handler> {

	private static Type<BeforeCancelEvent.Handler> TYPE;

	public static interface Handler extends EventHandler {
		void onBeforeCancel(BeforeCancelEvent event);
	}

	public static Type<BeforeCancelEvent.Handler> getType() {
		if (TYPE == null) {
			TYPE = new Type<BeforeCancelEvent.Handler>();
		}
		return TYPE;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
	    return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
	    handler.onBeforeCancel(this);
	}

}
