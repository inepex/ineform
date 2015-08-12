package com.inepex.ineFrame.shared.util.date;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserDateCreator {

    private final DateProvider dateProvider;

    @Inject
    public UserDateCreator(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    /**
     * Creates {@link UserDate} with {@link System#currentTimeMillis()}
     * 
     * @return
     */
    public UserDate create() {
        return new UserDate(dateProvider, System.currentTimeMillis());
    }

    /**
     * Creates {@link UserDate} with selected GWT time value.
     */
    public UserDate createWithoutTransform(long time) {
        return new UserDate(dateProvider, time);
    }

    /**
     * Create {@link UserDate} using {@link DateProvider#getDate(Long)} to
     * transform to GWT time.
     */
    public UserDate createByTransformingDataTime(long time) {
        return new UserDate(dateProvider, dateProvider.getDate(time).getTime());
    }

}
