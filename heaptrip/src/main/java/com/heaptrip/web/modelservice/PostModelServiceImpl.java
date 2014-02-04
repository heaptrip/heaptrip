package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.service.content.post.PostService;
import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.content.RegionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostModelServiceImpl extends ContentModelServiceImpl implements PostModelService {

    @Autowired
    private PostService postService;

    @Override
    public ContentModel savePostModel(ContentModel postModel) {
        Post post = convertContentModelToPost(postModel);
        post = postService.save(post);
        return convertContentToContentModel(ContentEnum.POST, post, false);
    }

    @Override
    public void updatePostModel(ContentModel postModel) {
        Post post = convertContentModelToPost(postModel);
        postService.update(post);
    }

    protected Post convertContentModelToPost(ContentModel contentModel) {
        Post post = new Post();
        post.setId(contentModel.getId());
        post.setOwner(getContentOwner());
        post.setStatus(convertContentStatusModelToContentStatus(contentModel.getStatus()));
        post.setMainLang(contentModel.getLocale());
        post.setName(new MultiLangText(contentModel.getName()));
        post.setDescription(new MultiLangText(contentModel.getDescription()));
        post.setSummary(new MultiLangText(contentModel.getSummary()));
        post.setCategories(convertCategoriesModelsToCategories(contentModel.getCategories()));
        post.setRegions(convertRegionModelsToRegions(contentModel.getRegions()));
        return post;
    }


    private SimpleCategory[] convertCategoriesModelsToCategories(CategoryModel[] categoryModels) {
        SimpleCategory[] result = null;
        if (categoryModels != null) {
            List<SimpleCategory> categories = new ArrayList<>();
            for (CategoryModel categoryModel : categoryModels) {
                categories.add(new SimpleCategory(categoryModel.getId()));
            }
            result = categories.toArray(new SimpleCategory[categories.size()]);
        }
        return result;
    }

    private SimpleRegion[] convertRegionModelsToRegions(RegionModel[] regionModels) {
        SimpleRegion[] result = null;
        if (regionModels != null) {
            List<SimpleRegion> regions = new ArrayList<>();
            for (RegionModel regionModel : regionModels) {
                regions.add(new SimpleRegion(regionModel.getId()));
            }
            result = regions.toArray(new SimpleRegion[regions.size()]);
        }
        return result;
    }

}
