package com.inepex.ineForm.client.form.events;

import com.google.gwt.event.shared.EventHandler;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;

public class SavedEvent extends FormLifecycleEventBase<SavedEvent.Handler> {

	private static Type<SavedEvent.Handler> TYPE;

	public static interface Handler extends EventHandler {
		void onSaved(SavedEvent event);
	}

	public static Type<SavedEvent.Handler> getType() {
		if (TYPE == null) {
			TYPE = new Type<SavedEvent.Handler>();
		}
		return TYPE;
	}
	
	private ObjectManipulationResult objectManipulationResult;
	
	public SavedEvent() {
	}

	public SavedEvent(ObjectManipulationResult objectManipulationResult) {
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
