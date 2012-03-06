package com.inepex.ineForm.client.places;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.datamanipulator.events.KeyValueObjectListModifiedEvent;
import com.inepex.ineForm.client.datamanipulator.events.KeyValueObjectListModifiedEventHandler;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.widget.SelectorView;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineForm.shared.dispatch.RelationListActionResult;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.places.OneParamPlace;
import com.inepex.ineFrame.client.navigation.places.OneParamPlace.OneParamPresenter;
import com.inepex.ineom.shared.Relation;

public class DefaultOneParamPresenter implements OneParamPresenter {
	
	public static interface SelectorPresenterFactory {
		public DefaultOneParamPresenter create(@Assisted String descriptorName);
	}
	
	public interface View extends IsWidget{
		
		void addNextClickHandler(ClickHandler h);
		void addPreviousClickHandler(ClickHandler h);
		void addItemSelectedClickHandler(ClickHandler h);
		
		int getSelectedIndex();
		String getSelectedItem();
		void setSelectedItem(String name);
		void setVisibleItemCount(int visibleItemCount);
		void setScrollStep(int scrollStep);
		
		void showLoading();
		void hideLoading();
		
		void addItem(String name);
		void clear();
	}

	protected final View selectorView;

	protected RelationListAction listAction;
	protected RelationListActionResult lastListResult;
	protected final FormContext formContext;
	protected Long selectedId=null;
	protected boolean updatingNOW=false;	
	protected OneParamPlace oneParamPlace;	
	protected AsyncCallback<String> callback;
	
	@Inject
	public DefaultOneParamPresenter(FormContext formContext) {	
		this.formContext=formContext;		
		
		selectorView = new SelectorView();
		bindView();
	}
	
	public DefaultOneParamPresenter setListAction(RelationListAction listAction) {
		this.listAction = listAction;
		return this;
	}
	
	public void setOneParamPlace(OneParamPlace oneParamPlace) {
		this.oneParamPlace = oneParamPlace;
	}

	private void bindView(){
		formContext.eventBus.addHandler(KeyValueObjectListModifiedEvent.TYPE, new KeyValueObjectListModifiedEventHandler() {
			
			@Override
			public void onObjectListModified(KeyValueObjectListModifiedEvent event) {
				updateList();
			}
		});
		
		
		selectorView.setScrollStep(1);
		selectorView.setVisibleItemCount(5);
		selectorView.addItemSelectedClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(lastListResult != null){
					for(Relation rel : lastListResult.getList()) {
						if(selectorView.getSelectedItem() == rel.getDisplayName()){
							selectedId = rel.getId();
							break;
						}
					}
				}
				String selectedString = null;
				if (selectedId != null){
					selectedString = selectedId.toString();
				}
				formContext.eventBus.fireEvent(new PlaceRequestEvent(oneParamPlace.getChildPlaceToken(selectedString)));
				
			}
		});
				
	
	}


	private void updateList() {
		if(updatingNOW)
			return;
		
		updatingNOW=true;
		
		selectorView.clear();
		
		formContext.ineDispatch.execute(getListAction(), new IneDispatch.SuccessCallback<RelationListActionResult>() {

			@Override
			public void onSuccess(RelationListActionResult result) {
				lastListResult = result;
				
				if(result.getList()!=null && result.getList().size()>0) {
					for(Relation rel : result.getList()) {
						selectorView.addItem(rel.getDisplayName());
					}
				}
				
				if(selectedId!=null) {
					for(Relation rel : result.getList()){
						if(rel.getId() == selectedId){
							selectorView.setSelectedItem(rel.getDisplayName());
							break;
						}
					}
				}
						
				updatingNOW=false;
				if (callback != null){
					callback.onSuccess(null);
				}
			}
		});
	}

	public Widget asWidget() {
		return selectorView.asWidget();
	}

	@Override
	public void getDefaultSelection(AsyncCallback<String> callback) {
		if (lastListResult != null) callback.onSuccess(null); 
		else {
			this.callback = callback;
		}
	}

	@Override
	public void setSelection(String selected) {
		if(selected==null)
			selectedId=null;
		else 
			selectedId=Long.parseLong(selected);
	}
	
	@Override
	public void onShow() {
		if (lastListResult == null && !updatingNOW)
			updateList();
	}
	
	private RelationListAction getListAction(){
		if(listAction==null)
			this.listAction= new RelationListAction(oneParamPlace.getDescriptorName(), null, 0, 1000, false);
		return listAction;
	}

	@Override
	public void setCurrentPlace(InePlace place) {
	}

	@Override
	public void setUrlParameters(Map<String, String> urlParams, UrlParamsParsedCallback callback) throws Exception {
	}

}
