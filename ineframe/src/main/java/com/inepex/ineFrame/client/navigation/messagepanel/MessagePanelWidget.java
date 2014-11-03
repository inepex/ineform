package com.inepex.ineFrame.client.navigation.messagepanel;

import java.util.LinkedList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineFrame.client.navigation.defaults.DefaultIneFrameMasterPage;
import com.inepex.ineFrame.client.util.DesignConstants;

public class MessagePanelWidget extends Grid implements MessagePanel {
	
	private LinkedList<Message> messages;
	private InlineLabel messageLabel = new InlineLabel();
	private Label closeLabel = new Label("X");
	private FlowPanel imageAndMessagePanel = new FlowPanel();
	private Timer timer;
	private HandlerRegistration closeHandlerReg;
	private boolean isShowing;
	
	private boolean isInitialized = false;
	private Provider<DefaultIneFrameMasterPage.View> masterPageView;
	
	@Inject
	public MessagePanelWidget(Provider<DefaultIneFrameMasterPage.View> masterPageView) {
		super(1, 2);
		isShowing = false;
		messages = new LinkedList<>();
		this.masterPageView = masterPageView;
	}
	
	@Override
	public void showMessage(String message, boolean isError, int delayMillis){
		if(isShowing){
			messages.addLast(new Message(message, isError, delayMillis));
			return;
		}else{
			displayMessage(message, isError, delayMillis);
		}
	}
	public void displayMessage(Message message){
		displayMessage(message.getMessage(), message.isError(), message.getDelayMillisec());
	}
	public void displayMessage(String message, boolean isError, int delayMillis){
		isShowing = true;
		imageAndMessagePanel.clear();
		messageLabel.setText("");
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
//			setWidget(0, 0, img);
			imageAndMessagePanel.add(img);
			removeStyleName(Res.INST.get().style().messageStyle());
			addStyleName(Res.INST.get().style().errorStyle());
		}
		else{
			Image img = new Image(Res.INST.get().system_alert_icon());
			img.setStyleName(Res.INST.get().style().messageIcon());
//			setWidget(0, 0, img);
			imageAndMessagePanel.add(img);
			removeStyleName(Res.INST.get().style().errorStyle());
			addStyleName(Res.INST.get().style().messageStyle());
		}
		if(messageLabel.getText().equals("")){
			messageLabel.setText(message);
		}else{
			messageLabel.setText(messageLabel.getText() + " | " + message);
		}
		
		imageAndMessagePanel.add(messageLabel);
		setWidget(0, 0, imageAndMessagePanel);
		
		masterPageView.get().showMessagePanel();
	}
	public double getHeight(){
		return Double.valueOf(getElement().getStyle().getHeight());
	}
	@Override
	public void hideMessage(){
		if(timer != null)
			timer.cancel();
		masterPageView.get().hideMessagePanel();
		new Timer(){
			@Override
			public void run() {
				if(messages.size()>0){
					displayMessage(messages.removeFirst());
				}else{
					isShowing = false;
				}					
			}
		}.schedule((int)(DesignConstants.defaultAnimationLength*1000));
		
	}
	@Override
	protected void onAttach() {
		if (!isInitialized){
			imageAndMessagePanel.add(messageLabel);
			setWidget(0, 0, imageAndMessagePanel);
			setWidget(0, 1, closeLabel);
			setCellPadding(0);
			setCellSpacing(0);
			setBorderWidth(0);
//			getColumnFormatter().setWidth(0, "1px");
//			getColumnFormatter().setWidth(1, "1px");
			getCellFormatter().setStyleName(0, 1, Res.INST.get().style().messageCloseIcon());
			addStyleName(Res.INST.get().style().messagebox());
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

	public void showMessage(Message message) {
		showMessage(message.getMessage(), message.isError(), message.getDelayMillisec());
	}
}
