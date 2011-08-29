package com.inepex.ineForm.client.form.widgets.customkvo;

import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFW.AddCallback;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFW.RemoveCallback;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;

public class CustomKVOFWView extends HandlerAwareFlowPanel implements CustomKVOFW.View {
	
	private final Map<Integer, DispRow> rowsByInnerId = new TreeMap<Integer, DispRow>();
	
	private final HorizontalPanel rowPanel = new HorizontalPanel();
	private final Button addBtn = new Button(IneFormI18n.ADD());
	
	private RemoveCallback removeCallback;
	private AddCallback addCallback;
	
	CustomKVOFWView() {
		add(rowPanel);
		add(addBtn);
	}
	
	@Override
	public void setRemoveCallback(RemoveCallback removeCallback) {
		this.removeCallback=removeCallback;
	}

	@Override
	public void setAddCallback(AddCallback addCallback) {
		this.addCallback=addCallback;
	}

	@Override
	public void clearRows() {
		rowPanel.clear();
		rowsByInnerId.clear();
	}
	
	@Override
	public void addRow(CustomKVORow r) {
		DispRow dr = new DispRow(r, removeCallback);
		rowPanel.add(dr);
		rowsByInnerId.put(r.getInnerId(), dr);
	}

	@Override
	public void removeRow(CustomKVORow r) {
		DispRow dr = rowsByInnerId.get(r.getInnerId());
		dr.removeFromParent();
		rowsByInnerId.remove(dr);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(addBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addCallback.onClick();
			}
		}));
	}
}
