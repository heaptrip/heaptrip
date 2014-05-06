package com.heaptrip.domain.service.criteria;


/**
 * Criteria for finding a field from the list of identifiers
 */
public class IDCriteria extends Criteria {

    // check mode
    private CheckModeEnum checkMode;

    // id
    private String id;

    public IDCriteria() {
        super();
    }

    public IDCriteria(CheckModeEnum checkMode, String id) {
        super();
        this.checkMode = checkMode;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CheckModeEnum getCheckMode() {
        return checkMode;
    }

    public void setCheckMode(CheckModeEnum checkMode) {
        this.checkMode = checkMode;
    }

}
