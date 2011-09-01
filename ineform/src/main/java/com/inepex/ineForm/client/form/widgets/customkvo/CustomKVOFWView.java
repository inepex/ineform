package com.inepex.ineForm.client.form.widgets.customkvo;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineForm.client.general.ErrorMessageManagerInterface;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineom.shared.util.SharedUtil;

public class CustomKVOFWView extends HandlerAwareFlowPanel implements CustomKVOFW.View {
	
	private final Map<Integer, DispRow> rowsByInnerId = new HashMap<Integer, DispRow>();
	
	private final VerticalPanel rowPanel = new VerticalPanel();
	private final Button addBtn = new Button(IneFormI18n.ADD());
	
	private RemoveCallback removeCallback;
	private AddCallback addCallback;
	private RowValueChangeCallback rowValueChangeCallback;
	
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
	public void setRowValueChangeCallback(
			RowValueChangeCallback rowValueChangeCallback) {
		this.rowValueChangeCallback = rowValueChangeCallback;
	}

	@Override
	public void clearRows() {
		rowPanel.clear();
		rowsByInnerId.clear();
	}
	
	@Override
	public void addRow(CustomKVORow r) {
		DispRow dr = new DispRow(r, removeCallback, rowValueChangeCallback);
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
				addCallback.onAdd();
			}
		}));
	}

	@Override
	public void dealResult(Map<Integer, String> res) {
		for(DispRow dr : rowsByInnerId.values())
			dr.getErrorManager().clearErrorMsg();
		
		for(Integer i : res.keySet()) {
			rowsByInnerId.get(i).getErrorManager().addErrorMsg(SharedUtil.Li(res.get(i)));
		}
	}

	@Override
	public ErrorMessageManagerInterface getErrorManager(CustomKVORow r) {
		return rowsByInnerId.get(r.getInnerId()).getErrorManager();
	}
}
