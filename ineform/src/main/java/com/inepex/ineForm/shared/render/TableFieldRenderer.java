package com.inepex.ineForm.shared.render;

import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface TableFieldRenderer {

    public static interface CustomCellContentDisplayer {
        /**
         * @return the html (or string) value of the pointed column's
         */
        public String getCustomCellContent(
            AssistedObjectHandler rowKvo,
            String fieldId,
            ColRDesc colRDesc);
    }

    void setObjectAndDescriptor(AssistedObject object, TableRDesc tableRDesc);

    void setCustomFieldRenderer(String key, CustomCellContentDisplayer customCellContentDisplayer);

    boolean containsCustomizer(String key);

    String getFieldByCustomizer(String key);

    String getField(String key);

}