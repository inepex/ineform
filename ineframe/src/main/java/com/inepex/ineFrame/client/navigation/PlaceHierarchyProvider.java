package com.inepex.ineFrame.client.navigation;


/**
 * Interface for providing Place Hierarchy for IneFrame
 * @author istvanszoboszlai
 *
 */
public interface PlaceHierarchyProvider {
	/**
	 * Override this function to provide place hierarchy to IneFrame.
	 */
	void createPlaceHierarchy();
	PlaceNode getPlaceRoot();
}
