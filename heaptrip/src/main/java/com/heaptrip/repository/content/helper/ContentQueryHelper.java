package com.heaptrip.repository.content.helper;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.criteria.ContentSortCriteria;
import com.heaptrip.repository.helper.AbstractQueryHelper;
import com.heaptrip.util.language.LanguageUtils;
import org.springframework.util.Assert;

public abstract class ContentQueryHelper<T extends ContentSortCriteria, M extends Content>
        extends AbstractQueryHelper<T, M> {

    @Override
    public String getProjection(T criteria) {
        Assert.notNull(criteria.getLocale(), "locale must not be null");
        String lang = LanguageUtils.getLanguageByLocale(criteria.getLocale());
        return String
                .format("{_class: 1, ownerId: 1, 'categories._id': 1, 'categories.name.%s': 1, 'regions._id': 1, 'regions.name.%s': 1, status: 1,"
                        + " 'name.%s': 1, 'name.mainLang': 1, 'name.mainText': 1, 'summary.%s': 1, 'summary.mainLang': 1, 'summary.mainText': 1," +
                        "'table._id': 1, 'table.begin': 1, 'table.end': 1, 'table.price': 1, created: 1, 'views.count': 1, mainLang: 1, rating: 1, comments: 1}",
                        lang, lang, lang, lang);
    }

    @Override
    public String getSort(T criteria) {
        String result = "{created: -1}";
        if (criteria != null && criteria.getSort() != null) {
            switch (criteria.getSort()) {
                case RATING:
                    return "{'rating.value': -1}";
                default:
                    return "{created: -1}";
            }
        }
        return result;
    }

    @Override
    public String getHint(T criteria) {
        return null;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.CONTENTS.getName();
    }
}
