package com.inepex.ineFrame.client.navigation;


/**
 * Provides simple base class for {@link PlaceHierarchyProvider}
 * @author istvanszoboszlai
 *
 */
public abstract class DefaultPlaceHierarchyProvider implements PlaceHierarchyProvider {
	
	public static final String SETTINGS = "settings";

	protected PlaceNode placeRoot;

	public DefaultPlaceHierarchyProvider() {
		placeRoot = PlaceNode.createRootNode(new RootPlace());
	}
	
	@Override
	public PlaceNode getPlaceRoot() {
		return placeRoot;
	}

}
