package com.inepex.ineFrame.client.navigation.places;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;
import com.inepex.ineFrame.client.navigation.InePlace;

public abstract class ParamPlace extends InePlace {

	public abstract String getChildToken(); 
	
	/**
	 * 
	 * @param urlParams
	 * @return false if params aren't correctly set. In this case associated page will be displayed.
	 */
	public abstract boolean notifyParamChangedReturnIsParamSet(Map<String, String> urlParams);
	
	/**
	 * @return - null or a ParamPlaceWidget
	 */
	public abstract ParamPlacePresenter getSelectorPresenter();
	
	public static interface ParamPlacePresenter extends IsWidget{
		public void realizeUrlParams(Map<String, String> params);
	}
}
