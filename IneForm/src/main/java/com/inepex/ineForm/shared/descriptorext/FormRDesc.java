package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineom.shared.descriptor.Node;

public class FormRDesc extends FormRDescBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5591579072781758641L;
	
	private final String objectDescriptorName;
	private final Node<FormRDescBase> rootNode;
	
	public FormRDesc(String objectDescriptorName, String... props) {
		this.objectDescriptorName=objectDescriptorName;
		addProps(props);
		rootNode=Node.createRootNode(this, FormRDescBase.class);
	}

	public String getObjectDescriptorName() {
		return objectDescriptorName;
	}

	public Node<FormRDescBase> getRootNode() {
		return rootNode;
	}
	
	
}
