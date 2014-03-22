package com.heaptrip.domain.service.criteria;

/**
 * Base class for all criterias
 */
public abstract class Criteria {

    // the number of records to skip
    private Long skip;

    // the maximum number of records
    private Long limit;

    public Long getSkip() {
        return skip;
    }

    public void setSkip(Long skip) {
        this.skip = skip;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}
