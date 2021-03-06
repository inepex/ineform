package com.inepex.ineFrame.client.navigation.menu;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Provider;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.navigation.DefaultPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.Tab;
import com.inepex.ineFrame.client.navigation.places.DummyPageProvider;
import com.inepex.ineFrame.client.navigation.places.SimpleCachingPlace;
import com.inepex.ineom.shared.util.SharedUtil;

public class MenuRendererShowChildreWhenActiveTest {

    /**
     * display nothing (a first level place is selected)
     * 
     */
    @Test
    public void testFirstLevelPlace() {

        EventBus eventBus = mock(EventBus.class);
        final MenuRenderer.View view = mock(MenuRenderer.View.class);

        Provider<MenuRenderer.View> viewProv = new Provider<MenuRenderer.View>() {

            @Override
            public View get() {
                return view;
            }
        };

        PlaceHierarchyProv phProvider = new PlaceHierarchyProv();
        phProvider.createPlaceHierarchy();

        MenuRenderer renderer = new MenuRenderer(
            phProvider,
            eventBus,
            viewProv,
            new NoAuthManager());

        phProvider.parentPlace.setHierarchicalToken("MenuParent");
        renderer.realizeNewPlaceOnMenu(phProvider.parentPlace, null);

        verify(view, times(1)).clearLevel(anyInt());
        verify(view, never()).createTab(anyString(), Mockito.any(Image.class), anyInt());
    }

    /**
     * displays 4 tabs setting selected, visible... proterties fine
     * 
     * and displays 1 child tab additional
     * 
     */
    @Test
    public void testPlainPlaceIsSelected() {

        EventBus eventBus = mock(EventBus.class);
        final MenuRenderer.View.Tab[] tabs = new MenuRenderer.View.Tab[5];
        final MenuRenderer.View view = mock(MenuRenderer.View.class);
        Provider<MenuRenderer.View> viewProv = new Provider<MenuRenderer.View>() {

            @Override
            public View get() {
                return view;
            }
        };
        when(view.createTab(anyString(), Mockito.any(Image.class), anyInt()))
            .thenAnswer(new Answer<MenuRenderer.View.Tab>() {

                int i = 0;

                @Override
                public Tab answer(InvocationOnMock invocation) throws Throwable {
                    MenuRenderer.View.Tab tab = mock(MenuRenderer.View.Tab.class);
                    tabs[i++] = tab;
                    return tab;
                }
            });

        PlaceHierarchyProv phProvider = new PlaceHierarchyProv();
        phProvider.createPlaceHierarchy();

        MenuRenderer renderer = new MenuRenderer(
            phProvider,
            eventBus,
            viewProv,
            new NoAuthManager());

        phProvider.plainPlace.setHierarchicalToken("MenuParent/plainChild");
        renderer.realizeNewPlaceOnMenu(phProvider.plainPlace, null);

        // 4 menu item
        verify(view, times(3)).createTab(anyString(), Mockito.any(Image.class), anyInt());
        verify(view, times(2)).createTab(anyString(), Mockito.any(Image.class), eq(0));
        verify(view, times(1)).createTab(anyString(), Mockito.any(Image.class), eq(1));

        // visibleItem1
        verify(tabs[2], times(1)).setClickable(true);
        verify(tabs[2], never()).setClickable(false);
        verify(tabs[2], times(1)).setEnabled(true);
        verify(tabs[2], never()).setEnabled(false);
        verify(tabs[2], times(1)).setItemVisible(true);
        verify(tabs[2], never()).setItemVisible(false);
        verify(tabs[2], times(1)).setSelected(false);
        verify(tabs[2], never()).setSelected(true);
    }

