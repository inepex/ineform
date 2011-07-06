package com.inepex.ineFrame.client.navigation.menu;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;

@Singleton
public class MenuRendererView extends FlowPanel implements MenuRenderer.View {
	
	private FlowPanel target = this;
	private UnorderedListWidget menuUL=null;
	private int menuLevel=-1;
	
	
	@Inject
	public MenuRendererView() {
	}

	@Override
	public void clearView() {
		clear();
		target= this;
		menuUL=null;
		menuLevel=-1;
	}
	
	@Override
	public void addWidget(IsWidget w) {
		HorizontalPanel hp = new HorizontalPanel();
		hp.getElement().getStyle().setWidth(100, Unit.PCT);
		hp.add(w);
		FlowPanel fp = new FlowPanel();
		hp.add(fp);
		target.add(hp);
		target=fp;
	}

	@Override
	public Tab createTab(String menuName, int level) {
		if(level>menuLevel) {
			menuLevel=level;
			
			FlowPanel newMenuDiv = new FlowPanel();
			switch (level) {
			case 0:
				newMenuDiv.addStyleName(ResourceHelper.getRes().style().menu());
				break;
			case 1:
				newMenuDiv.addStyleName(ResourceHelper.getRes().style().submenu());
				break;
				
			default:
				newMenuDiv.addStyleName(ResourceHelper.getRes().style().menu3());
				break;
			}
			
			menuUL=new UnorderedListWidget();
			newMenuDiv.add(menuUL);
			target.add(newMenuDiv);
			
			FlowPanel newTarget = new FlowPanel();
			target.add(newTarget);
			target=newTarget;
		}
		
		MenuBarWidget barWidget = new MenuBarWidget(menuName, level);
		menuUL.add(barWidget);
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

	@Override
	public FlowPanel getTarget() {
		return target;
	}
}
