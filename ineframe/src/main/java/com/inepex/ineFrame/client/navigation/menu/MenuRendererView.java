package com.inepex.ineFrame.client.navigation.menu;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.page.InePage;
import com.inepex.ineFrame.client.widgets.ListItemWidget;

@Singleton
public class MenuRendererView extends LayoutPanel implements MenuRenderer.View {

    protected List<MenuOneLevelView> levels = new ArrayList<MenuOneLevelView>();

    @Inject
    public MenuRendererView() {
        levels.add(new MenuOneLevelView(this, 0, false));
    }

    @Override
    public void clearLevel(int level) {
        MenuOneLevelView lastLevel = levels.get(levels.size() - 1);
        if (!lastLevel.isSelectorRendered()) {
            lastLevel.getTarget().clear();
        }

        if (level == 0) {
            levels.get(level).init();
        }
        for (int i = level; i < levels.size(); i++) {
            if (i != 0) {
                levels.get(i).removeFromParent();
                levels.remove(i);
            }
        }

    }

    private MenuOneLevelView getOneLevel(int level) {
        if (levels.size() <= level) {
            levels.add(new MenuOneLevelView(getOneLevel(level - 1).getTarget(), level, false));
        }
        return levels.get(level);
    }

    @Override
    public void showSelector(IsWidget w, int level, boolean asPage) {
        if (asPage) {
            getOneLevel(level).getTarget().add(w);
        } else {
            getOneLevel(level).setSelector(w);
        }
    }

    @Override
    public Tab createTab(String menuName, int level) {
        return createTab(menuName, null, level);
    }

    @Override
    public Tab createTab(String menuName, Image icon, int level) {
        MenuItemView barWidget = new MenuItemView(menuName, icon);
        getOneLevel(level).getMenu().add(barWidget);
        return barWidget;
    }

    @Override
    public void appendMenuWidget(Widget widget, int level) {
        getOneLevel(level).getMenu().add(new ListItemWidget(widget));
    }

    @Override
    public void showPage(InePage page) {
        MenuOneLevelView oneLevel = levels.get(levels.size() - 1);
        oneLevel.getTarget().clear();
        oneLevel.getTarget().add(page.asWidget());
    }

}
