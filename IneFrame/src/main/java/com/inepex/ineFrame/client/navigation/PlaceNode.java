package com.inepex.ineFrame.client.navigation;

import com.inepex.ineom.shared.descriptor.Node;


public class PlaceNode extends Node<InePlace> {

	private static final long serialVersionUID = 1L;

	public static <H extends InePlace> PlaceNode createRootNode(H nodeElement){
		return new PlaceNode(true, nodeElement);
	}
	
	protected PlaceNode(boolean isRootNode, InePlace nodeElement) {
		super(isRootNode, nodeElement);
	}
	
	protected PlaceNode(InePlace nodeElement) {
		super(nodeElement);
	}
	
	public void setAllHierarchicalTokens() {
		setAllHierarchicalTokensRecursive(this.getRootNode());
	}
	
	private void setAllHierarchicalTokensRecursive(Node<InePlace> node) {
		if (!node.isRootNode()) {
			if (node.getHierarchicalId() != null)
				node.getNodeElement().setHierarchicalToken(node.getHierarchicalId());
		}
		
		if (node.hasChildren()) {
			for (Node<InePlace> childNode : node.getChildren()) {
				setAllHierarchicalTokensRecursive(childNode);
			}
		}
	}

}
