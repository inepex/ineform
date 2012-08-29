package com.inepex.ineForm.client.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.page.InePage;


/**
 * 
 * ConnectorPage
 * -----------------
 * 
 * the abstract page for manage server-side connectors automatically
 * 
 * while every page loading:
 * 	-the connectors will be updated automatically
 * 	-waiting for UI refresh
 * 	-after all displays the page content by invoking onUrlParamsParsed()
 * 
 * NOTICE! for creating managed connectors use the createConnector(..) method
 */
public abstract class ConnectorPage extends HandlerAwareComposite implements InePage {

	private final List<ServerSideDataConnector> connectors = new ArrayList<ServerSideDataConnector>();
	protected InePlace currentPlace;
	
	protected final FlowPanel mainPanel = new FlowPanel(); 
	
	public ConnectorPage() {
		initWidget(mainPanel);
	}
	
	public ServerSideDataConnector createConnector(IneDispatch dispatcher, EventBus eventBus, String descriptorName) {
		ServerSideDataConnector c = new ServerSideDataConnector(dispatcher, eventBus, descriptorName) {
			@Override
			public void update(boolean updateDisplays) {
				//nothing to do page manages the updating
			}
		};
		connectors.add(c);
		return c;
	}
	
	@Override
	public void setCurrentPlace(InePlace place) {
		currentPlace = place;
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}
	
	@Override
	public void onShow() {
	}
	
	@Override
	final public void setUrlParameters(Map<String, String> urlParams,
			UrlParamsParsedCallback callback) throws Exception {
		
		if(affectUrlParameters(urlParams)) {
			if(connectors.size()<1) {
				callback.onUrlParamsParsed();
			} else {
				updateConnectors(true, new PageCallback(connectors.size(), callback));
			}
		}
	}
	
	/**
	 * 
	 * @param urlParams
	 * @return true if parameters are correct
	 * 			false when should skip the updating and invonking the callback
	 */
	protected boolean affectUrlParameters(Map<String, String> urlParams) {
		return true;
	}
	
	protected void updateConnectors(boolean updateDisplays, PageCallback pageCallback) {
		for(ServerSideDataConnector c : connectors)
			c.update(updateDisplays, pageCallback);
	}
	
	private class PageCallback implements ServerSideDataConnector.DataConnectorReadyCallback{

		private int size;
		private UrlParamsParsedCallback callback;
		
		public PageCallback(int size, UrlParamsParsedCallback callback) {
			this.size=size;
			this.callback=callback;
		}

		@Override
		public void ready() {
			size--;
			if(size==0)
				callback.onUrlParamsParsed();
		}
		
	}
}
