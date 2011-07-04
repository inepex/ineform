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
	private final String desciptorName;
	private final FormContext formContext;
	
	private InePage page;
	private SelectorWidget selectorWidget;
	
	public SelectorPlace(Provider<? extends InePage> provider, String paramToken, String desciptorName, String childToken, FormContext formContext) {
		this.provider=provider;
		this.paramToken=paramToken;
		this.desciptorName=desciptorName;
		this.childToken=childToken;
		this.formContext=formContext;
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
			selectorWidget = new SelectorWidget(paramToken, desciptorName, childToken, getHierarchicalToken(), formContext);
		}
		
		return selectorWidget;
	}

	@Override
	public String getChildToken() {
		return childToken;
	}

}
