package com.inepex.ineForm.client.form.events;

import com.google.gwt.event.shared.EventHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class BeforeSaveEvent extends FormLifecycleEventBase<BeforeSaveEvent.Handler> {

	private static Type<BeforeSaveEvent.Handler> TYPE;

	public static interface Handler extends EventHandler {
		void onBeforeSave(BeforeSaveEvent event);
	}

	public static Type<BeforeSaveEvent.Handler> getType() {
		if (TYPE == null) {
			TYPE = new Type<BeforeSaveEvent.Handler>();
		}
		return TYPE;
	}
	
	private AssistedObject kvo;
	
	public BeforeSaveEvent() {
	}

	public BeforeSaveEvent(AssistedObject kvo) {
		super();
		this.kvo = kvo;
	}



	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
	    return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
	    handler.onBeforeSave(this);
	}

	public AssistedObject getKvo() {
		return kvo;
	}

	public void setKvo(AssistedObject kvo) {
		this.kvo = kvo;
	}
	
	

}
