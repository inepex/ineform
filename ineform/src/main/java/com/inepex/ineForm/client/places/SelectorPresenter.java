package com.inepex.ineForm.client.places;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.datamanipulator.events.KeyValueObjectListModifiedEvent;
import com.inepex.ineForm.client.datamanipulator.events.KeyValueObjectListModifiedEventHandler;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.widget.SelectorView;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineForm.shared.dispatch.RelationListActionResult;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.PlaceHandlerHelper;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.places.ParamPlace.ParamPlacePresenter;
import com.inepex.ineom.shared.Relation;

public class SelectorPresenter  implements ParamPlacePresenter{
	
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

	private final View selectorView;
	
	private final String paramToken;
	private final RelationListAction listAction;
	private RelationListActionResult lastListResult;
	private final String childToken;
	private final InePlace place;
	private final FormContext formContext;
	
	private Long selectedId=null;
	private boolean updatingNOW=false;
	
	/**
	 * @param newToken - can be null... newToken should be in the same level
	 * 
	 * @param listAction - can be null
	 */
	SelectorPresenter(String paramToken, String descriptorName,
			String childToken, InePlace place, FormContext formContext, RelationListAction listAction) {
	
		this.childToken=childToken;
		this.formContext=formContext;
		this.place=place;
		this.paramToken=paramToken;
			
		if(listAction==null)
			this.listAction= new RelationListAction(descriptorName, null, 0, 1000, false);
		else
			this.listAction=listAction;
		
		selectorView = new SelectorView();
		bindView();
		
	}

	@Override
	public void realizeUrlParams(Map<String, String> params) {
		String val = params.get(paramToken);
		if(val==null)
			selectedId=null;
		else 
			selectedId=Long.parseLong(val);
		
		updateList();
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
					
				
				String newHierarchicalToken;
				if(selectedId==null) {
					newHierarchicalToken=place.getHierarchicalToken();
				} else {
					newHierarchicalToken=
						PlaceHandlerHelper.appendChild(
							PlaceHandlerHelper.appendParam(
									place.getHierarchicalToken(), paramToken, selectedId.toString()),
							childToken);
				}				
				
				formContext.eventBus.fireEvent(new PlaceRequestEvent(newHierarchicalToken));
			}
		});
				
	
	}


	private void updateList() {
		if(updatingNOW)
			return;
		
		updatingNOW=true;
		
		selectorView.clear();
		
		
		formContext.ineDispatch.execute(listAction, new IneDispatch.SuccessCallback<RelationListActionResult>() {

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
			}
		});
	}

	@Override
	public Widget asWidget() {
		return selectorView.asWidget();
	}
	
	
}
