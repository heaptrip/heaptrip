package com.heaptrip.domain.service.region;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.region.Region;

/**
 * 
 * Regin service
 * 
 */
public interface RegionService {

	/**
	 * Get region by id
	 * 
	 * @param regionId
	 * @param locale
	 * @return region
	 */
	public Region getRegionById(String regionId, Locale locale);

	/**
	 * Search regions by name
	 * 
	 * @param name
	 *            name or part of the name
	 * @param skip
	 *            start index
	 * @param limit
	 *            rows count
	 * @param locale
	 *            users locale
	 * @return list of regions
	 */
	public List<Region> getRegionsByName(String name, Long skip, Long limit, Locale locale);

	/**
	 * Get list of identifiers of parent regions by regionId
	 * 
	 * @param regionId
	 * @return list of identifiers
	 */
	// TODO konovalov add test
	public List<String> getParentsByRegionId(String regionId);

}
