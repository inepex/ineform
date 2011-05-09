package com.inepex.ineForm.client.form.widgets.datetime;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.inepex.ineForm.client.form.widgets.datetime.IneDateGWT.Precision;
import com.inepex.ineForm.client.resources.IneResources;
import com.inepex.ineFrame.shared.util.DateProvider;

class YMD_OOField extends AbstractField {
	
	//calendar
	private final boolean showcalendar;
	private PopupPanel popup;
	private DatePicker datepicker;
	private Image img_calendar;
	
	private final DateProvider dateProv;
	
	public YMD_OOField(DateProvider dateProv, IneDateGWT date, boolean showstepbuttons, int stepcount, boolean showcalendar, boolean usetextbox, DateTimeFieldParentInterface parent, boolean enableselectmanager) {
		super(date, Precision.YMD_OO, showstepbuttons, stepcount,usetextbox, parent, enableselectmanager);
		
		this.showcalendar=showcalendar;
		this.dateProv = dateProv;
		
		if(showcalendar) {
			img_calendar=new Image();
			datepicker= new I18nDatePicker();
			popup=new PopupPanel();
			popup.setWidget(datepicker);
			popup.setAutoHideEnabled(true);
		}
		
		if(showcalendar) panel_main.add(img_calendar);
		
		setEnabled(true);
	}
	
	@Override
	public boolean isInReadOnlyMode() {
		return !showcalendar&& super.isInReadOnlyMode();
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		if(showcalendar) {
			registerHandler(img_calendar.addClickHandler(new CalendarButtonHandler()));
			registerHandler(datepicker.addValueChangeHandler(new DatePickerValueChangeHandler()));
		}
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if(showcalendar)
			if(enabled) {
				img_calendar.setResource(IneResources.INSTANCE.calendar());
				img_calendar.addStyleName("clickable");
			} else {
				img_calendar.setResource(IneResources.INSTANCE.calendar_disabled());
				img_calendar.removeStyleName("clickable");
			}
	}
	
	private class CalendarButtonHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(enabled) {
				if(!CalendarUtil.isSameDate(inedate.getDateClone(), new Date(IneDateGWT.NULLDATE)))  {
					datepicker.setCurrentMonth(inedate.getDateClone());
					datepicker.setValue(inedate.getDateClone());
				} else {
					datepicker.setCurrentMonth(dateProv.getDate(new Date().getTime()));
					datepicker.setValue(dateProv.getDate(new Date().getTime()));
				}
				popup.showRelativeTo(img_calendar);
			}
		}
	}
	
	private class DatePickerValueChangeHandler implements ValueChangeHandler<Date> {

		@Override
		public void onValueChange(ValueChangeEvent<Date> event) {
		    inedate.setDate(PRECISION, event.getValue());
		    popup.hide();
		    parent.childValueChanged(true, false);
		}
		
	}
}

