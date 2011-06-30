package com.inepex.ineFrame.client.navigation;

import java.util.TreeMap;

import com.inepex.ineom.shared.descriptor.Node;


/**
 * Provides simple base class for {@link PlaceHierarchyProvider}
 * @author istvanszoboszlai
 *
 */
public abstract class DefaultPlaceHierarchyProvider implements PlaceHierarchyProvider {
	
	private TreeMap<String, PlaceNode> placeNodeByRealHierarchicalId;

	/**
	 * the real root of static tree
	 */
	protected PlaceNode realRoot;

	public DefaultPlaceHierarchyProvider() {
		realRoot = PlaceNode.createRootNode(new RootPlace());
	}
	
	/**
	 * create a root-copy of selected sub-tree (cached)
	 * 
	 * in a real application there are an administrator subtree, an public subtree, an user subtree....
	 */
	protected PlaceNode createCurrentRootCached(Node<InePlace> place) {
		if(placeNodeByRealHierarchicalId==null){
			placeNodeByRealHierarchicalId=new TreeMap<String, PlaceNode>();
		}
		
		PlaceNode pn = placeNodeByRealHierarchicalId.get(place.getHierarchicalId());
		
		if(pn==null) {
			pn  = PlaceNode.createRootNode(new RootPlace());
			Node.copy(pn, place);
			placeNodeByRealHierarchicalId.put(place.getHierarchicalId(), pn);			
		}
		
		return pn;
	}
}
