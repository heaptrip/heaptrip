package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.service.content.post.PostService;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.post.PostModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostModelServiceImpl extends ContentModelServiceImpl implements PostModelService {

    @Autowired
    private PostService postService;

    @Override
    public PostModel savePostModel(PostModel postModel) {
        Post post = convertContentModelToPost(postModel);
        post = postService.save(post);
        return (PostModel) convertContentToContentModel(ContentEnum.POST, post, false);
    }

    @Override
    public void updatePostModel(PostModel postModel) {
        Post post = convertContentModelToPost(postModel);
        postService.update(post);
    }

    protected Post convertContentModelToPost(ContentModel contentModel) {
        Post post = new Post();
        post.setId(contentModel.getId());
        post.setOwnerId(getCurrentUser().getId());
        post.setStatus(convertContentStatusModelToContentStatus(contentModel.getStatus()));
        post.setMainLang(contentModel.getLocale());
        post.setName(new MultiLangText(contentModel.getName()));
        post.setDescription(new MultiLangText(contentModel.getDescription()));
        post.setSummary(new MultiLangText(contentModel.getSummary()));
        post.setCategories(convertCategoriesModelsToCategories(contentModel.getCategories(),getCurrentLocale()));
        post.setRegions(convertRegionModelsToRegions(contentModel.getRegions(),getCurrentLocale()));
        return post;
    }





    /*private SimpleCategory[] convertCategoriesModelsToCategories(CategoryModel[] categoryModels) {
        SimpleCategory[] result = null;
        if (categoryModels != null) {
            List<SimpleCategory> categories = new ArrayList<>();
            for (CategoryModel categoryModel : categoryModels) {
                categories.add(new SimpleCategory(categoryModel.getId()));
            }
            result = categories.toArray(new SimpleCategory[categories.size()]);
        }
        return result;
    }*/

    /*private SimpleRegion[] convertRegionModelsToRegions(RegionModel[] regionModels) {
        SimpleRegion[] result = null;
        if (regionModels != null) {
            List<SimpleRegion> regions = new ArrayList<>();
            for (RegionModel regionModel : regionModels) {
                regions.add(new SimpleRegion(regionModel.getId()));
            }
            result = regions.toArray(new SimpleRegion[regions.size()]);
        }
        return result;
    }   */

}
