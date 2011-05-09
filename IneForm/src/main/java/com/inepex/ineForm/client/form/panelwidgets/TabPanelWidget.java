package com.inepex.ineForm.client.form.panelwidgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.shared.types.PanelWidgetT;

public class TabPanelWidget extends PanelWidget {
	
	private final FlowPanel mainPanel=new FlowPanel();
	private final CustomTabPanel tabPanel=new CustomTabPanel();
	private final List<TabPageWidget> tabs = new ArrayList<TabPageWidget>();
	boolean first=true;

	public TabPanelWidget(PanelWidgetT type, PanelWidget parent, DisplayedFormUnitChangeHandler parentHandler) {
		super(type, parent,parentHandler);
		initWidget(mainPanel);
		mainPanel.add(tabPanel);
	}

	@Override
	public void addToPanel(Widget w) {
		if(w instanceof TabPageWidget) {
			tabPanel.add(w, ((TabPageWidget) w).getTitleWidget());
			tabs.add((TabPageWidget) w);
			if(first) {
				tabPanel.selectTab(0,false);
				first=false;
			}
		} else {
			mainPanel.add(w);
		}
	}
	
	private class CustomTabPanel extends TabPanel {
		
		@Override
		public void selectTab(int index) {
			selectTab(index, true);
		}
		
		public void selectTab(int index, boolean userAction) {
			if(userAction) {
				if (tabPanel.getTabBar().getSelectedTab() != -1){
					onDisplayedFormUnitChange(
							TabPanelWidget.this
							, tabs.get(tabPanel.getTabBar().getSelectedTab()).getFormUnits()
							, new ChangeResponse(tabPanel.getTabBar().getSelectedTab(),index)
							);
				}
			}
			else super.selectTab(index);
		}
	}
	
	private class ChangeResponse extends DisplayedFormUnitChangeHandler.DisplayedFormUnitChangeResponse<Integer> {
		
		public ChangeResponse(Integer from, Integer to) {
			super(from, to);
		}

		@Override
		public void onSuccess() {
			tabPanel.selectTab(to,false);
			onPanelWidgetRefreshed(TabPanelWidget.this);
		}

		@Override
		public void onCancel() {
		}
	}
}
