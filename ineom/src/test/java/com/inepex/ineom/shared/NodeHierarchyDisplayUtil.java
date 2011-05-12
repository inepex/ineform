package com.inepex.ineom.shared;
import com.inepex.ineom.shared.descriptor.Node;


public class NodeHierarchyDisplayUtil {

	public static void drawNodeHierarchy(Node<?> rootNode) {
		drawNode(0, rootNode);
	}
	
	private static void drawNode(int level, Node<?> node) {
		System.out.print(level+"\t");
		
		for(int i=0; i<level; i++) {
			System.out.print("  ");
		}
		
		if(node.getNodeId()!=null)
			System.out.println(node.getNodeId());
		else 
			System.out.println("?anonym node?");
		
		if(node.getChildren()!=null) {
			for(Node<?> child : node.getChildren()) {
				drawNode(level+1, child);
			}
		}
	}
}
