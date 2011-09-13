package com.inepex.ineFrame.client.navigation.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.OnClickedLogic;
import com.inepex.ineFrame.client.navigation.PlaceHandlerHelper;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.Tab;
import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineFrame.client.navigation.places.ParamPlace.ParamPlacePresenter;
import com.inepex.ineFrame.client.navigation.places.WidgetPlace;
import com.inepex.ineFrame.client.page.InePage;
import com.inepex.ineom.shared.descriptor.Node;

@Singleton
public class MenuRenderer {
	
	public static interface View {
		
		public void clearView();

		public Tab createTab(String menuName, int level);
		public void appendMenuWidget(Widget widget, int level);
		
		public void addWidget(IsWidget w);
		public void showPage(InePage page);
		
		static interface Tab {
			public void setOnClickedLogic(OnClickedLogic logic);
			public void setItemVisible(boolean visible);
			public void setEnabled(boolean enabled);
			public void setClickable(boolean clickable);
			public void setSelected(boolean selected);
			public void renderToRightSide();
		}
		
		public IsWidget asWidget();
	}
	
	private final PlaceHierarchyProvider hierarchyProvider;
	private final EventBus eventBus;
	private final AuthManager authManager;
	
	private final View view;
	
	@Inject
	public MenuRenderer(PlaceHierarchyProvider hierarchyProvider, EventBus eventBus,
			View view, AuthManager authManager) {
		
		this.hierarchyProvider= hierarchyProvider;
		this.eventBus=eventBus;
		this.authManager=authManager;
		this.view=view;
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
	public void realizeNewPlaceOnMenu(InePlace place, Map<String, String> urlParams) {
		view.clearView();
		
		List<String> tokens = new ArrayList<String>(Arrays.asList(
				PlaceHandlerHelper.getPlacePart(place.getHierarchicalToken())
				.split(PlaceHandlerHelper.regExp(Node.ID_SEPARATOR))));
		
		//TODO sebi check it!
//		if(tokens.size()<2)
//			return;
		
		Node<InePlace> pointer = hierarchyProvider.getPlaceRoot();
		if(hierarchyProvider.getCurrentMenuRoot()!=null) {
			for(String s : hierarchyProvider.getCurrentMenuRoot()) {
				pointer=pointer.findNodeById(s);
				if(pointer==null)
					throw new RuntimeException();
				
				String token = tokens.remove(0);
				if(!token.equals(s))
					throw new RuntimeException(token+" != "+ s);
			}
		}
		
		for(int i=0; i<tokens.size()
				|| pointer!=null && pointer.getNodeElement()!=null && pointer.getNodeElement().isShowChildreWhenActive() && i==tokens.size(); i++) {
			Node<InePlace> selectednode=null;
			
			if(pointer.hasChildren()) {
				for(final Node<InePlace> node : pointer.getChildren()) {
					if(!(authManager instanceof NoAuthManager) &&
							!authManager.doUserHaveAnyOfRoles(node.getNodeElement().getRolesAllowedInArray()))
						continue;
					
					if(node.getNodeElement() instanceof WidgetPlace) {
						WidgetPlace wp = (WidgetPlace) node.getNodeElement();
						if(wp.isWidget(urlParams))
							view.appendMenuWidget(wp.getWidget(urlParams), i);
						
						continue;
					}
					
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
					
					tab.setOnClickedLogic(new OnClickedLogic() {
						
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
					ParamPlacePresenter w = ((ParamPlace) selectednode.getNodeElement()).getSelectorPresenter();
					if(w!=null)
						view.addWidget(w.asWidget());
				}
			}
		}
	}
	
	public void showPage(InePage page){
		view.showPage(page);
	}
}
