package com.inepex.ineForm.shared.dispatch;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;

import com.inepex.ineom.shared.dispatch.interfaces.RelationList;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

/**
 * General action for searching when a {@link List} of {@link KeyValueObject} is returned.
 * Especially useful in {@link ServerSideTableModel}
 *
 * @author István Szoboszlai
 *
 */
public class RelationListAction extends AbstractSearchAction 
	implements Action<RelationListActionResult>, RelationList {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8669603502664601792L;

	public RelationListAction() {
	}

	public RelationListAction(String descriptorName, AssistedObject searchParameters,
			int firstResult, int numMaxResult, boolean queryResultCount){
		super(descriptorName, searchParameters, firstResult, numMaxResult, queryResultCount);
	}


}
