package com.heaptrip.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.Region;
import com.heaptrip.domain.repository.RegionRepository;
import com.heaptrip.domain.repository.solr.SolrRegion;
import com.heaptrip.domain.repository.solr.SolrRegionRepository;
import com.heaptrip.domain.service.RegionService;

@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	private SolrRegionRepository regionSolrRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Override
	public List<Region> getRegionsByName(String name, Long skip, Long limit, Locale locale) throws SolrServerException {
		Assert.notNull(name, "name");
		Assert.notNull(locale, "locale");
		List<SolrRegion> solrRegions = regionSolrRepository.findByName(name, skip, limit, locale);
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
		Assert.notNull(regionId, "regionId");
		Assert.notNull(locale, "locale");
		return regionRepository.findById(regionId, locale);
	}

}
