package com.heaptrip.repository.helper;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.service.criteria.Criteria;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class QueryHelperFactory implements ApplicationContextAware {

    private Map<String, QueryHelper> queryHelpers = new HashMap<>();

    private ApplicationContext applicationContext;

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
