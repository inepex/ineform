package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * resources and style for this package (only this package!)
 * @author SoTi
 *
 */
interface Res extends ClientBundle {
	
	public static class INST {
		static Res INST;
		public static Res get() {
			if (INST == null) {
				INST = GWT.create(Res.class);
				INST.style().ensureInjected();
				INST.ineMenuStyle().ensureInjected();
			}
			return INST;
		}
	}	
	
	@Source("DefaultIneFrameMasterPageViewStyle.css")
	DefaultIneFrameMasterPageStyle style();
	
	@Source("IneMenuStyle.css")
	IneMenuStyle ineMenuStyle();
	
	
	ImageResource drawerAccountSettings();
	
	ImageResource drawerHelpCenter();
	
	ImageResource drawerLogout();
	
	ImageResource drawerProfile();
}
