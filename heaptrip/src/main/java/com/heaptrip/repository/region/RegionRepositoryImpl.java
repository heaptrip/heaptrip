package com.heaptrip.repository.region;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.repository.region.RegionRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.util.language.LanguageUtils;
import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class RegionRepositoryImpl extends CrudRepositoryImpl<Region> implements RegionRepository {

    @Override
    public Region findById(String Id, Locale locale) {
        MongoCollection coll = getCollection();
        String lang = LanguageUtils.getLanguageByLocale(locale);
        String fields = String.format("{'name.%s': 1, 'path.%s': 1, parent: 1, ancestors: 1, type: 1}", lang, lang);
        return coll.findOne("{ _id: #}", Id).projection(fields).as(Region.class);
    }

    @Override
    public Region getParentId(String regionId) {
        MongoCollection coll = getCollection();
        return coll.findOne("{_id: #}", regionId).projection("{parent: 1}").as(getCollectionClass());
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.REGIONS.getName();
    }

    @Override
    protected Class<Region> getCollectionClass() {
        return Region.class;
    }

}
