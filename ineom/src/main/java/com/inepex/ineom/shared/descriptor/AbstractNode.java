package com.inepex.ineom.shared.descriptor;


import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.LazyArrayList;

/**
 * @author Istvan Szoboszlai
 * 
 *	Base class for building a hierarchy of any Elements of the type T
 * @param <T> base generic type of this node
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractNode<T, S extends AbstractNode<T, S>> implements Serializable, IsSerializable {
	
	private final boolean isRootNode;
	private final AbstractNode<T,S> parent;
	private final List<AbstractNode<T, S>> children;
	private final T element;
	
	/**
	 * for root element
	 */
	protected AbstractNode(T element){
		this(true, element, null);
	}
	
	/**
	 * for child element
	 */
	protected AbstractNode(T nodeElement, AbstractNode<T, S> parent) {
		this(false, nodeElement, parent);
	}
	
	private AbstractNode(boolean isRootNode, T element, AbstractNode<T, S> parent){
		this.isRootNode = isRootNode;
		this.element=element;
		this.children= new LazyArrayList<AbstractNode<T, S>>();
		this.parent = parent;
	}
	
	protected abstract S createChild(T childElement);

	
	@SuppressWarnings("unchecked")
	private S addChildPrivate(AbstractNode<T, S> node) {
		children.add(node);
		return (S) this;
	}

	public final S addChild(T childElement) {
		return addChildPrivate(createChild(childElement));
	}
	
	
	public final S addChildGC(T childElement) {
		S child = createChild(childElement);
		addChildPrivate(child);
		return child;
	}

	/**
	 * Gets level of depth in the nodetree.
	 * Level "0" means rootnode.
	 *
	 * @return level of depth
	 */
	public final int getLevel() {
		AbstractNode<T, ?> cursor = this;

		int level = 0;
		while ((cursor = cursor.getParent()) != null) {
			level ++;
		}

		return level;
	}
	
	@SuppressWarnings("unchecked")
	public final S getRootNode(){
		AbstractNode<T, ?> cursor = this;
		while (!cursor.isRootNode) {
			cursor = cursor.getParent();
		}
		return (S) cursor;
	}

	@SuppressWarnings("unchecked")
	public final S getParent() {
		return (S) parent;
	}
	
	public final boolean hasChildren() {
		return !children.isEmpty();
	}

	@SuppressWarnings("unchecked")
	public final List<S> getChildren() {
		return Collections.unmodifiableList((List<S>)children);
	}

	public final T getElement() {
		return element;
	}

	public final boolean isRootNode() {
		return isRootNode;
	}
	
	/**
	 * @return iterator over: this?, parent, parent of parent, ...
	 */
	public final Iterator<S> parentLineIterator(final boolean putThisInto) {
		return new Iterator<S>() {

			private AbstractNode<T, ?> cursor = putThisInto ? AbstractNode.this : parent;
			
			@Override
			public boolean hasNext() {
				return cursor!=null;
			}

			@Override
			@SuppressWarnings("unchecked")
			public S next() {
				S ret = (S) cursor;
				cursor = cursor.parent;
				return ret;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
		
	}
}
