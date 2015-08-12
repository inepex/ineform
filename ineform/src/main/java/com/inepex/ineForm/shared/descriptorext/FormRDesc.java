package com.inepex.ineForm.shared.descriptorext;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.descriptor.Node;

public class FormRDesc extends FormRDescBase implements Serializable, IsSerializable {

    private static final long serialVersionUID = -5591579072781758641L;

    private String objectDescriptorName;
    private Node<FormRDescBase> rootNode;

    public FormRDesc() {
        rootNode = Node.createRootNode(this, FormRDescBase.class);
    }

    public FormRDesc(String objectDescriptorName, String... props) {
        this.objectDescriptorName = objectDescriptorName;
        addProps(props);
        rootNode = Node.createRootNode(this, FormRDescBase.class);
    }

    public String getObjectDescriptorName() {
        return objectDescriptorName;
    }

    public void setObjectDescriptorName(String objectDescriptorName) {
        this.objectDescriptorName = objectDescriptorName;
    }

    public Node<FormRDescBase> getRootNode() {
        return rootNode;
    }

}
