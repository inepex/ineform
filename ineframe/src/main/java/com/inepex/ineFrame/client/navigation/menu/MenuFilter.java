package com.inepex.ineFrame.client.navigation.menu;

import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineom.shared.descriptor.Node;

public interface MenuFilter {

	public boolean filter(Node<InePlace> node);
	
}
