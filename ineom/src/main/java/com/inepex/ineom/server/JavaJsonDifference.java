package com.inepex.ineom.server;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.inepex.ineom.shared.JsonDifference;

public class JavaJsonDifference implements JsonDifference {

    private static final Logger _logger = LoggerFactory.getLogger(JavaJsonDifference.class);

    @Inject
    public JavaJsonDifference() {}

    @Override
    public String difference(String original, String modified) {
        try {
            ObjectMapper m = new ObjectMapper();

            ObjectNode origNode = (ObjectNode) m.readTree("{}");
            if (original != null) {
                origNode = (ObjectNode) m.readTree(original);
            }
            ObjectNode modifNode = (ObjectNode) m.readTree("{}");
            if (modified != null) {
                modifNode = (ObjectNode) m.readTree(modified);
            }

            ObjectNode diffNode = (ObjectNode) m.readTree("{}");

            Iterator<String> originalFieldNames = origNode.fieldNames();
            while (originalFieldNames.hasNext()) {
                String key = originalFieldNames.next();
                if (!modifNode.has(key)) { // deleted
                    diffNode.put(key, NullNode.getInstance());
                } else if (!modifNode.get(key).toString().equals(origNode.get(key).toString())) { // modified
                    diffNode.put(key, modifNode.get(key));
                }
            }

            Iterator<String> modifiedFieldNames = modifNode.fieldNames();
            while (modifiedFieldNames.hasNext()) {
                String key = modifiedFieldNames.next();
                if (!origNode.has(key)) { // added
                    diffNode.put(key, modifNode.get(key));
                }
            }
            return diffNode.toString();
        } catch (Exception e) {
            e.printStackTrace();
            _logger.error("Json parsing error", e);
            return "{}";
        }
    }

}
