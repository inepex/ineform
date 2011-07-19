package com.inepex.ineForm.client.widget;

import java.util.ArrayList;
import java.util.TreeMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.inepex.ineForm.client.places.SelectorPresenter;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;


public class SelectorView extends FlowPanel implements SelectorPresenter.View{
	

	private class InnerClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if(event.getSource().equals(nextButton)){
				scrollDown();
				for(int i=0;i<hmDown.getHandlerCount(ClickEvent.getType());i++){
					hmDown.getHandler(ClickEvent.getType(), 0).onClick(event);
				}
			}
			else if(event.getSource().equals(previousButton)){
				scrollUp();
				for(int i=0;i<hmUp.getHandlerCount(ClickEvent.getType());i++){
					hmUp.getHandler(ClickEvent.getType(), 0).onClick(event);
				}
			}
			else{
				int selected = elementOrder.indexOf(((Label)((SimplePanel)event.getSource()).getWidget()).getText());
				if(selected != -1 && selected != selectedIndex){
					selectItem(elementOrder.get(selected));
					for(int i=0;i<hm.getHandlerCount(ClickEvent.getType());i++){
						hm.getHandler(ClickEvent.getType(), 0).onClick(event);
					}
				}
			}
		}
	}
	
	protected InnerClickHandler innerClickHandler;
	protected FlowPanel itemContainer;
	protected ArrayList<String> elementOrder;
	protected Panel nextButton;
	protected Panel previousButton;
	protected int selectedIndex;
	protected HandlerManager hm, hmUp, hmDown;
	protected int visibleItemCount;
	protected int firstVisibleItem;
	protected int scrollStep;
	protected TreeMap<String,Panel> items;

	public SelectorView() {
		super();
		elementOrder = new ArrayList<String>();
		items = new TreeMap<String, Panel>();
		innerClickHandler = new InnerClickHandler();
		hm = new HandlerManager(this);
		hmUp = new HandlerManager(this);
		hmDown = new HandlerManager(this);
		selectedIndex = -1;
		visibleItemCount = 7;
		firstVisibleItem = 0;
		scrollStep = 1;
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
		
		previousButton.addDomHandler(innerClickHandler, ClickEvent.getType());
		nextButton.addDomHandler(innerClickHandler, ClickEvent.getType());
		
		this.add(previousButton);
		this.add(itemContainer);
		this.add(nextButton);		
	}
	
	public void clear(){
		elementOrder.clear();
		itemContainer.clear();
		selectedIndex = -1;
		firstVisibleItem = 0;
		items.clear();
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
		elementOrder.add(name);
		items.put(name, createSimpleItem(name));
		if(isItemVisible(name))
			update();
	}
	
	private Panel createSimpleItem(String name){
		SimplePanel c = new SimplePanel();
		Label lbl = new Label(name);
		c.addStyleName(ResourceHelper.getRes().style().item());
		lbl.addStyleName(ResourceHelper.getRes().style().content());
		c.addDomHandler(innerClickHandler, ClickEvent.getType());
		c.setWidget(lbl);
		return c;
	}
	
	private boolean isItemVisible(String name){
		int i = elementOrder.indexOf(name);
		if(i >= firstVisibleItem && i <= firstVisibleItem + visibleItemCount)
			return true;
		else 
			return false;
	}
	
	private void update(){
		itemContainer.clear();
		for(int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount && i < elementOrder.size(); i++){
			Panel item = items.get(elementOrder.get(i));
//			if(item == null){
//				item = createSimpleItem(elementOrder.get(i));
//				items.put(elementOrder.get(i), item);
//			}
			itemContainer.add(item);				
		}
	}

	@Override
	public void addNextClickHandler(ClickHandler h) {
		hmDown.addHandler(ClickEvent.getType(),h);
	}

	@Override
	public void addPreviousClickHandler(ClickHandler h) {
		hmUp.addHandler(ClickEvent.getType(),h);
	}

	@Override
	public void addItemSelectedClickHandler(ClickHandler h) {
		hm.addHandler(ClickEvent.getType(),h);
	}

	@Override
	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	public String getSelectedItem(){
		if(selectedIndex == -1)
			return null;
		else
			return elementOrder.get(selectedIndex);
	}

	@Override
	public void setSelectedItem(String name) {
//TODO zoom to selected element
	}
	
	@Override
	public void setVisibleItemCount(int visibleItemCount) {
		if(visibleItemCount >= 0)
			this.visibleItemCount = visibleItemCount;
	}
	
	private void scrollDown(){
		int i = firstVisibleItem;
		if(scrollStep + firstVisibleItem + visibleItemCount >= elementOrder.size()){
			firstVisibleItem = elementOrder.size()-visibleItemCount;
			if(firstVisibleItem < 0)
				firstVisibleItem = 0;
		}
		else
			firstVisibleItem += scrollStep;
		if(i != firstVisibleItem)
			update();
	}
	
	private void scrollUp(){
		int i = firstVisibleItem;
		if(firstVisibleItem - scrollStep < 0)
			firstVisibleItem = 0;
		else
			firstVisibleItem -= scrollStep;
		if(i != firstVisibleItem)
			update();
	}

	private void selectItem(String name){
		//de-select previous
		//TODO
		//select
		items.get(name);
		selectedIndex = elementOrder.indexOf(name);
	}

	@Override
	public void setScrollStep(int scrollStep) {
		if(scrollStep > 0)
			this.scrollStep = scrollStep;
	}
}
