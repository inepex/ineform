package com.inepex.ineForm.client.form.widgets.datetime;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class IneDateGWTTest {

    @Test
    @SuppressWarnings("deprecation")
    public void addMonthTest() {
        Date date = new Date(2012 - 1900, 2, 30);
        System.out.println(date.toString());

        IneDateGWT.addMonthsToDateSafe(date, -1);

        Assert.assertEquals(1, date.getMonth());
        System.out.println(date.toString());
    }
}
