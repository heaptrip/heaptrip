package com.heaptrip.service.region;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.system.SolrException;
import com.heaptrip.domain.repository.region.RegionRepository;
import com.heaptrip.domain.repository.solr.SolrRegionRepository;
import com.heaptrip.domain.repository.solr.entity.SolrRegion;
import com.heaptrip.domain.service.adm.ErrorService;
import com.heaptrip.domain.service.region.RegionService;

@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	private SolrRegionRepository solrRegionRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private ErrorService errorService;

	@Override
	public List<Region> getRegionsByName(String name, Long skip, Long limit, Locale locale) {
		Assert.notNull(name, "name must not be null");
		Assert.notNull(locale, "locale must not be null");
		List<SolrRegion> solrRegions = null;
		try {
			solrRegions = solrRegionRepository.findByName(name, skip, limit, locale);
		} catch (SolrServerException e) {
			throw errorService.createException(SolrException.class, e, ErrorEnum.ERR_SYSTEM_SOLR);
		}
		List<Region> result = new ArrayList<>();
		if (solrRegions != null) {
			for (SolrRegion solrRegion : solrRegions) {
				Region region = solrRegion.toRegion(locale);
				result.add(region);
			}
		}
		return result;
	}

	@Override
	public Region getRegionById(String regionId, Locale locale) {
		Assert.notNull(regionId, "regionId must not be null");
		Assert.notNull(locale, "locale must not be null");
		return regionRepository.findById(regionId, locale);
	}

	@Override
	public List<String> getParentsByRegionId(String regionId) {
		Assert.notNull(regionId, "categoryId must not be null");
		List<String> ids = new ArrayList<>();
		Region region = regionRepository.getParentId(regionId);
		while (region != null && region.getParent() != null) {
			ids.add(region.getParent());
			region = regionRepository.getParentId(region.getParent());
		}
		return ids;
	}

}
