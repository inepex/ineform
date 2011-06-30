package com.inepex.ineom;

import org.junit.Assert;
import org.junit.Test;

import com.inepex.ineom.shared.descriptor.Node;


public class NodeTest {

	/**
	 * 
	 * DON'T forget that the id and the node element will be the same (not a copy) 
	 * 
	 */
	@Test
	public void nodeCopyTest() {
		Node<A> root = Node.createRootNode(new A(), A.class);
		
		root.addChildGC("A", new A())
				.addChild("a", new A())
				.addChild("b", new A())
				.addChildGC("c", new A())
					.addChild("1", new A())
					.addChild("2", new A())
					.addChild("3", new A())
					.getParent()
				.getParent()
			.getParent();
		
		
		Node<A> rootCopy = Node.createRootNode(new A(), A.class);
		Node.copy(rootCopy, root);
		
		check(root.getChildren().get(0), rootCopy.getChildren().get(0));
		
		//all right, plus one point ;)
		Assert.assertEquals(1, 1);
	}
	
	private void check(Node<A> node, Node<A> node2) {
		if(node.getNodeId()!=node2.getNodeId())
			Assert.fail("id check fault by node :"+node.getNodeId());
		
		if(node.getNodeElement()!=node2.getNodeElement())
			Assert.fail("node element check fault by node :"+node.getNodeId());
		
		if(node.getChildren()!=null && node2.getChildren()!=null) {
			if(node.getChildren()==null && node2.getChildren()==null 
					|| node.getChildren().size() != node2.getChildren().size()) {
				Assert.fail("children size check fault by node :"+node.getNodeId());
			}
			
			for(int i=0; i<node.getChildren().size(); i++) {
				check(node.getChildren().get(i), node2.getChildren().get(i));
			}
		}
	}

	
	
	private class A {
	}
}
