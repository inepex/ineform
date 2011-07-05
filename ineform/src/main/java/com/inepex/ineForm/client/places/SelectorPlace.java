package com.inepex.ineForm.client.places;

import java.util.Map;

import com.google.inject.Provider;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineFrame.client.page.InePage;

public class SelectorPlace extends ParamPlace {
	
	private final Provider<? extends InePage> provider;
	private final String paramToken;
	private final String childToken;
	private final String newToken;
	private final String desciptorName;
	private final FormContext formContext;
	
	private InePage page;
	private SelectorWidget selectorWidget;
	
	/**
	 * 
	 * @param provider - can be null
	 * @param paramToken
	 * @param desciptorName
	 * @param childToken - redirect to child when param is set
	 * @param formContext
	 * @param newToken - can be null, if not null a selector widget display a "New" button.. new place must be in the same level 
	 * 
	 */
	public SelectorPlace(Provider<? extends InePage> provider, String paramToken, String desciptorName,
			String childToken, FormContext formContext, String newToken) {
		this.provider=provider;
		this.paramToken=paramToken;
		this.desciptorName=desciptorName;
		this.childToken=childToken;
		this.formContext=formContext;
		this.newToken=newToken;
	}
	
	@Override
	public InePage getAssociatedPage() {
		if(provider==null)
			return null;
		
		if(page==null)
			page = provider.get();
		
		return page;
	}

	@Override
	public boolean isParamSet(Map<String, String> urlParams) {
		return urlParams.get(paramToken)!=null && urlParams.get(paramToken).length()>0;
	}

	@Override
	public ParamPlaceWidget getSelectorWidget() {
		if(selectorWidget==null) {
			selectorWidget = new SelectorWidget(paramToken, desciptorName, childToken, this, formContext, newToken);
		}
		
		return selectorWidget;
	}

	@Override
	public String getChildToken() {
		return childToken;
	}

}
