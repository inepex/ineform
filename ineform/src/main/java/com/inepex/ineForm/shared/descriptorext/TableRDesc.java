package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineom.shared.descriptor.Node;


public class TableRDesc extends TableRDescBase {
	
	private static final long serialVersionUID = -2624519376818046268L;

	private String objectDescriptorName;

	private Node<TableRDescBase> rootNode;
	
	public TableRDesc() {
	}
	
	public TableRDesc(String objectDescriptorName, String... props) {
		this.objectDescriptorName=objectDescriptorName;
		addProps(props);
		rootNode=Node.createRootNode(this, TableRDescBase.class);
	}

	public String getObjectDescriptorName() {
		return objectDescriptorName;
	}

	public Node<TableRDescBase> getRootNode() {
		return rootNode;
	}
}
