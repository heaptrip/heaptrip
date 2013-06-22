package com.heaptrip.domain.repository.region;

import java.util.Locale;

import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.repository.CrudRepository;

public interface RegionRepository extends CrudRepository<Region> {

	public Region findById(String Id, Locale locale);

}
