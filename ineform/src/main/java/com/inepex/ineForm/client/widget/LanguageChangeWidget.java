package com.inepex.ineForm.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.inject.Inject;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.inei18n.client.I18nStore_Client;
import com.inepex.inei18n.shared.ChangeLanguageAction;
import com.inepex.inei18n.shared.SimpleResult;

public class LanguageChangeWidget extends HandlerAwareComposite {

	final Anchor currentLangLabel = new Anchor();
	
	IneDispatch ineDispatch;
	
	@Inject
	public LanguageChangeWidget(IneDispatch ineDispatch) {
		this.ineDispatch = ineDispatch;
		setLabelPropsForCurrentLang();
		initWidget(currentLangLabel);
	}
	
	public void setLabelPropsForCurrentLang() {
		if ("en".equals(I18nStore_Client.currentLanguage))
			currentLangLabel.setText("Magyar");
		else
			currentLangLabel.setText("English");
	}
	
	@Override
	protected void onAttach() {
		currentLangLabel.addClickHandler(new LangChangeClickHandler());
		super.onAttach();
	}
	
	private class LangChangeClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			String requestedLang = "en".equals(I18nStore_Client.currentLanguage) ? "hu" : "en";
			ChangeLanguageAction action = new ChangeLanguageAction(requestedLang);
			ineDispatch.execute(action, new LangChgCallback());
		}
	}
	
	private class LangChgCallback extends SuccessCallback<SimpleResult> {
		@Override
		public void onSuccess(SimpleResult result) {
			Window.Location.reload();
		}
	}
	
	
}
