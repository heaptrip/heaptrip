package com.heaptrip.domain.service.region;

import com.heaptrip.domain.entity.region.Region;

import java.util.List;
import java.util.Locale;

/**
 * Regin service
 */
public interface RegionService {

    /**
     * Get region by id
     *
     * @param regionId region ID
     * @param locale   requested locale
     * @return region
     */
    public Region getRegionById(String regionId, Locale locale);

    /**
     * Search regions by name
     *
     * @param name   name or part of the name
     * @param skip   start index
     * @param limit  rows count
     * @param locale users locale
     * @return list of regions
     */
    public List<Region> getRegionsByName(String name, Long skip, Long limit, Locale locale);

    /**
     * Get list of identifiers of parent regions by regionId
     *
     * @param regionId region ID
     * @return list of identifiers
     */
    public List<String> getParentsByRegionId(String regionId);

}
