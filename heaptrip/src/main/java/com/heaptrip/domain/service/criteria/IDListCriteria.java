package com.heaptrip.domain.service.criteria;


/**
 * Criteria for finding a field from the list of identifiers
 */
public class IDListCriteria {

    // check mode
    private CheckModeEnum checkMode;

    // id list
    private String[] ids;

    public IDListCriteria() {
        super();
    }

    public IDListCriteria(CheckModeEnum checkMode, String[] ids) {
        super();
        this.checkMode = checkMode;
        this.ids = ids;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public CheckModeEnum getCheckMode() {
        return checkMode;
    }

    public void setCheckMode(CheckModeEnum checkMode) {
        this.checkMode = checkMode;
    }

}
