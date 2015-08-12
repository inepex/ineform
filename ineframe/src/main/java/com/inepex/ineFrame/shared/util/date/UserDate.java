package com.inepex.ineFrame.shared.util.date;

public class UserDate extends LocalDate<UserDate> {

    private final DateProvider dateProvider;

    protected UserDate(DateProvider dateProvider, long time) {
        super(time);
        this.dateProvider = dateProvider;
    }

    @Override
    protected UserDate newInstance(long time) {
        return new UserDate(dateProvider, time);
    }

    public long getDataTime() {
        return dateProvider.whatMeansTyped(getTime()).getTime();
    }

    public long getGWTTime() {
        return getTime();
    }
}