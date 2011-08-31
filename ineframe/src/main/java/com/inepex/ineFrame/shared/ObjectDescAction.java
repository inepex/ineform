package com.inepex.ineFrame.shared;

import net.customware.gwt.dispatch.shared.Action;

/**
 * 
 * for getting custom object descriptors
 * 
 */
public class ObjectDescAction implements Action<ObjectDescResult>{

	private Long id;
	
	public ObjectDescAction() {
	}
	
	public ObjectDescAction(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
