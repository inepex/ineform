package com.inepex.ineForm.client.form.panelwidgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.shared.types.PanelWidgetT;

public class StepperPanelWidget extends PanelWidget{

	private final FlowPanel mainPanel=new FlowPanel();
	private final FlowPanel pagePanel=new FlowPanel();
	private int displayedPage=-1;
	private List<StepperPanelPageWidget> pages = new ArrayList<StepperPanelPageWidget>();
	
	public StepperPanelWidget(PanelWidgetT type,PanelWidget parent, DisplayedFormUnitChangeHandler handler) {
		super(type, parent, handler);
		initWidget(mainPanel);
		mainPanel.add(pagePanel);
	}

	@Override
	public void addToPanel(Widget w) {
		if(w instanceof StepperPanelPageWidget) {
			pages.add((StepperPanelPageWidget) w);
			pagePanel.add(w);
			if(displayedPage==-1) {
				displayedPage=0;
			} else {
				w.setVisible(false);
			}
		} else {
			mainPanel.add(w);
		}
	}
	
	public void setDisplayedPage(int index, boolean needCustomCodeCallback) {
		if(needCustomCodeCallback) {
			onDisplayedFormUnitChange(this, pages.get(displayedPage).getFormUnits(), new ChangeResponse(getDisplayedPageNumber(), index));
		} else {
			pages.get(displayedPage).setVisible(false);
			pages.get(index).setVisible(true);
			displayedPage=index;
			onPanelWidgetRefreshed(this);
		}
	}
	
	public int getPageCount() {
		return pages.size();
	}
	
	/**
	 * the number of displayed page (first is the 0.)
	 * @return -1 if does not have any page
	 */
	public int getDisplayedPageNumber() {
		return displayedPage;
	}
	
	private class ChangeResponse extends DisplayedFormUnitChangeHandler.DisplayedFormUnitChangeResponse<Integer> {
		
		public ChangeResponse(Integer from, Integer to) {
			super(from, to);
		}

		@Override
		public void onSuccess() {
			setDisplayedPage(to, false);
		}

		@Override
		public void onCancel() {
		}
	}
	
}
