package com.inepex.ineForm.shared.dispatch;

import java.util.List;

import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.dispatch.GenericActionResult;
import com.inepex.ineom.shared.dispatch.interfaces.RelationListResult;

/**
 * 
 * 
 * @author István Szoboszlai
 * 
 */
public class RelationListActionResult extends GenericActionResult implements RelationListResult {

    private List<Relation> list;
    private Long allResultCount;

    public RelationListActionResult() {
        super();
    }

    public RelationListActionResult(List<Relation> requestedPage) {
        super();
        this.list = requestedPage;
    }

    public RelationListActionResult(List<Relation> firstPage, Long allResultCount) {
        this(firstPage);
        this.allResultCount = allResultCount;

    }

    @Override
    public List<Relation> getList() {
        return list;
    }

    @Override
    public void setList(List<Relation> list) {
        this.list = list;
    }

    @Override
    public Long getAllResultCount() {
        return allResultCount;
    }

    @Override
    public void setAllResultCount(Long allResultCount) {
        this.allResultCount = allResultCount;
    }

}
