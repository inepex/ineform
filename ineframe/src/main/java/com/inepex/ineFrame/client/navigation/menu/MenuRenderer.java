package com.inepex.ineFrame.client.navigation.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.OnClieckedLogic;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.Tab;
import com.inepex.ineom.shared.descriptor.Node;

@Singleton
public class MenuRenderer {
	
	private final PlaceHierarchyProvider hierarchyProvider;
	private final EventBus eventBus;
	private final View view;
	
	public static interface View {
		
		public void clearView();
		public Tab createTab(String menuName, int level);
		
		static interface Tab {
			public void addChild(Tab tab);
			public void setOnClickedLogic(OnClieckedLogic logic);
			public void setItemVisible(boolean visible);
			public void setEnabled(boolean enabled);
			public void setClickable(boolean clickable);
			public void setSelected(boolean selected);
		}
		
		static interface OnClieckedLogic{
			public void doLogic();
		}
	}
	
	@Inject
	public MenuRenderer(PlaceHierarchyProvider hierarchyProvider, EventBus eventBus, View view) {
		this.hierarchyProvider= hierarchyProvider;
		this.eventBus=eventBus;
		this.view=view;
	}
	
	public View getView() {
		return view;
	}

	/**
	 * menurenderer shows the same level nodes by the "selected node line"
	 * 
	 * menurenderer does not show nodes that doesn't have menuName
	 * 
	 * menurenderer does not show the selected node's children by default
	 * 
	 */
	public void realizeNewPlace(String hierarchycalId, InePlace place) {
		view.clearView();
		
		List<String> tokens = new ArrayList<String>(Arrays.asList(hierarchycalId.split("/")));
		
		if(tokens.size()<1)
			return;
		
		Node<InePlace> pointer = hierarchyProvider.getCurrentRoot();
		
		Tab tabPointer=null;
		
		for(int i=0; i<tokens.size()
				|| pointer!=null && pointer.getNodeElement()!=null
						&& pointer.getNodeElement().isShowChildreWhenActive()
						&& i==tokens.size(); i++) {
			Tab selectedTab = null;
			Node<InePlace> selectednode=null;
			
			if(pointer.hasChildren()) {
				for(final Node<InePlace> node : pointer.getChildren()) {
					boolean selected = i<tokens.size() && node.getNodeId().equals(tokens.get(i));
					boolean visible = !
							(node.getNodeElement().isOnlyVisibleWhenActive() && !selected
							|| node.getNodeElement().getMenuName()==null
							|| node.getNodeElement().getMenuName().length()<1);
					
					Tab tab = view.createTab(node.getNodeElement().getMenuName(), i);
					if(tabPointer!=null)
						tabPointer.addChild(tab);
					
					tab.setClickable((!selected || i!=tokens.size()-1) && visible);
					tab.setSelected(selected);
					tab.setEnabled(true); //TODO implement enabled-disabled logic
					tab.setItemVisible(visible);
					
					tab.setOnClickedLogic(new OnClieckedLogic() {
						
						@Override
						public void doLogic() {
							PlaceRequestEvent pre = new PlaceRequestEvent(node.getHierarchicalId());
							eventBus.fireEvent(pre);
						}
					});
					
					if(selected) {
						selectedTab=tab;
						selectednode=node;
					}
				}
			}
			
			pointer=selectednode;
			tabPointer=selectedTab;
		}
	}
}
