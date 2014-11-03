package com.inepex.ineFrame.client.navigation.menu;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.util.DesignConstants;
import com.inepex.ineFrame.client.widgets.UnorderedListWidget;

public class MenuOneLevelView extends LayoutPanel {
	private LayoutPanel target = new LayoutPanel();
	private FlowPanel menu = new FlowPanel();
	private FlowPanel menuBorder = new FlowPanel();
	private UnorderedListWidget menuUL;
	private boolean selectorRendered = false;
	private boolean hiddenMenu = false;
	
	public MenuOneLevelView(LayoutPanel parent, int level, boolean hiddenMenu) {
		this.hiddenMenu = hiddenMenu;
		getElement().setId("OneLevel-" + level);
		parent.add(this);
		menu.addStyleName(ResourceHelper.getRes().style().menu());
		menuBorder.addStyleName(ResourceHelper.getRes().style().menuBorder());
		menu.getElement().setId("OneLevel-" + level + "-Menu");
		getElement().getStyle().setOverflow(Overflow.VISIBLE);
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
		add(menuBorder);
		add(target);
		if (!hiddenMenu){
			setWidgetTopHeight(menu, 0, Unit.PX, DesignConstants.base, Unit.PX);
			setWidgetTopHeight(menuBorder, DesignConstants.base, Unit.PX, DesignConstants.b0d125, Unit.PX);
			setWidgetTopBottom(target, DesignConstants.base + DesignConstants.b0d125, Unit.PX, 0, Unit.PX);	
		} else { 
			setWidgetTopHeight(menu, 0, Unit.PX, 0, Unit.PX);
			setWidgetTopHeight(menuBorder, 0, Unit.PX, 0, Unit.PX);
			setWidgetTopBottom(target, 0, Unit.PX, 0, Unit.PX);
		}
	}
	
	public LayoutPanel getTarget() {
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
		grid.getElement().setId("SelectorGrid");
		grid.setWidget(0, 0, selector.asWidget());
		clear();
		add(menu);
		add(menuBorder);
		add(grid);
		
		if (!hiddenMenu){
			setWidgetTopHeight(menu, 0, Unit.PX, DesignConstants.base, Unit.PX);
			setWidgetTopHeight(menuBorder, DesignConstants.base, Unit.PX, DesignConstants.b0d125, Unit.PX);
			setWidgetTopBottom(grid, DesignConstants.base + DesignConstants.b0d125, Unit.PX, 0, Unit.PX);	
		} else { 
			setWidgetTopHeight(menu, 0, Unit.PX, 0, Unit.PX);
			setWidgetTopHeight(menuBorder, 0, Unit.PX, 0, Unit.PX);
			setWidgetTopBottom(grid, 0, Unit.PX, 0, Unit.PX);
		}
		
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
