package com.inepex.ineForm.client.places;

import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineForm.shared.dispatch.RelationListResult;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.places.ParamPlace.ParamPlaceWidget;
import com.inepex.ineom.shared.kvo.Relation;

public class SelectorWidget extends HandlerAwareComposite implements ParamPlaceWidget{

	private final ListBox listBox;
	private final String paramToken;
	private final RelationListAction listAction;
	private final String childToken;
	private final String hieralchicalToken;
	private final FormContext formContext;
	private final Map<Long, Integer> listItemIdById;
	
	private Long selectedId=null;
	
	SelectorWidget(String paramToken, String descriptorName,
			String childToken, String hierarchicalToken, FormContext formContext) {
		this.childToken=childToken;
		this.formContext=formContext;
		this.hieralchicalToken=hierarchicalToken;
		this.paramToken=paramToken;
		
		listAction= new RelationListAction(descriptorName, null, 0, 1000, false);
		
		listItemIdById = new TreeMap<Long, Integer>();
		listBox=new ListBox(false);
		listBox.setVisibleItemCount(30);
		initWidget(listBox);
		
		addStyleName(ResourceHelper.getRes().style().selectorWidget());
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
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(listBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				int i = listBox.getSelectedIndex();
				if(i==-1)
					selectedId=null;
				else
					selectedId=Long.parseLong(listBox.getValue(i));
				
				String newHierarchicalToken;
				if(selectedId==null) {
					//FIXME: hieralchicalToken should be dynamic
					newHierarchicalToken=hieralchicalToken;
				} else {
					//FIXME: hieralchicalToken should be dynamic
					newHierarchicalToken=hieralchicalToken+"?"+paramToken+"="+selectedId.toString()+"/"+childToken;
				}				
				
				formContext.eventBus.fireEvent(new PlaceRequestEvent(newHierarchicalToken));
			}
		}));
	}

	private void updateList() {
		listBox.clear();
		listItemIdById.clear();
		
		formContext.ineDispatch.execute(listAction, new IneDispatch.SuccessCallback<RelationListResult>() {

			@Override
			public void onSuccess(RelationListResult result) {
				int i=0;
				
				if(result.getList()!=null && result.getList().size()>0) {
					for(Relation rel : result.getList()) {
						listBox.addItem(rel.getDisplayName(), rel.getId().toString());
						listItemIdById.put(rel.getId(), new Integer(i));
						i++;
					}
				}
				
				if(selectedId!=null) {
					Integer itemId = listItemIdById.get(selectedId);
					if(itemId!=null)
						listBox.setSelectedIndex(itemId);
					else
						System.out.println("warning: SelectorWidget: there is no item for id "+selectedId);
				}
			}
		});
	}
	
}
