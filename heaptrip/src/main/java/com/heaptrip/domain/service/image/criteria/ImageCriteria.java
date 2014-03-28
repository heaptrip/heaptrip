package com.heaptrip.domain.service.image.criteria;

import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.service.criteria.Criteria;

/**
 * Base criteria for search images
 */
public class ImageCriteria extends Criteria {

    //  _id of associated object (account id, trip id, table item id, trip route id, etc.)
    private String targetId;

    // image type
    private ImageEnum imageType;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public ImageEnum getImageType() {
        return imageType;
    }

    public void setImageType(ImageEnum imageType) {
        this.imageType = imageType;
    }
}
