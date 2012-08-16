package com.inepex.ineFrame.shared;

import net.customware.gwt.dispatch.shared.Action;

import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;

/**
 * 
 * for getting {@link CustomKVOObjectDesc}
 * 
 */
public class CustomObjectDescAction implements Action<CustomObjectDescResult>{

	private Long id;
	private boolean kvoNeed;
	
	public CustomObjectDescAction() {
	}
	
	public CustomObjectDescAction(Long id, boolean kvoNeed) {
		this.id=id;
		this.kvoNeed=kvoNeed;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isKvoNeed() {
		return kvoNeed;
	}
	
	public void setKvoNeed(boolean kvoNeed) {
		this.kvoNeed = kvoNeed;
	}
	
}
