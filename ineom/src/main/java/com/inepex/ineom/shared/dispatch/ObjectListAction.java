package com.inepex.ineom.shared.dispatch;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

/**
 * General action for searching when a {@link List} of {@link KeyValueObject} is returned.
 * Especially useful in {@link ServerSideTableModel}
 *
 * @author István Szoboszlai
 *
 */
public class ObjectListAction extends AbstractSearchAction implements Action<ObjectListResult> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5238110902341753891L;

	public ObjectListAction() {
	}

	public ObjectListAction(String descriptorName) {
		setDescriptorName(descriptorName); 
	}
	
	/**
	 * query with firstResult = 0, numMaxResult = 1000, queryResultCount = false;
	 * @param descriptorName
	 * @param searchParameters
	 */
	public ObjectListAction(String descriptorName, AssistedObject searchParameters){
		this(descriptorName, searchParameters, 0, 1000, false);
		
	}

	public ObjectListAction(String descriptorName, AssistedObject searchParameters,
			int firstResult, int numMaxResult, boolean queryResultCount){
		super(descriptorName, searchParameters, firstResult, numMaxResult, queryResultCount);
	}



}
