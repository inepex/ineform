package com.inepex.ineForm.client.form.widgets.chooser;

import java.util.List;

import com.inepex.ineom.shared.Relation;

public interface Chooser {

    public void setSelected(List<Relation> selected);

    public void select(Item item, boolean reRender, boolean isChange);

    public void deselect(Item item, boolean reRender);

    public void selectAll();

    public void deselectAll();

    public List<Item> getValueRange();

    public List<Item> getSelected();

    public List<Relation> getChanged();

    public void moveUp(Item item);

    public void moveDown(Item item);

    public boolean isSupportsOrdering();

}
