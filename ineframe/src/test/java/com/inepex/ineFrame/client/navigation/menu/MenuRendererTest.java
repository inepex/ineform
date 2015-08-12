package com.inepex.ineFrame.client.navigation.menu;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.user.client.ui.Image;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.navigation.DefaultPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.places.DummyPageProvider;
import com.inepex.ineFrame.client.navigation.places.SimpleCachingPlace;
import com.inepex.ineom.shared.util.SharedUtil;

public class MenuRendererTest extends MenuRendererTestBase {

    PlainPlaceHierarchyProv phProvider;

    @Before
    public void init() {
        phProvider = new PlainPlaceHierarchyProv();
        phProvider.createPlaceHierarchy();
        initMocks(phProvider);
    }

    /**
     * display nothing (a first level place is selected)
     * 
     */
    @Test
    public void testFirstLevelPlace() {
        phProvider.parentPlace.setHierarchicalToken("MenuParent");
        renderer.realizeNewPlaceOnMenu(phProvider.parentPlace, null);

        verify(view, times(1)).clearLevel(anyInt());
        verify(view, never()).createTab(anyString(), Mockito.any(Image.class), anyInt());
    }

    /**
     * display 5 tabs setting selected, visible... proterties fine
     */
    @Test
    public void testPlainPlaceIsSelected() {
        PlainPlaceHierarchyProv phProvider = new PlainPlaceHierarchyProv();
        phProvider.createPlaceHierarchy();

        MenuRenderer renderer =
            new MenuRenderer(phProvider, eventBus, viewProv, new NoAuthManager());

        phProvider.plainPlace.setHierarchicalToken("MenuParent/plainChild");
        renderer.realizeNewPlaceOnMenu(phProvider.plainPlace, null);

        verify(view, times(1)).clearLevel(anyInt());

        // 3 visible menu item
        verify(view, times(3)).createTab(anyString(), Mockito.any(Image.class), anyInt());
        verify(view, times(3)).createTab(anyString(), Mockito.any(Image.class), eq(0));

        // has menu name
        verifyTab(tabs[0], true, true, false);

        // has menu name 2
        verifyTab(tabs[1], true, true, false);

        // only visible when... and has name
        verifyTab(tabs[2], false, false, false);
    }

    /**
     * display 5 tabs setting selected, visible... proterties fine
     * 
     */
    @Test
    public void testOnlyVisiblePlaceIsSelected() {
        PlainPlaceHierarchyProv phProvider = new PlainPlaceHierarchyProv();
        phProvider.createPlaceHierarchy();

        MenuRenderer renderer =
            new MenuRenderer(phProvider, eventBus, viewProv, new NoAuthManager());

        phProvider.onlyVisibleWhenActiveAndHasName
            .setHierarchicalToken("MenuParent/onlyVisibleWhenActiveAndHasName");
        renderer.realizeNewPlaceOnMenu(phProvider.onlyVisibleWhenActiveAndHasName, null);

        verify(view, times(1)).clearLevel(anyInt());

        // 3 visible menu item
        verify(view, times(3)).createTab(anyString(), Mockito.any(Image.class), anyInt());
        verify(view, times(3)).createTab(anyString(), Mockito.any(Image.class), eq(0));

        // has menu name
        verifyTab(tabs[0], true, true, false);

        // has menu name 2
        verifyTab(tabs[1], true, true, false);

        // only visible when... and has names
        verifyTab(tabs[2], true, true, true);
    }

    private class PlainPlaceHierarchyProv extends DefaultPlaceHierarchyProvider {

        public final InePlace parentPlace = new SimpleCachingPlace(new DummyPageProvider());

        public final InePlace plainPlace = new SimpleCachingPlace(new DummyPageProvider());

        public final InePlace namedPlace = new SimpleCachingPlace(new DummyPageProvider())
            .setMenuName("Has menu name");

        public final InePlace namedPlace2 = new SimpleCachingPlace(new DummyPageProvider())
            .setMenuName("Has menu name 2");

        public final InePlace visibleWhenActive = new SimpleCachingPlace(new DummyPageProvider())
            .setOnlyVisibleWhenActive(true);

        public final InePlace onlyVisibleWhenActiveAndHasName = new SimpleCachingPlace(
            new DummyPageProvider())
            .setOnlyVisibleWhenActive(true)
            .setShowChildrenWhenActive(false)
            .setMenuName("Name of menu");

        @Override
        public void createPlaceHierarchy() {
            placeRoot
                .addChildGC("MenuParent", parentPlace)
                .addChildGC("plainChild", plainPlace)
                .addChild("youCanNotSeeInMenuBar", new SimpleCachingPlace(new DummyPageProvider()))
                .getParent()
                .addChild("hasMenuName", namedPlace)
                .addChild("hasMenuName2", namedPlace2)
                .addChild("onlyVisibleWhenActive", visibleWhenActive)
                .addChildGC("onlyVisibleWhenActiveAndHasName", onlyVisibleWhenActiveAndHasName)
                .addChild(
                    "youCanNotSeeInMenuBarTOO",
                    new SimpleCachingPlace(new DummyPageProvider()))
                .getParent()
                .getParent();
        }

        @Override
        public List<String> getCurrentMenuRoot() {
            return SharedUtil.Li("MenuParent");
        }
    }
}
