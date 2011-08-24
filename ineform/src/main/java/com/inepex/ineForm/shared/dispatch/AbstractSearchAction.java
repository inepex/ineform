package com.inepex.ineForm.shared.dispatch;

import java.io.Serializable;
import java.util.List;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;

/**
 * General action for searching when a {@link List} of {@link KeyValueObject} is returned.
 * Especially useful in {@link ServerSideTableModel}
 *
 * @author István Szoboszlai
 *
 */
public class AbstractSearchAction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4108044012222527271L;
	
	
	String descriptorName;
	AssistedObject searchParameters;
	
	Integer firstResult;
	Integer numMaxResult;
	Boolean queryResultCount;

	String orderKey = null;
	Boolean descending = false;
	
	public AbstractSearchAction() {
	}

	public AbstractSearchAction(String descriptorName, String searchDescriptorName,
			int firstResult, int numMaxResult, boolean queryResultCount){
		this(descriptorName, new KeyValueObject(searchDescriptorName), firstResult, numMaxResult, queryResultCount);
	}
	
	public AbstractSearchAction(String descriptorName, AssistedObject searchParameters,
			int firstResult, int numMaxResult, boolean queryResultCount){
		this.descriptorName = descriptorName;
		this.searchParameters = searchParameters;
		this.firstResult = firstResult;
		this.numMaxResult = numMaxResult;
		this.queryResultCount = queryResultCount;
	}

	public String getDescriptorName() {
		return descriptorName;
	}

	public void setDescriptorName(String descriptorName) {
		this.descriptorName = descriptorName;
	}

	public AssistedObject getSearchParameters() {
		return searchParameters;
	}

	public void setSearchParameters(AssistedObject searchParameters) {
		this.searchParameters = searchParameters;
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getNumMaxResult() {
		return numMaxResult;
	}

	public void setNumMaxResult(Integer numMaxResult) {
		this.numMaxResult = numMaxResult;
	}

	public Boolean isQueryResultCount() {
		return queryResultCount;
	}

	public void setQueryResultCount(Boolean queryResultCount) {
		this.queryResultCount = queryResultCount;
	}

	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	public Boolean isDescending() {
		return descending;
	}

	public void setDescending(Boolean descending) {
		this.descending = descending;
	}


}
