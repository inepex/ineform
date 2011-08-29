package com.inepex.ineForm.client.general;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class FlowPanelBasedEMM extends SimpleTableErrorMessageManager implements IsWidget {
	
	private final FlowPanel fp;
	
	/**
	 * it's private use {@link FlowPanelBasedEMM#newInstance()}
	 * 
	 * @param fp
	 * @param itsElement
	 */
	private FlowPanelBasedEMM(FlowPanel fp, Element itsElement) {
		super(itsElement);
		this.fp=fp;
	}
	
	public static FlowPanelBasedEMM newInstance() {
		FlowPanel fp = new FlowPanel();
		return new FlowPanelBasedEMM(fp, fp.getElement());
	}
	
	@Override
	public Widget asWidget() {
		return fp;
	}
	

}
