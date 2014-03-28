package com.heaptrip.domain.repository.image;

import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.image.criteria.ImageCriteria;

import java.util.List;


public interface ImageRepository extends CrudRepository<Image> {

    public List<Image> findByCriteria(ImageCriteria imageCriteria);

    public long countByCriteria(ImageCriteria imageCriteria);

    public void updateNameAndText(String imageId, String name, String text);

    public void incLike(String imageId);

    public void removeByTargetId(String targetId);
}
