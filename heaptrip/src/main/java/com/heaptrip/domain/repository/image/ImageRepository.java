package com.heaptrip.domain.repository.image;

import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.repository.CrudRepository;

import java.util.List;


public interface ImageRepository extends CrudRepository<Image> {

    public List<Image> findByTargetId(String targetId);

    public long getCountByTargetId(String targetId);

    public List<Image> findByTargetId(String targetId, int skip, int limit);

    public void updateNameAndText(String imageId, String name, String text);

    public void incLike(String imageId);

    public void removeByTargetId(String targetId);
}
