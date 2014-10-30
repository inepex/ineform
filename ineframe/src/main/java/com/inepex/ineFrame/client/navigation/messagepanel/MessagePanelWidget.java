package com.inepex.ineFrame.client.navigation.messagepanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.inepex.ineFrame.client.navigation.defaults.DefaultIneFrameMasterPageView;

public class MessagePanelWidget extends Grid implements MessagePanel {
	
	private Label messageLabel = new Label();
	private Label closeLabel = new Label("X");
	private Timer timer;
	private DefaultIneFrameMasterPageView parent;
	private HandlerRegistration closeHandlerReg;
	
	private boolean isInitialized = false;
	
	@Inject
	public MessagePanelWidget() {
		super(1, 3);
	}
	
	@Override
	public void showMessage(String message, boolean isError, int delayMillis){
		if(delayMillis > 0){
			timer = new Timer() {
				
				@Override
				public void run() {
					hideMessage();
					
				}
			}; 
			timer.schedule(delayMillis);	
		}
		if(isError){
			Image img = new Image(Res.INST.get().system_alert_icon());
			img.setStyleName(Res.INST.get().style().messageIcon());
			setWidget(0, 0, img);
			removeStyleName(Res.INST.get().style().messageStyle());
			addStyleName(Res.INST.get().style().errorStyle());
		}
		else{
			Image img = new Image(Res.INST.get().system_alert_icon());
			img.setStyleName(Res.INST.get().style().messageIcon());
			setWidget(0, 0, img);
			removeStyleName(Res.INST.get().style().errorStyle());
			addStyleName(Res.INST.get().style().messageStyle());
		}
		setVisible(true);
		if(messageLabel.getText().equals("")){
			messageLabel.setText(message);
		}else{
			messageLabel.setText(messageLabel.getText() + " | " + message);
		}
	}
	public double getHeight(){
		return Double.valueOf(getElement().getStyle().getHeight());
	}
	@Override
	public void hideMessage(){
		if(timer != null)
			timer.cancel();
		messageLabel.setText("");
		addStyleName(Res.INST.get().style().closed());
		//setVisible(false);
	}
	@Override
	protected void onAttach() {
		if (!isInitialized){
			setWidget(0, 1, messageLabel);
			setWidget(0, 2, closeLabel);
			setCellPadding(0);
			setCellSpacing(0);
			setBorderWidth(0);
			getColumnFormatter().setWidth(0, "1px");
			getColumnFormatter().setWidth(2, "1px");
			getCellFormatter().setStyleName(0, 2, Res.INST.get().style().messageCloseIcon());
			setStyleName(Res.INST.get().style().messagebox());
			isInitialized = true;
		}
		closeHandlerReg = closeLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				hideMessage();
				
			}
		});
		super.onAttach();
	}
	@Override
	protected void onDetach() {
		closeHandlerReg.removeHandler();
		super.onDetach();
	}

	@Override
	public int defaultDelay() {
		return 5000;
	}
	@Override
	public void showMessage(String message, boolean isError) {
		showMessage(message, isError, defaultDelay());
	}
	public void setParent(DefaultIneFrameMasterPageView parent) {
		this.parent = parent;
	}
	public DefaultIneFrameMasterPageView getParent() {
		return parent;
	}

}
