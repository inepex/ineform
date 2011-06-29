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
	 * 1.,
	 * menu renderer DOES NOT display the rootNode's element, and it's children
	 * the first displayed menu line will be the the one of root's child's children
	 * 
	 * 2.,
	 * menurenderer shows the same level nodes by the "selected node line"
	 * 
	 * 3.,
	 * menurenderer does not show nodes that doesn't have menuName
	 * 
	 * 4.,
	 * menurenderer does not show the selected node's children
	 * 
	 */
	public void realizeNewPlace(InePlace place) {
		view.clearView();
		
		List<String> tokens = new ArrayList<String>(Arrays.asList(place.getHierarchicalToken().split("/")));
		
		if(tokens.size()<2)
			return;
		
		Node<InePlace> pointer = hierarchyProvider.getPlaceRoot();
		pointer=pointer.findNodeByHierarchicalId(tokens.remove(0));
		
		Tab tabPointer=null;
		
		for(int i=0; i<tokens.size(); i++) {
			Tab selectedTab = null;
			Node<InePlace> selectednode=null;
			
			if(pointer.hasChildren()) {
				for(final Node<InePlace> node : pointer.getChildren()) {
					boolean selected = node.getNodeId().equals(tokens.get(i));
					boolean visible = !
							(node.getNodeElement().isOnlyVisibleWhenActive() && !selected
							|| node.getNodeElement().getMenuName()==null
							|| node.getNodeElement().getMenuName().length()<1);
					
					Tab tab = view.createTab(node.getNodeElement().getMenuName(), i);
					if(tabPointer!=null)
						tabPointer.addChild(tab);
					
					tab.setClickable((!selected || i<tokens.size()-1) && visible);
					tab.setSelected(selected);
					tab.setEnabled(true); //TODO implement enabled-disabled logic
					tab.setItemVisible(visible);
					
					tab.setOnClickedLogic(new OnClieckedLogic() {
						
						@Override
						public void doLogic() {
							PlaceRequestEvent pre = new PlaceRequestEvent(node.getNodeElement().getHierarchicalToken());
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
