package com.inepex.ineForm.shared.customkvoeditor;

import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFW;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

/**
 * for {@link CustomKVOFW} and {@link CustomKVOReader} to get the object descriptor of displayed kvo!!!
 */
public interface CustomOdFinder {
	
	public void getCustomOd(Long id, OdFoundCallback callback);
	public void getCustomOdWithData(Long id, OdAndDataFoundCallback callback);
	
	public static interface OdFoundCallback{
		public void onFound(ObjectDesc od);
	}
	
	public static interface OdAndDataFoundCallback{
		public void onFound(ObjectDesc od, AssistedObject kvo);
	}
}
