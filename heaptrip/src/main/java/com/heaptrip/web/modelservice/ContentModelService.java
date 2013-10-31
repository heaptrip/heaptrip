package com.heaptrip.web.modelservice;

import java.util.Locale;

import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.RegionModel;
import com.heaptrip.web.model.profile.AccountModel;

public interface ContentModelService {

	AccountModel convertContentOwnerToModel(ContentOwner owner);

	ContentOwner getContentOwner();


}
