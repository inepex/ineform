package com.inepex.ineFrame.client.async;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;

public class FullscreenStatusIndicator extends Composite implements
	AsyncStatusIndicator  {

	private static FullscreenStatusIndicatorUiBinder uiBinder = GWT
			.create(FullscreenStatusIndicatorUiBinder.class);

	interface FullscreenStatusIndicatorUiBinder extends
			UiBinder<Widget, FullscreenStatusIndicator> {
	}
	
	@UiField
	FlowPanel centerPanel;
	
	private HTML generalLoadingMessage = null;
	
	private int requestInProgressCounter = 0;
	
	private int unconfirmedErrorCount = 0;
 
	public FullscreenStatusIndicator() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public void onAsyncRequestStarted(String loadingMessage) {
		if (generalLoadingMessage == null) {
			generalLoadingMessage = new HTML("<h1>" + IneFrameI18n.loading() + "</h1>");
			centerPanel.add(generalLoadingMessage);
			generalLoadingMessage.setVisible(false);
		}
		
		if (requestInProgressCounter == 0) {
			RootPanel.get().add(this);
		}
				
		generalLoadingMessage.setVisible(true);
		
		requestInProgressCounter++;
	}

	@Override
	public void onGeneralFailure(String errorMessage) {
		requestInProgressCounter--;
		unconfirmedErrorCount++;
		centerPanel.add(getErrorWidget(errorMessage));
		updateVisiblityStates();
	}
	
	@Override
	public void onForbidden(String forbiddenMessage) {
		
	}

	@Override
	public void onSuccess(String successMessage) {
		requestInProgressCounter--;
		updateVisiblityStates();
	}
	
	private Widget getErrorWidget(String errorMsg) {
		final FlowPanel fp = new FlowPanel();
		Button confirmButton = new Button(IneFrameI18n.OK());
		confirmButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				fp.removeFromParent();
				unconfirmedErrorCount--;
				updateVisiblityStates();
			}
		});

		fp.add(new InlineHTML("<h2>" + errorMsg + "</h2>"));
		fp.add(confirmButton);
		
		return fp;
	}
	
	private void updateVisiblityStates() {
		if (requestInProgressCounter == 0) {
			generalLoadingMessage.setVisible(false);
			if(unconfirmedErrorCount == 0) {
				this.removeFromParent();
			}
		}
	}

	@Override
	public void onUnAuthenticated() {
	}

}
