package com.inepex.ineom.server;

import org.junit.Assert;
import org.junit.Test;

public class JavaJsonDifferenceTest {

    @Test
    public void basic() {
        String difference = new JavaJsonDifference()
            .difference("{ \"prop1\" : \"val1\"}", "{ \"prop1\" : \"val2\"}");
        Assert.assertEquals("{\"prop1\":\"val2\"}", difference);
    }
}
