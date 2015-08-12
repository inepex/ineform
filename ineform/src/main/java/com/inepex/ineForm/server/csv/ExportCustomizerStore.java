package com.inepex.ineForm.server.csv;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.render.TableFieldRenderer;

public class ExportCustomizerStore {

    private Map<String, ExportTableCustomizer> customizersByDescriptor = new HashMap<>();

    @Inject
    public ExportCustomizerStore() {}

    public void registerCustomizer(String descriptorName, ExportTableCustomizer customizer) {
        customizersByDescriptor.put(descriptorName, customizer);
    }

    public void customize(String descriptorName, TableFieldRenderer fieldRenderer) {
        if (customizersByDescriptor.containsKey(descriptorName)) {
            customizersByDescriptor.get(descriptorName).customize(fieldRenderer);
        }
    }

}
