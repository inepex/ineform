package com.inepex.ineForm.client.form.widgets.datetime;

import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.ConstantHelper;
import com.inepex.ineForm.client.form.widgets.datetime.IneDateGWT.Precision;

class OMO_OOField extends ListBox implements DateTimeFieldInterface {
	
	private final Precision PRECISION;

	private final IneDateGWT inedate;

	private final DateTimeFieldParentInterface parent;

	private HandlerRegistration hr_valuechange;

	
	public OMO_OOField(IneDateGWT date, DateTimeFieldParentInterface parent) {
		super(false);
		
		this.PRECISION=Precision.OMO_OO;
		this.inedate=date;
		this.parent=parent;
		

		for(String str : ConstantHelper.NAME_OF_MONTHS()) {
			addItem(str);
		}
	}

	@Override
	protected void onAttach() {
		super.onAttach();

		hr_valuechange=addChangeHandler(new MonthListFieldChangeHandler());
	}

	@Override
	protected void onDetach() {
		super.onDetach();

		if(hr_valuechange!=null) {
			hr_valuechange.removeHandler();
			hr_valuechange=null;
		}
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	private class MonthListFieldChangeHandler implements ChangeHandler {

		@SuppressWarnings("deprecation")
		@Override
		public void onChange(ChangeEvent event) {
			inedate.setDate(PRECISION, new Date(2000, getSelectedIndex(), 0));
			parent.childValueChanged(true, false);
		}
		
		
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
	
	@Override
	public boolean isNull() {
		return false;
	}


	@Override
	@SuppressWarnings("deprecation")
	public void refresh(boolean empty, boolean initialValue) {
		setSelectedIndex(inedate.getDateClone().getMonth());
	}

	@Override
	public boolean isTextBox() {
		return false;
	}

	@Override
	public boolean isFocusable() {
		return false;
	}

	@Override
	public boolean isInReadOnlyMode() {
		return false;
	}

}
