package com.inepex.ineFrame.client.navigation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.inepex.ineFrame.client.navigation.places.DummyPageProvider;
import com.inepex.ineFrame.client.navigation.places.SimpleCachingPlace;

public class UpdateHierarchicalTokensSimpleTest {

    @Test
    public void testNothingToDo() {
        PlaceNode pn = mock(PlaceNode.class);
        PlaceHandlerHelper.updateHierarchicalTokens("", pn);

        verifyZeroInteractions(pn);
    }

    /**
     * 
     * by a static current place: hierarchy place id will be null or same as
     * node id
     * 
     */
    @Test
    public void test() {
        TestPlaceHierarchyProv prov = new TestPlaceHierarchyProv();
        prov.createPlaceHierarchy();

        PlaceHandlerHelper.updateHierarchicalTokens("A", prov.getPlaceRoot());

        Assert
            .assertEquals(
                "A",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "B",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("B")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "C",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("C")
                    .getNodeElement()
                    .getHierarchicalToken());

        Assert
            .assertEquals(
                "A/a",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/a")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/b",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/b")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/c",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/c")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/d",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/e",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/e")
                    .getNodeElement()
                    .getHierarchicalToken());

        Assert
            .assertNull(
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d/1")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertNull(
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d/2")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertNull(
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d/3")
                    .getNodeElement()
                    .getHierarchicalToken());

    }

    @Test
    public void test2() {
        TestPlaceHierarchyProv prov = new TestPlaceHierarchyProv();
        prov.createPlaceHierarchy();

        PlaceHandlerHelper.updateHierarchicalTokens("A/a", prov.getPlaceRoot());

        Assert
            .assertEquals(
                "A",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "B",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("B")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "C",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("C")
                    .getNodeElement()
                    .getHierarchicalToken());

        Assert
            .assertEquals(
                "A/a",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/a")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/b",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/b")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/c",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/c")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/d",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/e",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/e")
                    .getNodeElement()
                    .getHierarchicalToken());

        Assert
            .assertNull(
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d/1")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertNull(
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d/2")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertNull(
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d/3")
                    .getNodeElement()
                    .getHierarchicalToken());

    }

    @Test
    public void test3() {
        TestPlaceHierarchyProv prov = new TestPlaceHierarchyProv();
        prov.createPlaceHierarchy();

        PlaceHandlerHelper.updateHierarchicalTokens("A/d", prov.getPlaceRoot());

        Assert
            .assertEquals(
                "A",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "B",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("B")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "C",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("C")
                    .getNodeElement()
                    .getHierarchicalToken());

        Assert
            .assertEquals(
                "A/a",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/a")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/b",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/b")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/c",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/c")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/d",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/e",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/e")
                    .getNodeElement()
                    .getHierarchicalToken());

        Assert
            .assertEquals(
                "A/d/1",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d/1")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/d/2",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d/2")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/d/3",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d/3")
                    .getNodeElement()
                    .getHierarchicalToken());

    }

    @Test
    public void test4() {
        TestPlaceHierarchyProv prov = new TestPlaceHierarchyProv();
        prov.createPlaceHierarchy();

        PlaceHandlerHelper.updateHierarchicalTokens("A/d/3", prov.getPlaceRoot());

        Assert
            .assertEquals(
                "A",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "B",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("B")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "C",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("C")
                    .getNodeElement()
                    .getHierarchicalToken());

        Assert
            .assertEquals(
                "A/a",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/a")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/b",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/b")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/c",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/c")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/d",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/e",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/e")
                    .getNodeElement()
                    .getHierarchicalToken());

        Assert
            .assertEquals(
                "A/d/1",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d/1")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/d/2",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d/2")
                    .getNodeElement()
                    .getHierarchicalToken());
        Assert
            .assertEquals(
                "A/d/3",
                prov
                    .getPlaceRoot()
                    .findNodeByHierarchicalId("A/d/3")
                    .getNodeElement()
                    .getHierarchicalToken());

    }

    private class TestPlaceHierarchyProv extends DefaultPlaceHierarchyProvider {

        @Override
        public void createPlaceHierarchy() {
            placeRoot
                .addChildGC("A", new SimpleCachingPlace(new DummyPageProvider()))
                .addChild("a", new SimpleCachingPlace(new DummyPageProvider()))
                .addChild("b", new SimpleCachingPlace(new DummyPageProvider()))
                .addChild("c", new SimpleCachingPlace(new DummyPageProvider()))
                .addChildGC("d", new SimpleCachingPlace(new DummyPageProvider()))
                .addChild("1", new SimpleCachingPlace(new DummyPageProvider()))
                .addChild("2", new SimpleCachingPlace(new DummyPageProvider()))
                .addChild("3", new SimpleCachingPlace(new DummyPageProvider()))
                .getParent()
                .addChild("e", new SimpleCachingPlace(new DummyPageProvider()))
                .getParent()
                .addChild("B", new SimpleCachingPlace(new DummyPageProvider()))
                .addChild("C", new SimpleCachingPlace(new DummyPageProvider()));
        }

        @Override
        public List<String> getCurrentMenuRoot() {
            throw new RuntimeException();
        }
    }
}
