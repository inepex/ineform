package com.inepex.ineom.shared.descriptor;

@SuppressWarnings("serial")
public class IdNode extends AbstractNode<Long, IdNode>{

	public static IdNode createRoot(Long element) {
		return new IdNode(element);
	}
	
	private IdNode(Long element) {
		super(element);
	}

	private IdNode(Long nodeElement, AbstractNode<Long,IdNode> parent) {
		super(nodeElement, parent);
	}

	@Override
	protected IdNode createChild(Long childElement) {
		return new IdNode(childElement, this);
	}

}
