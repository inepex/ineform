package com.inepex.ineForm.client.places;

import java.util.Map;

import com.google.inject.Provider;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineFrame.client.page.InePage;

public class SelectorPlace extends ParamPlace {
	
	private final Provider<? extends InePage> provider;
	protected final String paramToken;	
	protected final String desciptorName;
	protected final FormContext formContext;
	
	protected String childToken;
	protected String newToken;
	protected RelationListAction relListAction;
	
	private InePage page;
	private SelectorWidget selectorWidget;
	
	public SelectorPlace(Provider<? extends InePage> provider, String paramToken, String desciptorName,
			FormContext formContext) {
		this.provider=provider;
		this.paramToken=paramToken;
		this.desciptorName=desciptorName;
		this.formContext=formContext;
	}
	
	public void setRelListAction(RelationListAction relListAction) {
		this.relListAction = relListAction;
	}
	
	public RelationListAction getRelListAction() {
		return relListAction;
	}

	@Override
	public boolean notifyParamChangedReturnIsParamSet(Map<String, String> urlParams) {
		return urlParams.get(paramToken)!=null && urlParams.get(paramToken).length()>0;
	}

	@Override
	final public InePage getAssociatedPage() {
		if(provider==null)
			return null;
		
		if(page==null)
			page = provider.get();
		
		return page;
	}
	
	@Override
	final public ParamPlaceWidget getSelectorWidget() {
		if(childToken==null)
			throw new RuntimeException("you may forgot to set childToken!");
			
		if(selectorWidget==null) {
			selectorWidget = new SelectorWidget(paramToken, desciptorName, childToken, this, formContext, newToken, relListAction);
		}
		
		return selectorWidget;
	}

	@Override
	final public String getChildToken() {
		return childToken;
	}
	
	final public SelectorPlace setChildToken(String childToken) {
		this.childToken = childToken;
		return this;
	}
	
	final public String getNewToken() {
		return newToken;
	}
	
	/**
	 * @param newToken - can be null, if not null a selector widget display a "New" button.. new place must be in the same level
	 */
	final public SelectorPlace setNewToken(String newToken) {
		this.newToken = newToken;
		return this;
	}

}
