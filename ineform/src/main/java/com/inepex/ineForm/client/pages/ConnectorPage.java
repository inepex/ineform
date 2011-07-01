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

	private final List<Connector> connectors = new ArrayList<ConnectorPage.Connector>();
	protected InePlace currentPlace;
	
	protected final FlowPanel mainPanel = new FlowPanel(); 
	
	private PageCallback pc;
	
	public ConnectorPage() {
		initWidget(mainPanel);
	}
	
	public ServerSideDataConnector createConnector(IneDispatch dispatcher, EventBus eventBus, String descriptorName) {
		Connector c = new Connector(dispatcher, eventBus, descriptorName);
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
				pc = new PageCallback(connectors.size(), callback);
				updateConnectors(true);
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
	
	protected void updateConnectors(boolean updateDisplays) {
		for(Connector c : connectors)
			c.update(updateDisplays);
	}
	
	private class PageCallback {

		private int size;
		private UrlParamsParsedCallback callback;
		
		public PageCallback(int size, UrlParamsParsedCallback callback) {
			this.size=size;
			this.callback=callback;
		}

		public void ready() {
			size--;
			if(size==0)
				callback.onUrlParamsParsed();
		}
		
	}
	
	private class Connector extends ServerSideDataConnector {

		public Connector(IneDispatch dispatcher, EventBus eventBus, String descriptorName) {
			super(dispatcher, eventBus, descriptorName);
		}

		@Override
		protected void updateDisplaysAndfireListChangedEvent() {
			if(pc!=null)
				pc.ready();
			
			super.updateDisplaysAndfireListChangedEvent();
		}
		
	}
}
