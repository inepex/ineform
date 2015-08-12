package com.inepex.ineFrame.client.navigation;

import java.util.List;

/**
 * Interface for providing Place Hierarchy for IneFrame
 * 
 * @author istvanszoboszlai
 *
 */
public interface PlaceHierarchyProvider {
    /**
     * Override this function to provide place hierarchy to IneFrame.
     */
    void createPlaceHierarchy();

    PlaceNode getPlaceRoot();

    /**
     * these tokens select the current root place, null means placeRoot
     */
    List<String> getCurrentMenuRoot();
}
