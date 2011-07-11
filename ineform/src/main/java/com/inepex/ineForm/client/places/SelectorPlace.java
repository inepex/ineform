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
	protected RelationListAction relListAction;
	
	private InePage page;
	private SelectorPresenter selectorPresenter;
	
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
	final public String getChildToken() {
		return childToken;
	}
	
	final public SelectorPlace setChildToken(String childToken) {
		this.childToken = childToken;
		return this;
	}

	@Override
	final public ParamPlacePresenter getSelectorPresenter() {
		if(childToken==null)
			throw new RuntimeException("you may forgot to set childToken!");
			
		if(selectorPresenter==null) {
			selectorPresenter = new SelectorPresenter(paramToken, desciptorName, childToken, this, formContext, relListAction);
		}
		
		return selectorPresenter;
	}

}
