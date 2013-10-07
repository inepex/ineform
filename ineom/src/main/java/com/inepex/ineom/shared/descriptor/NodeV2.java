package com.inepex.ineom.shared.descriptor;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.LazyArrayList;

/**
 * @author Istvan Szoboszlai
 *	Base class for building a hierarchy of any Elements of the type T
 * @param <T> base generic type of this node
 * 
 */
@SuppressWarnings("serial")
public class NodeV2<T> implements Serializable, IsSerializable {
	
	private boolean isRootNode = false;
	private NodeV2<T> parent;
	
	private List<NodeV2<T>> children = new LazyArrayList<NodeV2<T>>();
	
	private T element = null;
	
//------------------------- Constructors
//--------------------------------------------------------------------
	
	protected NodeV2(boolean isRootNode, T element){
		this.isRootNode = isRootNode;
		this.element=element;
	}
	
	/**
	 * it's parent will generate the node's id, when you add to it as child
	 */
	private NodeV2(T nodeElement) {
		this(false, nodeElement);
	}

//------------------------- adding child
//--------------------------------------------------------------------
	
	private NodeV2<T> addChildPrivate(NodeV2<T> node) {
		children.add(node);
		node.parent = this;
		return this;
	}
	
	
	private NodeV2<T> addChildGCPrivate(NodeV2<T> node) {
		addChildPrivate(node);
		return node;
	}

	public NodeV2<T> addChild(T childElement) {
		return addChildPrivate(new NodeV2<T>(childElement));
	}
	
	public NodeV2<T> addChildGC(T childElement) {
		return addChildGCPrivate(new NodeV2<T>(childElement));
	}
	
//------------------------- getters
//--------------------------------------------------------------------

	/**
	 * Gets level of depth in the nodetree.
	 * Level "0" means rootnode.
	 *
	 * @return level of depth
	 */
	public int getLevel() {
		NodeV2<T> cursor = this;

		int level = 0;
		while ((cursor = cursor.getParent()) != null) {
			level ++;
		}

		return level;
	}
	
	public NodeV2<T> getRootNode(){
		NodeV2<T> cursor = this;
		while (!cursor.isRootNode) {
			cursor = cursor.getParent();
		}
		return cursor;
	}

	public NodeV2<T> getParent() {
		return parent;
	}
	
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public List<NodeV2<T>> getChildren() {
		return Collections.unmodifiableList(children);
	}

	public T getElement() {
		return element;
	}

	public boolean isRootNode() {
		return isRootNode;
	}
}
