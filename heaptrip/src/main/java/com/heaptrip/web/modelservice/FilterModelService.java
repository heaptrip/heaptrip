package com.heaptrip.web.modelservice;

import java.util.List;

import com.heaptrip.util.tuple.TreObject;
import com.heaptrip.web.model.content.RegionModel;
import com.heaptrip.web.model.filter.CategoryTreeModel;

public interface FilterModelService {

	List<CategoryTreeModel> getCategories();

	List<RegionModel> searchRegionsByText(String text);

	/**
	 * 
	 * @param regionId
	 * @return 
	 * 		regions.getUno() - COUNTRY 
	 * 		regions.getDue() - AREA
	 *      regions.getTre() - CITY
	 */
	TreObject<RegionModel, RegionModel, RegionModel> getRegionHierarchy(String regionId);

	String[] getUserCategories(String uid);

    String[] getUserRegions(String uid);

}
