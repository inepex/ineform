package com.inepex.ineForm.client.form.events;

import com.google.gwt.event.shared.EventHandler;

public class DeletedEvent extends FormLifecycleEventBase<DeletedEvent.Handler> {

	private static Type<DeletedEvent.Handler> TYPE;

	public static interface Handler extends EventHandler {
		void onSaved(DeletedEvent event);
	}

	public static Type<DeletedEvent.Handler> getType() {
		if (TYPE == null) {
			TYPE = new Type<DeletedEvent.Handler>();
		}
		return TYPE;
	}
	
	private Long id;
	
	public DeletedEvent() {
	}
	
	public DeletedEvent(Long id) {
		super();
		this.id = id;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
	    return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
	    handler.onSaved(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}