package com.inepex.ineFrame.shared.util.date;

import java.text.ParseException;

public interface DateFormatter {

    public String format(String pattern, Long date);

    public Long parseUiDate(String format, String localDateString) throws ParseException;
}
