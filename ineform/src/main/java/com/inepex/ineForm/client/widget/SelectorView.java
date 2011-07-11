package com.inepex.ineForm.client.widget;

import java.util.ArrayList;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.inepex.ineForm.client.places.SelectorPresenter;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;


public class SelectorView extends HandlerAwareFlowPanel implements SelectorPresenter.View, HasSelectionHandlers<String>{
	

	private class ItemClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			int selected = elementOrder.indexOf(((Label)((SimplePanel)event.getSource()).getWidget()).getText());
			if(selected != -1 && selected != selectedIndex){
				selectedIndex = selected;
				fireSelectionEvent();
			}
			
		}
		
	}
	
	protected ItemClickHandler itemClickHandler;
	protected FlowPanel itemContainer;
	protected ArrayList<String> elementOrder;
	protected Panel nextButton;
	protected Panel previousButton;
	protected int selectedIndex;
	protected HandlerManager hm;

	public SelectorView() {
		super();
		elementOrder = new ArrayList<String>();
		itemClickHandler = new ItemClickHandler();
		hm = new HandlerManager(this);
		selectedIndex = -1;
		initLayout();
	}
	
	
	
	private void initLayout(){
		itemContainer = new FlowPanel();
		this.addStyleName(ResourceHelper.getRes().style().selector());
		previousButton = new FlowPanel() ;
		nextButton = new FlowPanel();
		previousButton.addStyleName(ResourceHelper.getRes().style().arrow());
		nextButton.addStyleName(ResourceHelper.getRes().style().arrow());	
		Image prev = new Image(ResourceHelper.getRes().selector_up_arrow());
		Image next = new Image(ResourceHelper.getRes().selector_down_arrow());
		previousButton.add(prev);
		nextButton.add(next);
		
		this.add(previousButton);
		this.add(itemContainer);
		this.add(nextButton);		
	}
	
	public void clear(){
		elementOrder.clear();
		itemContainer.clear();
		selectedIndex = -1;
	}
	
	private void fireSelectionEvent(){
		SelectionEvent.fire(this, elementOrder.get(selectedIndex));
	}
	
	@Override
	public void showLoading() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideLoading() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addItem(String name) {
		SimplePanel c = new SimplePanel();
		Label lbl = new Label(name);
		c.addStyleName(ResourceHelper.getRes().style().item());
		lbl.addStyleName(ResourceHelper.getRes().style().content());
		c.addDomHandler(itemClickHandler, ClickEvent.getType());
		c.setWidget(lbl);
		itemContainer.add(c);
	}

	@Override
	public void addNextClickHandler(ClickHandler h) {
		registerHandler(nextButton.addDomHandler(h, ClickEvent.getType()));
	}

	@Override
	public void addPreviousClickHandler(ClickHandler h) {
		registerHandler(previousButton.addDomHandler(h, ClickEvent.getType()));
	}

	@Override
	public void addItemSelectionHandler(SelectionHandler<String> h) {
		registerHandler(addSelectionHandler(h));
	}

	@Override
	public int getSelectedIndex() {
		return selectedIndex;
	}

	@Override
	public HandlerRegistration addSelectionHandler(
			SelectionHandler<String> handler) {
		return hm.addHandler(SelectionEvent.getType(),handler);
	}



	@Override
	public void setSelectedIndex() {
		// TODO Auto-generated method stub
		
	}


}
