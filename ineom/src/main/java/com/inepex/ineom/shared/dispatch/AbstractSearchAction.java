package com.inepex.ineom.shared.dispatch;

import java.io.Serializable;
import java.util.List;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

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
	
	int firstResult;
	int numMaxResult;
	boolean queryResultCount;

	String orderKey = null;
	boolean descending = false;
	
	public AbstractSearchAction() {
	}

	public AbstractSearchAction(String descriptorName,
			int firstResult, int numMaxResult, boolean queryResultCount){
		this(descriptorName, new KeyValueObject(), firstResult, numMaxResult, queryResultCount);
	}
	
	public AbstractSearchAction(String descriptorName, AssistedObject searchParameters,
			int firstResult, int numMaxResult, boolean queryResultCount){
		this.descriptorName = descriptorName;
		this.searchParameters = searchParameters;
		this.firstResult = firstResult;
		this.numMaxResult = numMaxResult;
		this.queryResultCount = queryResultCount;
	}

	public AssistedObject getSearchParameters() {
		return searchParameters;
	}
	public int getFirstResult() {
		return firstResult;
	}
	public int getNumMaxResult() {
		return numMaxResult;
	}
	
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public void setNumMaxResult(int numMaxResult) {
		this.numMaxResult = numMaxResult;
	}

	public String getOrderKey() {
		return orderKey;
	}
	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	public boolean isDescending() {
		return descending;
	}
	public void setDescending(boolean descending) {
		this.descending = descending;
	}

	public boolean isQueryResultCount() {
		return queryResultCount;
	}

	public void setQueryResultCount(boolean queryResultCount) {
		this.queryResultCount = queryResultCount;
	}

	public String getDescriptorName() {
		return descriptorName;
	}

	public void setDescriptorName(String descriptorName) {
		this.descriptorName = descriptorName;
	}

	public void setSearchParameters(AssistedObject searchParameters) {
		this.searchParameters = searchParameters;
	}
}
