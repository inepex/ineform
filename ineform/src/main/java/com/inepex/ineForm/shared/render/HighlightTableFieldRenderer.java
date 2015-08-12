package com.inepex.ineForm.shared.render;

import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class HighlightTableFieldRenderer implements TableFieldRenderer {

    private final TableFieldRenderer tableFieldRenderer;
    private final String beginOfHighlight;
    private final String endOfHighlight;

    private String highlightText;

    public HighlightTableFieldRenderer(
        TableFieldRenderer tableFieldRenderer,
        String beginOfHighlight,
        String endOfHighlight) {
        this.tableFieldRenderer = tableFieldRenderer;
        this.beginOfHighlight = beginOfHighlight;
        this.endOfHighlight = endOfHighlight;
    }

    public void setHighlightText(String highlightText) {
        this.highlightText = highlightText;
    }

    @Override
    public void setObjectAndDescriptor(AssistedObject object, TableRDesc tableRDesc) {
        tableFieldRenderer.setObjectAndDescriptor(object, tableRDesc);
    }

    @Override
    public void setCustomFieldRenderer(
        String key,
        CustomCellContentDisplayer customCellContentDisplayer) {
        tableFieldRenderer.setCustomFieldRenderer(key, customCellContentDisplayer);
    }

    @Override
    public boolean containsCustomizer(String key) {
        return tableFieldRenderer.containsCustomizer(key);
    }

    @Override
    public String getFieldByCustomizer(String key) {
        return alter(tableFieldRenderer.getFieldByCustomizer(key));
    }

    @Override
    public String getField(String key) {
        return alter(tableFieldRenderer.getField(key));
    }

    private String alter(String origValue) {
        if (highlightText == null || "".equals(highlightText))
            return origValue;

        int index = origValue.toLowerCase().indexOf(highlightText.toLowerCase());
        if (index == -1)
            return origValue;
        int matchEndExclusive = index + highlightText.length();

        StringBuffer newValue = new StringBuffer();
        if (index > 0)
            newValue.append(origValue.substring(0, index));
        newValue.append(beginOfHighlight);
        newValue.append(origValue.substring(index, matchEndExclusive));
        newValue.append(endOfHighlight);

        if (matchEndExclusive < origValue.length())
            newValue.append(origValue.substring(matchEndExclusive));

        return newValue.toString();
    }
}
