package com.heaptrip.web.converter;

import com.heaptrip.domain.entity.ContentCategory;
import com.heaptrip.domain.entity.ContentOwner;
import com.heaptrip.domain.entity.ContentRegion;
import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.ContentOwnerModel;
import com.heaptrip.web.model.content.RegionModel;

public interface ContentModelService {

	CategoryModel convertCategoryToModel(ContentCategory category);

	CategoryModel[] convertCategoriesToModel(ContentCategory[] categories);

	RegionModel convertRegionToModel(ContentRegion region);

	RegionModel[] convertRegionsToModel(ContentRegion[] regions);

	ContentOwnerModel convertContentOwnerToModel(ContentOwner owner);

}
