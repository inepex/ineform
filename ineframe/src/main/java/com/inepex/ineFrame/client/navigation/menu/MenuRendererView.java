package com.inepex.ineFrame.client.navigation.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;

public class MenuRendererView extends UnorderedListWidget implements MenuRenderer.View {
	
	@Inject
	public MenuRendererView() {
	}

	@Override
	public void clearView() {
		clear();
	}

	@Override
	public Tab createTab(String menuName, int level) {
		 MenuBarWidget barWidget = new MenuBarWidget(menuName, level);
		 if(level==0)
			 add(barWidget);
		return barWidget;
	}
	
	
	private class MenuBarWidget extends HandlerAwareComposite implements Tab {

		private final ListItemWidget listWidget;
		private final UnorderedListWidget ulwidget = new UnorderedListWidget();
		
		private boolean clickable = true;
		private boolean visible = true;
		private boolean enabled = true;
		private boolean selected = false;
		
		private OnClieckedLogic onClickedLogic;
		
		
		public MenuBarWidget(String menuName, int level) {
			listWidget = new ListItemWidget(menuName);
			//TODO level depenent style
			
			initWidget(listWidget);
			listWidget.add(ulwidget);
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
		public void addChild(Tab tab) {
			ulwidget.add((Widget) tab);
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
				//TODO additional style name
			} else {
				
			}
			
			this.clickable=clickable;
		}

		@Override
		public void setSelected(boolean selected) {
			if(this.selected) {
				//TODO additional style name
			} else {
				
			}
			
			this.selected=selected;
		}

		@Override
		public void setItemVisible(boolean visible) {
			this.visible=visible;
			setVisible(visible);
		}
			
		
	}
}
