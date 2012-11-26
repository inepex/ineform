package com.inepex.ineFrame.client.navigation.menu;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.OnClickedLogic;
import com.inepex.ineFrame.client.page.InePage;
import com.inepex.ineFrame.client.widgets.AnchorWidget;
import com.inepex.ineFrame.client.widgets.ListItemWidget;
import com.inepex.ineFrame.client.widgets.UnorderedListWidget;

@Singleton
public class MenuRendererView extends FlowPanel implements MenuRenderer.View {
	
	private class OneLevel extends FlowPanel {
		private FlowPanel target = new FlowPanel();
		private FlowPanel menu = new FlowPanel();
		private UnorderedListWidget menuUL;
		private boolean selectorRendered = false;
		
		public OneLevel(FlowPanel parent, int level) {
			parent.add(this);
			
			switch (level) {
			case 0:
				menu.addStyleName(ResourceHelper.getRes().style().menu());
				break;
			case 1:
				menu.addStyleName(ResourceHelper.getRes().style().submenu());
				break;
				
			default:
				menu.addStyleName(ResourceHelper.getRes().style().menu3());
				break;
			}
			init();
		}

		public void init(){
			selectorRendered = false;
			clear();
			target.clear();
			menu.clear();
			if (menuUL != null){
				menuUL.clear();
				menu.add(menuUL);
			}
			add(menu);
			add(target);
		}
		
		public FlowPanel getTarget() {
			return target;
		}

		public UnorderedListWidget getMenu() {
			if (menuUL == null) {
				menuUL = new UnorderedListWidget();
				menu.add(menuUL);
			}
			return menuUL;
		}
		
		public void setSelector(IsWidget selector){
			Grid grid = new Grid(1, 2);
			grid.setWidget(0, 0, selector.asWidget());
			clear();
			add(menu);
			add(grid);
			grid.setWidget(0, 1, target);
			grid.addStyleName(ResourceHelper.getRes().style().menuRendererWidgetContainer());
			grid.getCellFormatter().getElement(0, 0).getStyle().setWidth(1, Unit.PX);
			grid.getRowFormatter().getElement(0).getStyle().setVerticalAlign(VerticalAlign.TOP);
			selectorRendered = true;
		}

		public boolean isSelectorRendered() {
			return selectorRendered;
		}
	}
	
	private List<OneLevel> levels = new ArrayList<OneLevel>(); 
	
	@Inject
	public MenuRendererView() {
		levels.add(new OneLevel(this, 0));
	}

	@Override
	public void clearLevel(int level) {
		OneLevel lastLevel = levels.get(levels.size() - 1);
		if (!lastLevel.isSelectorRendered()){
			lastLevel.getTarget().clear();
		}
		
		if (level == 0){
			levels.get(level).init();	
		} 
		for (int i = level; i < levels.size(); i++){
			if (i != 0){
				levels.get(i).removeFromParent();
				levels.remove(i);
			}
		}
			
	}
	
	private OneLevel getOneLevel(int level){
		if (levels.size() <= level){
			levels.add(new OneLevel(getOneLevel(level - 1).getTarget(), level));
		}
		return levels.get(level);
	}
	
	@Override
	public void showSelector(IsWidget w, int level, boolean asPage) {
		if (asPage){
			getOneLevel(level).getTarget().add(w);
		} else {
			getOneLevel(level).setSelector(w);
		}
	}

	@Override
	public Tab createTab(String menuName, int level) {
		MenuBarWidget barWidget = new MenuBarWidget(menuName);
		getOneLevel(level).getMenu().add(barWidget);
		return barWidget;
	}	
	
	@Override
	public void appendMenuWidget(Widget widget, int level) {
		getOneLevel(level).getMenu().add(new ListItemWidget(widget));
	}
	

	public static class MenuBarWidget extends HandlerAwareComposite implements Tab {

		private final ListItemWidget listWidget;
		private final AnchorWidget a;
		
		private boolean clickable = true;
		private boolean enabled = true;
		
		private OnClickedLogic onClickedLogic;
		
		
		public MenuBarWidget(String menuName) {
			a = new AnchorWidget(menuName);
			listWidget = new ListItemWidget(a);
			initWidget(listWidget);
		}
		
		@Override
		protected void onAttach() {
			super.onAttach();
			
			registerHandler(listWidget.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if(clickable && onClickedLogic!=null)
						onClickedLogic.doLogic();
				}
			}));
		}

		@Override
		public void setOnClickedLogic(OnClickedLogic logic) {
			this.onClickedLogic=logic;
		}

		@Override
		public void setEnabled(boolean enabled) {
			if(this.enabled){
				//TODO additional style name
			} else {
			}
			
			this.enabled=enabled;
		}

		@Override
		public void setClickable(boolean clickable) {
			if(this.clickable) {
				getElement().getStyle().setCursor(Cursor.POINTER);
			} else {
				getElement().getStyle().setCursor(Cursor.DEFAULT);
			}
			this.clickable=clickable;
		}

		@Override
		public void setSelected(boolean selected) {
			if(selected) {
				addStyleName(ResourceHelper.getRes().style().current_page_item());
			}
		}

		@Override
		public void setItemVisible(boolean visible) {
			setVisible(visible);
		}

		@Override
		public void renderToRightSide() {
			getElement().getStyle().setFloat(Float.RIGHT);
		}
	}

	@Override
	public void showPage(InePage page) {
		OneLevel oneLevel = levels.get(levels.size() - 1);
		oneLevel.getTarget().add(page.asWidget());
	}

}
