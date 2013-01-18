package com.inepex.ineFrame.client.navigation;

import java.util.List;
import java.util.Map;

public class PlaceToken {
	
	private String token;
	
	public PlaceToken(String token) {
		this.token = token;
	}
	
	public PlaceToken appendChild(String childToken){
		token = PlaceHandlerHelper.appendChild(token, childToken);
		return this;
	}
	
	public PlaceToken appendParam(String name, String value){
		token = PlaceHandlerHelper.appendParam(token, name, value);
		return this;
	}
	
	public PlaceToken appendParam(String name, List<String> value) {
		token = PlaceHandlerHelper.appendParam(token, name, value);
		return this;
	}
	
	public Map<String, String> getUrlParameters(){
		return PlaceHandlerHelper.getUrlParameters(token);
	}
	
	public PlaceToken createParentLevelMenuToken(){
		token =  PlaceHandlerHelper.createParentLevelMenuToken(token);
		return this;
	}
	
	public PlaceToken createSameLevelMenuToken(String... subMenuTokens) {
		token = PlaceHandlerHelper.createSameLevelMenuToken(token, subMenuTokens);
		return this;
		
	}
	
	public String getPlacePart(String currentFullToken) {
		return PlaceHandlerHelper.getPlacePart(currentFullToken);
	}
	
	public String getToken(){
		return token;
	}
}
