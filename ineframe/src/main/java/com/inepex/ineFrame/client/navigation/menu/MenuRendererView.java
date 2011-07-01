package com.inepex.ineFrame.client.navigation.menu;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;

public class MenuRendererView extends FlowPanel implements MenuRenderer.View {
	
	private final FlowPanel menu = new FlowPanel();
	private final UnorderedListWidget menuUL = new UnorderedListWidget();
	
	private final FlowPanel subMenu = new FlowPanel();
	private final UnorderedListWidget subMenuUL = new UnorderedListWidget();
	
	private final FlowPanel menu3 = new FlowPanel();
	private final UnorderedListWidget menu3UL = new UnorderedListWidget();
	
	private final FlowPanel menu4 = new FlowPanel();
	private final UnorderedListWidget menu4UL = new UnorderedListWidget();
	
	@Inject
	public MenuRendererView() {
		menu.addStyleName(ResourceHelper.getRes().style().menu());
		menu.add(menuUL);
		add(menu);
		
		subMenu.addStyleName(ResourceHelper.getRes().style().submenu());
		subMenu.add(subMenuUL);
		add(subMenu);
		
		menu3.add(menu3UL);
		add(menu3);
		
		menu4.add(menu4UL);
		add(menu4);
	}

	@Override
	public void clearView() {
		menuUL.clear();
		menu.setVisible(false);
		subMenuUL.clear();
		subMenu.setVisible(false);
		menu3UL.clear();
		menu3.setVisible(false);
		menu4UL.clear();
		menu4.setVisible(false);
	}

	@Override
	public Tab createTab(String menuName, int level) {
		 MenuBarWidget barWidget = new MenuBarWidget(menuName, level);
		 
		 switch (level) {
		 case 0:
			menu.setVisible(true);
			menuUL.add(barWidget);
			break;
		 case 1:
			subMenu.setVisible(true);
			subMenuUL.add(barWidget);
			break;
		 case 2:
			menu3.setVisible(true);
			menu3UL.add(barWidget);
			break;
		 case 3:
			menu4.setVisible(true);
			menu4UL.add(barWidget);
			break;
		 default:
			throw new RuntimeException("not implemented yet!!");
		 }
		 
		 return barWidget;
	}
	
	private class MenuBarWidget extends HandlerAwareComposite implements Tab {

		private final ListItemWidget listWidget;
		private final AnchorWidget a;
		
		private boolean clickable = true;
		private boolean enabled = true;
		
		private OnClieckedLogic onClickedLogic;
		
		
		public MenuBarWidget(String menuName, int level) {
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
		public void setOnClickedLogic(OnClieckedLogic logic) {
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
}
