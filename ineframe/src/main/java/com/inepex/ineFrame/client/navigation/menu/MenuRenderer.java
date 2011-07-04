package com.inepex.ineFrame.client.navigation.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.OnClieckedLogic;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.Tab;
import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineFrame.client.navigation.places.ParamPlace.ParamPlaceWidget;
import com.inepex.ineom.shared.descriptor.Node;

@Singleton
public class MenuRenderer {
	
	private final PlaceHierarchyProvider hierarchyProvider;
	private final EventBus eventBus;
	private final View view;
	
	public static interface View {
		
		public void clearView();
		public Tab createTab(String menuName, int level);
		public void addWidget(IsWidget w);
		public FlowPanel getTarget();
		
		static interface Tab {
			public void setOnClickedLogic(OnClieckedLogic logic);
			public void setItemVisible(boolean visible);
			public void setEnabled(boolean enabled);
			public void setClickable(boolean clickable);
			public void setSelected(boolean selected);
			public void renderToRightSide();
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
	 * 
	 * menurenderer shows the same level nodes by the "selected node line"
	 * 
	 * menurenderer does not show nodes that doesn't have menuName
	 * 
	 * menurenderer does not show the selected node's children by default
	 * 
	 */
	public FlowPanel realizeNewPlace(InePlace place) {
		view.clearView();
		
		List<String> tokens = new ArrayList<String>(Arrays.asList(place.getHierarchicalToken().split("/")));
		
		if(tokens.size()<2)
			return view.getTarget();
		
		Node<InePlace> pointer = hierarchyProvider.getPlaceRoot();
		if(hierarchyProvider.getCurrentMenuRoot()!=null) {
			for(String s : hierarchyProvider.getCurrentMenuRoot()) {
				pointer=pointer.findNodeById(s);
				if(pointer==null)
					throw new RuntimeException();
				
				String token = tokens.remove(0);
				if(!token.equals(s))
					throw new RuntimeException();
			}
		}
		
		for(int i=0; i<tokens.size()
				|| pointer!=null && pointer.getNodeElement()!=null && pointer.getNodeElement().isShowChildreWhenActive() && i==tokens.size(); i++) {
			Node<InePlace> selectednode=null;
			
			if(pointer.hasChildren()) {
				for(final Node<InePlace> node : pointer.getChildren()) {
					boolean selected = i<tokens.size() && node.getNodeId().equals(tokens.get(i));
					boolean visible = !
							(node.getNodeElement().isOnlyVisibleWhenActive() && !selected
							|| node.getNodeElement().getMenuName()==null
							|| node.getNodeElement().getMenuName().length()<1);
					
					Tab tab = view.createTab(node.getNodeElement().getMenuName(), i);
					
					tab.setClickable((!selected || i!=tokens.size()-1) && visible);
					tab.setSelected(selected);
					tab.setEnabled(true); //TODO implement enabled-disabled logic
					tab.setItemVisible(visible);
					if(node.getNodeElement().isRenderOnRightSide())
						tab.renderToRightSide();
					
					tab.setOnClickedLogic(new OnClieckedLogic() {
						
						@Override
						public void doLogic() {
							PlaceRequestEvent pre = new PlaceRequestEvent(node.getNodeElement().getHierarchicalToken());
							eventBus.fireEvent(pre);
						}
					});
					
					if(selected) {
						selectednode=node;
					}
				}
			}
			
			pointer=selectednode;
			
			if(selectednode!=null) {
				if(selectednode.getNodeElement()!=null && selectednode.getNodeElement() instanceof ParamPlace) {
					ParamPlaceWidget w = ((ParamPlace) selectednode.getNodeElement()).getSelectorWidget();
					if(w!=null) view.addWidget(w);
				}
			}
		}
		
		return view.getTarget();
	}
}