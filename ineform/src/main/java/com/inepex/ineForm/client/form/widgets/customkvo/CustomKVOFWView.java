package com.inepex.ineForm.client.form.widgets.customkvo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.inepex.ineForm.client.general.ErrorMessageManagerInterface;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineom.shared.util.SharedUtil;

public class CustomKVOFWView extends HandlerAwareFlowPanel implements CustomKVOFW.View {
	
	private final Map<Long, DispRow> rowsByInnerId = new HashMap<Long, DispRow>();
	private List<Long> dispRowinnerIdMirror = new ArrayList<Long>();
	
	private final FlexTable rowTable = new FlexTable();
	
	private final Button addBtn = new Button(IneFormI18n.ADD());
	
	private RemoveCallback removeCallback;
	private AddCallback addCallback;
	private RowValueChangeCallback rowValueChangeCallback;
	
	CustomKVOFWView() {
		add(rowTable);
		add(addBtn);
		
		createHeader();
	}
	
	private void createHeader() {
		rowTable.setText(0, 0, IneFormI18n.customKVO_key());
		rowTable.setText(0, 1, IneFormI18n.customKVO_type());
		rowTable.setText(0, 2, IneFormI18n.customKVO_value());
		
		rowTable.getCellFormatter().setStyleName(0, 0, ResourceHelper.getRes().style().customKVOHeader());
		rowTable.getCellFormatter().setStyleName(0, 1, ResourceHelper.getRes().style().customKVOHeaderType());
		rowTable.getCellFormatter().setStyleName(0, 2, ResourceHelper.getRes().style().customKVOHeader());
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
	public void dealResult(Map<Long, String> res) {
		for(DispRow dr : rowsByInnerId.values())
			dr.getErrorManager().clearErrorMsg();
		
		for(Long i : res.keySet()) {
			rowsByInnerId.get(i).getErrorManager().addErrorMsg(SharedUtil.Li(res.get(i)));
		}
	}

	@Override
	public ErrorMessageManagerInterface getErrorManager(CustomKVORow r) {
		return rowsByInnerId.get(r.getInnerId()).getErrorManager();
	}
	
	@Override
	public void clearRows() {
		dispRowinnerIdMirror.clear();
		rowsByInnerId.clear();
		
		//remove all rows except the header
		for(int i=rowTable.getRowCount()-1; i>0; i--)
			rowTable.removeRow(i);
	}
	
	@Override
	public void addRow(CustomKVORow r) {
		DispRow dr = new DispRow(r, removeCallback, rowValueChangeCallback, rowTable);
		rowsByInnerId.put(r.getInnerId(), dr);
		dispRowinnerIdMirror.add(r.getInnerId());
	}

	@Override
	public void removeRow(CustomKVORow r) {
		//TODO
		rowsByInnerId.remove(rowsByInnerId.get(r.getInnerId()));
		
		int index = dispRowinnerIdMirror.indexOf(r.getInnerId());
		
		dispRowinnerIdMirror.remove(index);
		
		//because of header
		index++; 
		rowTable.removeRow(index);
	}
}
