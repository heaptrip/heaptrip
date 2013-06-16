package com.heaptrip.web.converter;

import java.util.List;

import com.heaptrip.web.model.content.RegionModel;
import com.heaptrip.web.model.filter.CategoryTreeModel;

public interface FilterModelService {

	List<CategoryTreeModel> getCategories();

	List<RegionModel> searchRegionsByText(String text);
	
	String[] getUserCategories();

}
