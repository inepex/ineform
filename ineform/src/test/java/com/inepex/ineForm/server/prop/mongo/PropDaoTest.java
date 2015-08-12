package com.inepex.ineForm.server.prop.mongo;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class PropDaoTest {

    private static final String descriptor = "testObj";
    private static final Long object1 = 1L;
    private static final Long object2 = 2L;
    private static final Long object3 = 3L;
    private static final String group1 = "group1";
    private static final String group2 = "group2";

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    PropDao propDao = new PropDao("localhost", "", "");

    @Test
    public void testInsert() {
        String prop = "{ \"alma\" : \"korte\"}";
        propDao.setProp(descriptor, object1, group1, prop);
        String storedProp = propDao.getPropGroup(descriptor, object1, group1);
        Assert.assertEquals(prop, storedProp);
    }

    @Test
    public void testModify() {
        String prop = "{ \"alma\" : \"korte\"}";
        String prop2 = "{ \"alma\" : \"banan\"}";

        propDao.setProp(descriptor, object1, group1, prop);
        propDao.setProp(descriptor, object1, group1, prop2);

        String storedProp = propDao.getPropGroup(descriptor, object1, group1);
        Assert.assertEquals(prop2, storedProp);
    }

    @Test
    public void testFind() {
        String prop = "{ \"onRoad\" : true}";
        String propFalse = "{ \"onRoad\" : false}";

        propDao.setProp(descriptor, object1, group1, prop);
        propDao.setProp(descriptor, object2, group1, propFalse);
        propDao.setProp(descriptor, object2, group1, prop);

        List<Long> ids = propDao.findObjectIds(descriptor, "{\"" + group1 + ".onRoad\" : true }");
        Assert.assertEquals(2, ids.size());
    }

    private class PropRunnable implements Runnable {

        private Long id;

        public PropRunnable(Long id) {
            super();
            this.id = id;
        }

        @Override
        public void run() {
            propDao.setProp(descriptor, id, group1, "{ \"onRoad\" : true}");
        }

    }

    // @Test
    public void testThreads() throws Exception {
        for (Long id = 1L; id <= 100L; id++) {
            new Thread(new PropRunnable(id)).start();
        }
        Thread.sleep(2000);
        System.out.println("check thread");
    }

    @After
    public void clean() {
        propDao.removeProps(descriptor, object1);
        propDao.removeProps(descriptor, object2);
        propDao.removeProps(descriptor, object3);
    }

}
