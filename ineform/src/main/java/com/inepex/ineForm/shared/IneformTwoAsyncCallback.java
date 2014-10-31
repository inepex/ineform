package com.inepex.ineForm.shared;

import com.inepex.ineFrame.shared.IneformAsyncCallback;


public abstract class IneformTwoAsyncCallback <First, Second> {

	private IneformAsyncCallback<First> firstCallback = new IneformAsyncCallback<First>() {

		@Override
		public void onResponse(First response) {
			setFirstResponseAndCheckReady(response);
		}
	};
	
	private IneformAsyncCallback<Second> secondCallback = new IneformAsyncCallback<Second>() {

		@Override
		public void onResponse(Second response) {
			setSecondResponseAndCheckReady(response);
		}
	};
	
	private First firstResponse;
	private Second secondResponse;
	
	private synchronized void setFirstResponseAndCheckReady(First firstResponse){
		this.firstResponse = firstResponse;
		if (secondResponse != null) onResponse(this.firstResponse, this.secondResponse);
	}
	
	private synchronized void setSecondResponseAndCheckReady(Second secondResponse){
		this.secondResponse = secondResponse;
		if (firstResponse != null) onResponse(this.firstResponse, this.secondResponse);
	}
	
	public IneformAsyncCallback<First> getFirstCallback(){
		return firstCallback;
	}
	
	public IneformAsyncCallback<Second> getSecondCallback(){
		return secondCallback;
	}
	
	
	public abstract void onResponse(First firstResponse, Second secondResponse);
}
