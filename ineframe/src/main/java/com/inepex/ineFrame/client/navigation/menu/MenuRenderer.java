package com.inepex.ineFrame.client.navigation.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.OnClickedLogic;
import com.inepex.ineFrame.client.navigation.PlaceHandlerHelper;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.Tab;
import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineFrame.client.navigation.places.WidgetPlace;
import com.inepex.ineFrame.client.page.InePage;
import com.inepex.ineom.shared.descriptor.Node;

@Singleton
public class MenuRenderer {

    public static interface View {

        /**
         * level = 0 -> clears the whole panel
         * 
         */
        public void clearLevel(int level);

        public Tab createTab(String menuName, int level);

        public Tab createTab(String menuName, Image icon, int level);

        public void appendMenuWidget(Widget widget, int level);

        public void showSelector(IsWidget w, int level, boolean asPage);

        public void showPage(InePage page);

        static interface Tab {
            public void setOnClickedLogic(OnClickedLogic logic);

            public void setItemVisible(boolean visible);

            public void setEnabled(boolean enabled);

            public void setClickable(boolean clickable);

            public void setSelected(boolean selected);

            public void renderToRightSide();
        }

        public IsWidget asWidget();
    }

    private final PlaceHierarchyProvider hierarchyProvider;
    private final EventBus eventBus;
    private final AuthManager authManager;

    private final Provider<View> view;

    private String lastTokenPlacePart = null;

    private List<String> lastTokenPartsFromMenuRoot = null;

    private MenuFilter filter;

    @Inject
    public MenuRenderer(
        PlaceHierarchyProvider hierarchyProvider,
        EventBus eventBus,
        Provider<View> view,
        AuthManager authManager) {

        this.hierarchyProvider = hierarchyProvider;
        this.eventBus = eventBus;
        this.authManager = authManager;
        this.view = view;
    }

    /**
     * 
     * menurenderer shows the same level nodes by the "selected node line"
     * 
     * menurenderer does not show nodes that doesn't have menuName
     * 
     * menurenderer does not show the selected node's children by default
     * 
     */
    public void realizeNewPlaceOnMenu(InePlace place, Map<String, String> urlParams) {
        String newTokenPlacePart = PlaceHandlerHelper.getPlacePart(place.getHierarchicalToken());

        if (newTokenPlacePart.equals(lastTokenPlacePart)) {
            return;
        }
        List<String> tokens = new ArrayList<String>(
            Arrays.asList(newTokenPlacePart.split(PlaceHandlerHelper.regExp(Node.ID_SEPARATOR))));
        Node<InePlace> pointer = getRootNode(tokens); // also removes unneeded
                                                      // parts of tokens list
        int levelOfChange = PlaceHandlerHelper.levelOfChange(lastTokenPartsFromMenuRoot, tokens);

        view.get().clearLevel(levelOfChange);
        lastTokenPlacePart = newTokenPlacePart;
        lastTokenPartsFromMenuRoot = tokens;
        if (place.isWithoutMenu()) {
            return;
        }

        pointer = getFirstChangedNode(pointer, levelOfChange, tokens);

        // iterate through the children of pointer node, then the children of
        // selected node of the first level and so on.
        int level = 0;
        for (int i = levelOfChange; i < tokens.size()
            || pointer != null && pointer.getNodeElement() != null
                && pointer.getNodeElement().isShowChildreWhenActive() && i == tokens.size(); i++) {
            level = i;
            Node<InePlace> selectednode = null;

            if (pointer.hasChildren()) {
                for (Node<InePlace> node : pointer.getChildren()) {
                    if (node.getNodeElement().isHideWhenLoggedIn() && authManager.isUserLoggedIn())
                        continue;
                    if (userHasNoRight(node))
                        continue;
                    if (filter != null && filter.filter(node))
                        continue;

                    if (handleWidgetPlace(node, urlParams, i))
                        continue;

                    boolean selected = isSelected(i, tokens, node);
                    boolean visible = isVisible(node, selected);

                    createTab(node, tokens, selected, visible, i);

                    if (selected) {
                        selectednode = node;
                    }
                }
            }

            pointer = selectednode;

            if (pointer != null) {
                renderSelectorWidget(pointer, level);
            }
        }

    }

