package com.inepex.ineForm.client.form.widgets.customkvo;

import com.inepex.ineom.shared.descriptor.ObjectDesc;

/**
 * 
 * for CustomKvoFW to get the object descriptor of displayed kvo !!!
 */
public interface OdFinder {
	
	public void getCustomOd(Long id, OdFoundCallback callback);
	
	public static interface OdFoundCallback{
		public void onFound(ObjectDesc od);
	}
}
