package com.inepex.ineFrame.shared.util.date;

public class UntrasformedLocalDate extends LocalDate<UntrasformedLocalDate> {

    public UntrasformedLocalDate() {
        this(System.currentTimeMillis());
    }

    public UntrasformedLocalDate(long time) {
        super(time);
    }

    @Override
    protected UntrasformedLocalDate newInstance(long time) {
        return new UntrasformedLocalDate(time);
    }

    public long getTimeInMillis() {
        return getTime();
    }
}