    private Node<InePlace> getRootNode(List<String> tokens) {
        Node<InePlace> pointer = hierarchyProvider.getPlaceRoot();
        if (hierarchyProvider.getCurrentMenuRoot() != null) {
            for (String s : hierarchyProvider.getCurrentMenuRoot()) {
                pointer = pointer.findNodeById(s);
                if (pointer == null)
                    throw new RuntimeException();

                String token = tokens.remove(0);
                if (!token.equals(s))
                    throw new RuntimeException(token + " != " + s);
            }
        }
        return pointer;
    }

    private Node<InePlace> getFirstChangedNode(
        Node<InePlace> pointer,
        int levelOfChange,
        List<String> tokens) {
        for (int i = 0; i < levelOfChange; i++) {
            boolean foundSelected = false;
            for (Node<InePlace> child : pointer.getChildren()) {
                if (isSelected(i, tokens, child)) {
                    pointer = child;
                    foundSelected = true;
                }
            }
            if (!foundSelected) {
                throw new RuntimeException("Node not found for nodeId: " + tokens.get(i));
            }
        }
        return pointer;
    }

    private void renderSelectorWidget(Node<InePlace> selectednode, int level) {
        if (selectednode.getNodeElement() != null
            && selectednode.getNodeElement() instanceof ParamPlace) {
            ParamPlace place = ((ParamPlace) selectednode.getNodeElement());
            if (place.isSelectorPage()) {
                view.get().showSelector(place.getAssociatedPage().asWidget(), level, false);
            } else {
                view.get().showSelector(place.getAssociatedPage().asWidget(), level, true);
            }
        }
    }

    private boolean userHasNoRight(Node<InePlace> node) {
        return !(authManager instanceof NoAuthManager)
            && !authManager.doUserHaveAnyOfRoles(node.getNodeElement().getRolesAllowedInArray());
    }

    private
        boolean
        handleWidgetPlace(Node<InePlace> node, Map<String, String> urlParams, int level) {
        if (node.getNodeElement() instanceof WidgetPlace) {
            WidgetPlace wp = (WidgetPlace) node.getNodeElement();
            if (wp.isWidget(urlParams))
                view.get().appendMenuWidget(wp.getWidget(urlParams), level);
            return true;
        }
        return false;
    }

    private boolean isSelected(int i, List<String> tokens, Node<InePlace> node) {
        return i < tokens.size() && node.getNodeId().equals(tokens.get(i));
    }

    private boolean isVisible(Node<InePlace> node, boolean selected) {
        return !(node.getNodeElement().isOnlyVisibleWhenActive() && !selected
            || node.getNodeElement().getMenuName() == null
            || node.getNodeElement().getMenuName().length() < 1);
    }

    private void createTab(
        final Node<InePlace> node,
        List<String> tokens,
        boolean selected,
        boolean visible,
        int level) {
        if (node.getNodeElement().getMenuName() == null)
            return;
        Tab tab = view.get().createTab(
            node.getNodeElement().getMenuName(),
            node.getNodeElement().getIcon(),
            level);

        // tab.setClickable((!selected || level!=tokens.size()-1) && visible);
        tab.setClickable(visible);
        tab.setSelected(selected);
        tab.setEnabled(true); // TODO implement enabled-disabled logic
        tab.setItemVisible(visible);
        if (node.getNodeElement().isRenderOnRightSide())
            tab.renderToRightSide();

        tab.setOnClickedLogic(new OnClickedLogic() {

            @Override
            public void doLogic() {
                PlaceRequestEvent pre = new PlaceRequestEvent(
                    node.getNodeElement().getHierarchicalToken());
                eventBus.fireEvent(pre);
            }
        });
    }

    public void showPage(InePlace place, InePage page) {
        boolean isParamPlace = place instanceof ParamPlace;
        // boolean isSelectorPage = ((ParamPlace)place).isSelectorPage();
        if (!isParamPlace) {
            view.get().showPage(page);
        }
    }

    public void setFilter(MenuFilter filter) {
        this.filter = filter;
    }

}
