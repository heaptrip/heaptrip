package com.heaptrip.web.converter;

import java.util.List;

import com.heaptrip.web.model.filter.CategoryTreeModel;

public interface FilterModelService {
 
	List<CategoryTreeModel> getCategories();
	
}
