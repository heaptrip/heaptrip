package com.heaptrip.web.converter;

import java.util.Date;

import com.heaptrip.domain.entity.Price;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.ContentOwnerModel;
import com.heaptrip.web.model.content.DateModel;
import com.heaptrip.web.model.content.PriceModel;
import com.heaptrip.web.model.content.RegionModel;

public interface ContentModelService {

	CategoryModel convertCategoryToModel(SimpleCategory category);

	CategoryModel[] convertCategoriesToModel(SimpleCategory[] categories);

	RegionModel convertRegionToModel(SimpleRegion region);

	RegionModel[] convertRegionsToModel(SimpleRegion[] regions);

	ContentOwnerModel convertContentOwnerToModel(ContentOwner owner);

	DateModel convertDate(Date date);
	
	PriceModel convertPrice(Price price);

}
