package com.inepex.ineFrame.server.di.jpa;

import jakarta.inject.Inject;
import com.google.inject.persist.PersistService;

public class PersistInitializer {

    @Inject
    PersistInitializer(PersistService persistService) {
        persistService.start();
    }

}