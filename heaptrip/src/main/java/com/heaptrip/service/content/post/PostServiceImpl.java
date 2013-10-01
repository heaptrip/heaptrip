package com.heaptrip.service.content.post;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.category.Category;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.Views;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.repository.category.CategoryRepository;
import com.heaptrip.domain.repository.content.post.PostRepository;
import com.heaptrip.domain.repository.region.RegionRepository;
import com.heaptrip.domain.service.category.CategoryService;
import com.heaptrip.domain.service.content.ContentSearchService;
import com.heaptrip.domain.service.content.post.PostService;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.service.content.ContentServiceImpl;

@Service
public class PostServiceImpl extends ContentServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ContentSearchService contentSearchService;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private RegionService regionService;

	@Override
	public Post save(Post content) {
		Assert.notNull(content, "content must not be null");
		Assert.notNull(content.getId(), "contentId must not be null");
		Assert.notNull(content.getOwner(), "owner must not be null");
		Assert.notNull(content.getOwner().getId(), "owner.id must not be null");
		Assert.notEmpty(content.getName(), "name must not be empty");
		Assert.notEmpty(content.getSummary(), "summary must not be empty");
		Assert.notEmpty(content.getDescription(), "description must not be empty");

		// TODO read and set owner name, account type and rating
		// content.getOwner().setName("Ivan Petrov");
		// content.getOwner().setRating(0D);
		// trip.getOwner().setType(AccountEnum.USER);

		// TODO if owner account type == (CLUB or COMPANY) then set owners
		content.setOwners(new String[] { content.getOwner().getId() });

		Set<String> categoryIds = new HashSet<>();
		if (content.getCategories() != null) {
			for (SimpleCategory simpleCategory : content.getCategories()) {
				// set content categories
				Assert.notNull(simpleCategory.getId(), "category.id must not be null");
				Category category = categoryRepository.findOne(simpleCategory.getId());
				Assert.notNull(category, String.format("error category.id: %s", simpleCategory.getId()));
				simpleCategory.setName(category.getName());
				// set all categories
				categoryIds.add(simpleCategory.getId());
				categoryIds.addAll(categoryService.getParentsByCategoryId(simpleCategory.getId()));
			}
		}
		content.setCategoryIds(categoryIds.toArray(new String[0]));

		Set<String> regionIds = new HashSet<>();
		if (content.getRegions() != null) {
			for (SimpleRegion simpleRegion : content.getRegions()) {
				// set content regions
				Assert.notNull(simpleRegion.getId(), "region.id must not be null");
				Region region = regionRepository.findOne(simpleRegion.getId());
				Assert.notNull(region, String.format("error region.id: %s", simpleRegion.getId()));
				simpleRegion.setName(region.getName());
				// set all regions
				regionIds.add(simpleRegion.getId());
				regionIds.addAll(regionService.getParentsByRegionId(simpleRegion.getId()));
			}
		}
		content.setRegionIds(regionIds.toArray(new String[0]));

		content.setStatus(new ContentStatus(ContentStatusEnum.DRAFT));
		content.setCreated(new Date());
		content.setDeleted(null);
		content.setRating(ContentRating.getDefaultValue());
		content.setComments(0L);

		Views views = new Views();
		views.setCount(0);
		content.setViews(views);

		// save to mongodb
		postRepository.save(content);

		// save to solr
		contentSearchService.saveContent(content.getId());

		return content;
	}

	@Override
	public void remove(String contentId) {
		Assert.notNull(contentId, "contentId must not be null");
		super.remove(contentId);
		// remove from solr
		contentSearchService.removeContent(contentId);
	}

	@Override
	public void hardRemove(String contentId) {
		Assert.notNull(contentId, "contentId must not be null");
		super.hardRemove(contentId);
		// remove from solr
		contentSearchService.removeContent(contentId);
	}

	@Override
	public void update(Post post) {
		Assert.notNull(post, "post must not be null");
		Assert.notNull(post.getId(), "post.id must not be null");
		Assert.notEmpty(post.getName(), "name must not be empty");
		Assert.notEmpty(post.getSummary(), "summary must not be empty");
		Assert.notEmpty(post.getDescription(), "description must not be empty");

		Set<String> categoryIds = new HashSet<>();
		if (post.getCategories() != null) {
			for (SimpleCategory simpleCategory : post.getCategories()) {
				// set content categories
				Assert.notNull(simpleCategory.getId(), "category.id must not be null");
				Category category = categoryRepository.findOne(simpleCategory.getId());
				Assert.notNull(category, String.format("error category.id: %s", simpleCategory.getId()));
				simpleCategory.setName(category.getName());
				// set all categories
				categoryIds.add(simpleCategory.getId());
				categoryIds.addAll(categoryService.getParentsByCategoryId(simpleCategory.getId()));
			}
		}
		post.setCategoryIds(categoryIds.toArray(new String[0]));

		Set<String> regionIds = new HashSet<>();
		if (post.getRegions() != null) {
			for (SimpleRegion simpleRegion : post.getRegions()) {
				// set content regions
				Assert.notNull(simpleRegion.getId(), "region.id must not be null");
				Region region = regionRepository.findOne(simpleRegion.getId());
				Assert.notNull(region, String.format("error region.id: %s", simpleRegion.getId()));
				simpleRegion.setName(region.getName());
				// set all regions
				regionIds.add(simpleRegion.getId());
				regionIds.addAll(regionService.getParentsByRegionId(simpleRegion.getId()));
			}
		}
		post.setRegionIds(regionIds.toArray(new String[0]));

		// update to db
		postRepository.update(post);

		// save to solr
		contentSearchService.saveContent(post.getId());
	}

}
