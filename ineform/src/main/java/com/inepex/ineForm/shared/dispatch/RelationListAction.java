package com.inepex.ineForm.shared.dispatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.customware.gwt.dispatch.shared.Action;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.dispatch.interfaces.RelationList;

/**
 * General action for searching when a {@link List} of {@link KeyValueObject} is
 * returned. Especially useful in {@link ServerSideTableModel}
 *
 * @author István Szoboszlai
 *
 */
public class RelationListAction extends AbstractSearchAction
    implements
    Action<RelationListActionResult>,
    RelationList {
    /**
	 * 
	 */
    private static final long serialVersionUID = -8669603502664601792L;
    private List<String> propGroups = new ArrayList<String>();

    public RelationListAction() {}

    public RelationListAction(
        String descriptorName,
        AssistedObject searchParameters,
        int firstResult,
        int numMaxResult,
        boolean queryResultCount) {
        super(descriptorName, searchParameters, firstResult, numMaxResult, queryResultCount);
    }

    @Override
    public void setPropGroups(String... propGroups) {
        this.propGroups = Arrays.asList(propGroups);
    }

    @Override
    public List<String> getPropGroups() {
        return propGroups;
    }

}
