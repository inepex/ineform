package com.inepex.ineForm.shared;


public abstract class IneformThreeAsyncCallback <First, Second, Third> {

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
	
	private IneformAsyncCallback<Third> thirdCallback = new IneformAsyncCallback<Third>() {

		@Override
		public void onResponse(Third response) {
			setThirdResponseAndCheckReady(response);
		}
	};
	
	private First firstResponse;
	private Second secondResponse;
	private Third thirdResponse;
	
	private synchronized void setFirstResponseAndCheckReady(First firstResponse){
		this.firstResponse = firstResponse;
		if (secondResponse != null && thirdResponse != null) onResponse();
	}
	
	private synchronized void setSecondResponseAndCheckReady(Second secondResponse){
		this.secondResponse = secondResponse;
		if (firstResponse != null && thirdResponse != null) onResponse();
	}
	
	private synchronized void setThirdResponseAndCheckReady(Third thirdResponse){
		this.thirdResponse = thirdResponse;
		if (firstResponse != null && secondResponse != null) onResponse();
	}
	
	private void onResponse(){
		 onResponse(this.firstResponse, this.secondResponse, this.thirdResponse);
	}
	
	public IneformAsyncCallback<First> getFirstCallback(){
		return firstCallback;
	}
	
	public IneformAsyncCallback<Second> getSecondCallback(){
		return secondCallback;
	}
	
	public IneformAsyncCallback<Third> getThirdCallback(){
		return thirdCallback;
	}
	
	
	public abstract void onResponse(First firstResponse, Second secondResponse, Third thirdResponse);
}
