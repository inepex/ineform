package com.inepex.ineForm.shared.dispatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.customware.gwt.dispatch.shared.Action;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectList;

/**
 * General action for searching when a {@link List} of {@link KeyValueObject} is returned.
 * Especially useful in {@link ServerSideTableModel}
 *
 * @author István Szoboszlai
 *
 */
public class ObjectListAction extends AbstractSearchAction implements Action<ObjectListActionResult>, ObjectList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5238110902341753891L;

	private List<String> propGroups = new ArrayList<String>();
	
	private Long executingUser;
	
	public ObjectListAction() {
	}

	public ObjectListAction(String descriptorName) {
		setDescriptorName(descriptorName);
		setDefaults();
	}
	
	private void setDefaults(){
		queryResultCount = false;
		numMaxResult = 1000;
		firstResult = 0;
	}
	
	public ObjectListAction(String descriptorName, List<String> propGroups) {
		setDescriptorName(descriptorName); 
		this.propGroups = propGroups;
		setDefaults();
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

	@Override
	public void setPropGroups(String... propGroups) {
		this.propGroups = Arrays.asList(propGroups);
	}

	@Override
	public List<String> getPropGroups() {
		return propGroups;
	}

	public Long getExecutingUser() {
		return executingUser;
	}

	public void setExecutingUser(Long executingUser) {
		this.executingUser = executingUser;
	}

}