    /**
     * display 4 tabs setting selected, visible... proterties fine showing
     * children
     * 
     */
    @Test
    public void testOnlyVisiblePlaceIsSelected() {

        EventBus eventBus = mock(EventBus.class);
        final MenuRenderer.View.Tab[] tabs = new MenuRenderer.View.Tab[6];
        final MenuRenderer.View view = mock(MenuRenderer.View.class);

        Provider<MenuRenderer.View> viewProv = new Provider<MenuRenderer.View>() {

            @Override
            public View get() {
                return view;
            }
        };

        when(view.createTab(anyString(), Mockito.any(Image.class), anyInt()))
            .thenAnswer(new Answer<MenuRenderer.View.Tab>() {

                int i = 0;

                @Override
                public Tab answer(InvocationOnMock invocation) throws Throwable {
                    MenuRenderer.View.Tab tab = mock(MenuRenderer.View.Tab.class);
                    tabs[i++] = tab;
                    return tab;
                }
            });

        PlaceHierarchyProv phProvider = new PlaceHierarchyProv();
        phProvider.createPlaceHierarchy();

        MenuRenderer renderer = new MenuRenderer(
            phProvider,
            eventBus,
            viewProv,
            new NoAuthManager());

        phProvider.onlyVisibleWhenActiveAndHasName
            .setHierarchicalToken("MenuParent/onlyVisibleWhenActiveAndHasName");
        renderer.realizeNewPlaceOnMenu(phProvider.onlyVisibleWhenActiveAndHasName, null);

        verify(view, times(1)).clearLevel(anyInt());

        // 4 menu item
        verify(view, times(4)).createTab(anyString(), Mockito.any(Image.class), anyInt());
        verify(view, times(2)).createTab(anyString(), Mockito.any(Image.class), eq(0));
        verify(view, times(2)).createTab(anyString(), Mockito.any(Image.class), eq(1));

        // visibleItem2
        verify(tabs[2], times(1)).setClickable(true);
        verify(tabs[2], never()).setClickable(false);
        verify(tabs[2], times(1)).setEnabled(true);
        verify(tabs[2], never()).setEnabled(false);
        verify(tabs[2], times(1)).setItemVisible(true);
        verify(tabs[2], never()).setItemVisible(false);
        verify(tabs[2], times(1)).setSelected(false);
        verify(tabs[2], never()).setSelected(true);

        // visibleItem3
        verify(tabs[3], times(1)).setClickable(true);
        verify(tabs[3], never()).setClickable(false);
        verify(tabs[3], times(1)).setEnabled(true);
        verify(tabs[3], never()).setEnabled(false);
        verify(tabs[3], times(1)).setItemVisible(true);
        verify(tabs[3], never()).setItemVisible(false);
        verify(tabs[3], times(1)).setSelected(false);
        verify(tabs[3], never()).setSelected(true);

    }

    private class PlaceHierarchyProv extends DefaultPlaceHierarchyProvider {

        public final InePlace parentPlace = new SimpleCachingPlace(new DummyPageProvider());

        public final InePlace plainPlace = new SimpleCachingPlace(new DummyPageProvider())
            .setShowChildrenWhenActive(true);

        public final InePlace namedPlace = new SimpleCachingPlace(new DummyPageProvider())
            .setMenuName("Has menu name");

        public final InePlace visibleWhenActive = new SimpleCachingPlace(new DummyPageProvider())
            .setOnlyVisibleWhenActive(true);

        public final InePlace onlyVisibleWhenActiveAndHasName = new SimpleCachingPlace(
            new DummyPageProvider())
                .setOnlyVisibleWhenActive(true)
                .setShowChildrenWhenActive(true)
                .setMenuName("Name of menu");

        @Override
        public void createPlaceHierarchy() {
            placeRoot
                .addChildGC("MenuParent", parentPlace)
                .addChildGC("plainChild", plainPlace)
                .addChild(
                    "visibleItem1",
                    new SimpleCachingPlace(new DummyPageProvider()).setMenuName("Visible 1"))
                .getParent()
                .addChild("hasMenuName", namedPlace)
                .addChild("onlyVisibleWhenActive", visibleWhenActive)
                .addChildGC("onlyVisibleWhenActiveAndHasName", onlyVisibleWhenActiveAndHasName)
                .addChild(
                    "visibleItem2",
                    new SimpleCachingPlace(new DummyPageProvider()).setMenuName("visible2"))
                .addChildGC(
                    "visibleItem3",
                    new SimpleCachingPlace(new DummyPageProvider()).setMenuName("visible3"))
                .addChild(
                    "invisibleToo",
                    new SimpleCachingPlace(new DummyPageProvider()).setMenuName("invisibleToo1"))
                .getParent()
                .getParent()
                .getParent();
        }

        @Override
        public List<String> getCurrentMenuRoot() {
            return SharedUtil.Li("MenuParent");
        }
    }
}
