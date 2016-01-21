package com.inepex.ineForm.client.table;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class DisableAbleCheckboxCell
        extends AbstractEditableCell<CheckedAndDisabled, CheckedAndDisabled> {

    private static final SafeHtml INPUT_CHECKED_DISABLED = SafeHtmlUtils.fromSafeConstant(
        "<input type=\"checkbox\" tabindex=\"-1\" checked disabled=\"disabled\"/>");
    private static final SafeHtml INPUT_UNCHECKED_DISABLED = SafeHtmlUtils
        .fromSafeConstant("<input type=\"checkbox\" tabindex=\"-1\" disabled=\"disabled\"/>");
    private static final SafeHtml INPUT_CHECKED = SafeHtmlUtils
        .fromSafeConstant("<input type=\"checkbox\" tabindex=\"-1\" checked/>");
    private static final SafeHtml INPUT_UNCHECKED = SafeHtmlUtils
        .fromSafeConstant("<input type=\"checkbox\" tabindex=\"-1\"/>");

    private final boolean dependsOnSelection;
    private final boolean handlesSelection;

    /**
     * Construct a new {@link CheckboxCell}.
     */
    public DisableAbleCheckboxCell() {
        this(false);
    }

    /**
     * Construct a new {@link CheckboxCell} that optionally controls selection.
     *
     * @param isSelectBox
     *            true if the cell controls the selection state
     * @deprecated use {@link #CheckboxCell(boolean, boolean)} instead
     */
    @Deprecated
    public DisableAbleCheckboxCell(boolean isSelectBox) {
        this(isSelectBox, isSelectBox);
    }

    /**
     * Construct a new {@link CheckboxCell} that optionally controls selection.
     *
     * @param dependsOnSelection
     *            true if the cell depends on the selection state
     * @param handlesSelection
     *            true if the cell modifies the selection state
     */
    public DisableAbleCheckboxCell(boolean dependsOnSelection, boolean handlesSelection) {
        super(BrowserEvents.CHANGE, BrowserEvents.KEYDOWN);
        this.dependsOnSelection = dependsOnSelection;
        this.handlesSelection = handlesSelection;
    }

    @Override
    public boolean dependsOnSelection() {
        return dependsOnSelection;
    }

    @Override
    public boolean handlesSelection() {
        return handlesSelection;
    }

    @Override
    public boolean isEditing(Context context, Element parent, CheckedAndDisabled value) {
        // A checkbox is never in "edit mode". There is no intermediate state
        // between checked and unchecked.
        return false;
    }

    @Override
    public void onBrowserEvent(
        Context context,
        Element parent,
        CheckedAndDisabled value,
        NativeEvent event,
        ValueUpdater<CheckedAndDisabled> valueUpdater) {
        String type = event.getType();

        boolean enterPressed = BrowserEvents.KEYDOWN.equals(type)
            && event.getKeyCode() == KeyCodes.KEY_ENTER;
        if (BrowserEvents.CHANGE.equals(type) || enterPressed) {
            InputElement input = parent.getFirstChild().cast();
            Boolean isChecked = input.isChecked();

            /*
             * Toggle the value if the enter key was pressed and the cell
             * handles selection or doesn't depend on selection. If the cell
             * depends on selection but doesn't handle selection, then ignore
             * the enter key and let the SelectionEventManager determine which
             * keys will trigger a change.
             */
            if (enterPressed && (handlesSelection() || !dependsOnSelection())) {
                isChecked = !isChecked;
                input.setChecked(isChecked);
            }

            /*
             * Save the new value. However, if the cell depends on the
             * selection, then do not save the value because we can get into an
             * inconsistent state.
             */
            if (value.getChecked() != isChecked && !dependsOnSelection()) {
                value.setChecked(isChecked);
                setViewData(context.getKey(), value);
            } else {
                clearViewData(context.getKey());
            }

            if (valueUpdater != null) {
                valueUpdater.update(value);
            }
        }
    }

    @Override
    public void render(Context context, CheckedAndDisabled value, SafeHtmlBuilder sb) {
        // Get the view data.
        Object key = context.getKey();
        CheckedAndDisabled viewData = getViewData(key);
        if (viewData != null && viewData.equals(value)) {
            clearViewData(key);
            viewData = null;
        }

        boolean checked = value != null
            && ((viewData != null) ? viewData.getChecked() : value.getChecked());
        boolean disabled = Boolean.TRUE.equals(value.getDisabled());

        if (checked && disabled) {
            sb.append(INPUT_CHECKED_DISABLED);
        } else if (!checked && disabled) {
            sb.append(INPUT_UNCHECKED_DISABLED);
        } else if (checked && !disabled) {
            sb.append(INPUT_CHECKED);
        } else if (!checked && !disabled) {
            sb.append(INPUT_UNCHECKED);
        }
    }
}
