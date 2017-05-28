package com.inepex.ineom.shared.descriptor;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Istvan Szoboszlai Base class for building a hierarchy of any Elements
 *         of the type T
 * @param <T>
 *            base generic type of this node
 * 
 */
public class Node<T> implements Serializable, IsSerializable {

    private static final long serialVersionUID = 7055245403110164008L;

    public static final String ID_SEPARATOR = "/";

    private boolean isRootNode = false;
    private Node<T> parent;

    private int defChildNodeIdCounter = 0;
    private List<Node<T>> children;

    private boolean hasDefaultId = false;
    private T nodeElement = null;
    private String nodeId = null;

    // ------------------------- Constructors
    // --------------------------------------------------------------------
    /**
     * This static method can access the private constructor that creates the
     * rootnode. There can only be one rootNode.
     * 
     * @return
     */
    public static <H, T extends H> Node<H> createRootNode(T nodeElement, Class<H> baseClass) {
        return new Node<H>(true, nodeElement);
    }

    protected Node() {}

    protected Node(boolean isRootNode, T nodeElement) {
        this.isRootNode = isRootNode;
        this.nodeElement = nodeElement;
    }

    /**
     * it's parent will generate the node's id, when you add to it as child
     */
    protected Node(T nodeElement) {
        this(false, nodeElement);
    }

    // ------------------------- adding child
    // --------------------------------------------------------------------

    private Node<T> addChildPrivate(String nodeName, Node<T> node) {
        if (children == null)
            children = new ArrayList<Node<T>>();

        children.add(node);
        node.parent = this;
        if (nodeName != null) {
            node.nodeId = nodeName;
        } else {
            node.nodeId = "" + defChildNodeIdCounter++;
            node.hasDefaultId = true;
        }

        return this;
    }

    private Node<T> addChildGCPrivate(String nodeName, Node<T> node) {
        addChildPrivate(nodeName, node);
        return node;
    }

    public Node<T> addChild(String nodeName, T nodeElement) {
        return addChildPrivate(nodeName, new Node<T>(nodeElement));
    }

    public Node<T> addChild(T nodeElement) {
        return addChildPrivate(null, new Node<T>(nodeElement));
    }

    public Node<T> addChildGC(T nodeElement) {
        return addChildGCPrivate(null, new Node<T>(nodeElement));
    }

    public Node<T> addChildGC(String nodeName, T nodeElement) {

        return addChildGCPrivate(nodeName, new Node<T>(nodeElement));
    }

    // ------------------------- node methods
    // --------------------------------------------------------------------

    public String getHierarchicalId() {
        if (getNodeId() == null)
            return null;

        StringBuffer token = new StringBuffer();

        // token surely contains the part that represents this very node
        token.append(this.getNodeId());

        // Build the token hierarchically from parents
        Node<T> parent = this;
        while ((parent = parent.getParent()) != null && !parent.isRootNode) {
            token.insert(0, ID_SEPARATOR);
            token.insert(0, parent.getNodeId());
        }

        return token.toString();
    }

    public Node<T> getRootNode() {
        Node<T> parent = this;
        while (!parent.isRootNode) {
            parent = parent.getParent();
        }
        return parent;
    }

    /**
     * Searches for a node in the tree by a given <b>hierarchical id</b> .
     *
     * @param hierarchicalId
     *            the complete token pointing to the Node requested
     * @return The found Node if exists of null
     */
    public Node<T> findNodeByHierarchicalId(String hierarchicalId) {
        // Obviously we can't search for null string or empty string
        if (hierarchicalId == null || hierarchicalId.trim().equals(""))
            return null;

        String[] tokenParts = hierarchicalId.split("[" + ID_SEPARATOR + "]");

        // Find the node by scanning on each level
        Node<T> cursorNode = getRootNode();
        for (int i = 0; i < tokenParts.length; i++) {
            if (cursorNode == null || cursorNode.getChildren() == null)
                return null;

            for (Node<T> node : cursorNode.getChildren()) {
                if (tokenParts[i].equals(node.getNodeId())) {
                    cursorNode = node;
                    break;
                }
            }
        }

        if (cursorNode == null || cursorNode.getHierarchicalId() == null)
            return null;

        String computedId = cursorNode.getHierarchicalId();
        if (!computedId.equals(hierarchicalId)) {
            return null;
        }

        return cursorNode;
    }

    public Node<T> findNodeById(String id) {
        if (id != null && id.equals(getNodeId()))
            return this;
        if (!hasChildren())
            return null;
        for (Node<T> childNode : getChildren()) {
            Node<T> foundNode = childNode.findNodeById(id);
            if (foundNode != null)
                return foundNode;
        }
        return null;
    }

    public Collection<String> getKeysUnderNode() {
        Set<String> idList = new HashSet<String>();
        addChildIdsRecursve(idList, this);
        return idList;
    }

    private Collection<String> addChildIdsRecursve(Set<String> idList, Node<T> parent) {
        if (!parent.hasChildren())
            return idList;
        for (Node<T> childNode : parent.getChildren()) {
            idList.add(childNode.getNodeId());
            addChildIdsRecursve(idList, childNode);
        }
        return idList;
    }

    // ------------------------- getters and setters
    // --------------------------------------------------------------------

    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    public boolean hasDefaultId() {
        return hasDefaultId;
    }

    /**
     * Gets level of depth in the nodetree. Level "0" means rootnode.
     *
     * @return level of depth
     */
    public int getLevel() {
        Node<T> cursor = this;

        int level = 0;
        while ((cursor = cursor.getParent()) != null) {
            level++;
        }

        return level;
    }

    public Node<T> getParent() {
        return parent;
    }

    public String getNodeId() {
        return nodeId;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public T getNodeElement() {
        return nodeElement;
    }

    public void setNodeElement(T nodeElement) {
        this.nodeElement = nodeElement;
    }

    public boolean isRootNode() {
        return isRootNode;
    }

    public Node<T> dummy() {
        return this;
    }

    public Node<T> removeChild(String id) {
        if (getChildren() == null)
            return this;
        int indexToRemove = -1;
        for (int i = 0; i < getChildren().size(); i++) {
            if (getChildren().get(i).getNodeId().equals(id)) {
                indexToRemove = i;
                break;
            }
        }
        if (indexToRemove != -1)
            getChildren().remove(indexToRemove);
        return this;
    }

}
