package com.inepex.ineFrame.client.navigation.places;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;
import com.inepex.ineFrame.client.navigation.InePlace;

public abstract class ParamPlace extends InePlace {

	public abstract String getChildToken(); 
	
	public abstract boolean notifyParamChangedReturnIsParamSet(Map<String, String> urlParams);
	
	/**
	 * @return - null or a ParamPlaceWidget
	 */
	public abstract ParamPlaceWidget getSelectorWidget();
	
	public static interface ParamPlaceWidget extends IsWidget {
		public void realizeUrlParams(Map<String, String> params);
	}
}
