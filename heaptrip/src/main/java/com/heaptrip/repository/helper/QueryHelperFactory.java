package com.heaptrip.repository.helper;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.service.criteria.Criteria;
import com.heaptrip.util.SpringApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class QueryHelperFactory {

    private Map<String, QueryHelper> queryHelpers = new HashMap<>();

    @Autowired
    private SpringApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        Map<String, QueryHelper> helpers = applicationContext.getBeansOfType(QueryHelper.class);
        for (String beanName : helpers.keySet()) {
            QueryHelper helper = helpers.get(beanName);
            queryHelpers.put(helper.getCriteriaClass().getName(), helper);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Criteria, M extends BaseObject> QueryHelper<T, M> getHelperByCriteria(T criteria) {
        return queryHelpers.get(criteria.getClass().getName());
    }

    @SuppressWarnings("unchecked")
    public <T extends Criteria, M extends BaseObject> QueryHelper<T, M> getHelperByCriteriaClass(Class<T> criteriaClass) {
        return queryHelpers.get(criteriaClass.getName());
    }

}
