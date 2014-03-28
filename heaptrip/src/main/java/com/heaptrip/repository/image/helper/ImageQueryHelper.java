package com.heaptrip.repository.image.helper;

import com.heaptrip.domain.service.image.criteria.ImageCriteria;
import com.heaptrip.repository.helper.QueryHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageQueryHelper implements QueryHelper<ImageCriteria> {

    @Override
    public Class<ImageCriteria> getCriteriaClass() {
        return ImageCriteria.class;
    }

    @Override
    public String getQuery(ImageCriteria criteria) {
        String query = "{";
        if (StringUtils.isNotEmpty(criteria.getTargetId())) {
            query += "target: #";
        }
        if (criteria.getImageType() != null) {
            query += ", type: #";
        }
        query += "}";
        return query;
    }

    @Override
    public Object[] getParameters(ImageCriteria criteria, Object... arguments) {
        List<Object> parameters = new ArrayList<>();
        // targetId
        if (criteria.getTargetId() != null) {
            parameters.add(criteria.getTargetId());
        }
        // imageType
        if (criteria.getImageType() != null) {
            parameters.add(criteria.getImageType());
        }
        return parameters.toArray();
    }

    @Override
    public String getProjection(ImageCriteria criteria) {
        return "{name:1, text:1, type: 1, target: 1, refs: 1, ownerId: 1, uploaded: 1, likes: 1}";
    }

    @Override
    public String getHint(ImageCriteria criteria) {
        return "{target: 1, uploaded: 1}";
    }

    @Override
    public String getSort(ImageCriteria criteria) {
        return "{uploaded: 1}";
    }
}
