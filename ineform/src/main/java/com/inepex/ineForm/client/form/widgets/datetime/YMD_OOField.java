package com.inepex.ineForm.client.form.widgets.datetime;

import java.util.Date;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.widgets.datetime.IneDateGWT.Precision;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineFrame.shared.util.date.DateProvider;

class YMD_OOField extends AbstractField {

    // calendar
    private final boolean showcalendar;
    private PopupPanel popup;
    private DatePicker datepicker;
    private Image img_calendar;

    private final DateProvider dateProv;

    public YMD_OOField(
        DateProvider dateProv,
        IneDateGWT date,
        boolean showstepbuttons,
        int stepcount,
        boolean showcalendar,
        boolean usetextbox,
        DateTimeFieldParentInterface parent,
        boolean enableselectmanager) {
        super(date,
            Precision.YMD_OO,
            showstepbuttons,
            stepcount,
            usetextbox,
            parent,
            enableselectmanager);

        this.showcalendar = showcalendar;
        this.dateProv = dateProv;

        if (showcalendar) {
            img_calendar = new Image();
            datepicker = new I18nDatePicker();
            popup = new PopupPanel();
            popup.getElement().getStyle().setZIndex(1010); // front of
                                                           // ActionPopup
            popup.setWidget(datepicker);
            popup.setAutoHideEnabled(true);

            img_calendar.setResource(ResourceHelper.ineformRes().calendar());
            img_calendar.addStyleName(ResourceHelper.ineformRes().style().clickable());
            img_calendar.getElement().getStyle().setPaddingLeft(5,
                Unit.PX);
            img_calendar.getElement().getStyle().setPaddingRight(5,
                Unit.PX);
            img_calendar.getElement().getStyle().setPaddingRight(5,
                Unit.PX);
            img_calendar.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);

            if (IneFormProperties.IN_OLD_STYLE_COMPATIBILITY_MODE) {
                img_calendar.getElement().getStyle().setHeight(17,
                    Unit.PX);
                img_calendar.getElement().getStyle().setFloat(Float.LEFT);
            }
        }

        if (showcalendar)
            panel_main.insert(img_calendar,
                0);

        setEnabled(true);
    }

    @Override
    public boolean isInReadOnlyMode() {
        return !showcalendar && super.isInReadOnlyMode();
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        if (showcalendar) {
            registerHandler(img_calendar.addClickHandler(new CalendarButtonHandler()));
            registerHandler(datepicker.addValueChangeHandler(new DatePickerValueChangeHandler()));
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (showcalendar)
            if (enabled) {
                img_calendar.getElement().getStyle().setCursor(Cursor.POINTER);
                img_calendar.getElement().getStyle().setOpacity(1.0);
            } else {
                img_calendar.getElement().getStyle().setCursor(Cursor.DEFAULT);
                img_calendar.getElement().getStyle().setOpacity(0.3);
            }
    }

    private class CalendarButtonHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            if (enabled) {
                if (!CalendarUtil.isSameDate(inedate.getDateClone(),
                    new Date(
                        IneDateGWT.NULLDATE))) {
                    datepicker.setCurrentMonth(inedate.getDateClone());
                    datepicker.setValue(inedate.getDateClone());
                } else {
                    datepicker.setCurrentMonth(dateProv.getDate(System.currentTimeMillis()));
                    datepicker.setValue(dateProv.getDate(System.currentTimeMillis()));
                }
                popup.showRelativeTo(img_calendar);
            }
        }
    }

    private class DatePickerValueChangeHandler implements ValueChangeHandler<Date> {

        @Override
        public void onValueChange(ValueChangeEvent<Date> event) {
            inedate.setDate(PRECISION,
                event.getValue());
            popup.hide();
            parent.childValueChanged(true,
                false);
        }

    }
}
