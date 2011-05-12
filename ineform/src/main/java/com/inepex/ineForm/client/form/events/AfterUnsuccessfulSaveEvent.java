package com.inepex.ineForm.client.form.events;

import com.google.gwt.event.shared.EventHandler;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;

public class AfterUnsuccessfulSaveEvent extends FormLifecycleEventBase<AfterUnsuccessfulSaveEvent.Handler> {

	private static Type<AfterUnsuccessfulSaveEvent.Handler> TYPE;

	public static interface Handler extends EventHandler {
		void onSaved(AfterUnsuccessfulSaveEvent event);
	}

	public static Type<AfterUnsuccessfulSaveEvent.Handler> getType() {
		if (TYPE == null) {
			TYPE = new Type<AfterUnsuccessfulSaveEvent.Handler>();
		}
		return TYPE;
	}
	
	private ObjectManipulationResult objectManipulationResult;
	
	public AfterUnsuccessfulSaveEvent() {
	}

	public AfterUnsuccessfulSaveEvent(ObjectManipulationResult objectManipulationResult) {
		super();
		this.objectManipulationResult = objectManipulationResult;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
	    return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
	    handler.onSaved(this);
	}

	public ObjectManipulationResult getObjectManipulationResult() {
		return objectManipulationResult;
	}

	public void setObjectManipulationResult(
			ObjectManipulationResult objectManipulationResult) {
		this.objectManipulationResult = objectManipulationResult;
	}
	
	

}
