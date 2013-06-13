package com.heaptrip.domain.repository;

import java.util.Locale;

import com.heaptrip.domain.entity.Region;

public interface RegionRepository extends CrudRepository<Region> {

	public Region findById(String Id, Locale locale);

}
