package com.heaptrip.repository.helper;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.service.criteria.Criteria;

@Service
public class QueryHelperFactory implements ApplicationContextAware {

	@SuppressWarnings("rawtypes")
	private Map<String, QueryHelper> queryHelpers = new HashMap<>();

	private ApplicationContext applicationContext;

	@SuppressWarnings("rawtypes")
	@PostConstruct
	public void init() {
		Map<String, QueryHelper> helpers = applicationContext.getBeansOfType(QueryHelper.class);
		for (String beanName : helpers.keySet()) {
			QueryHelper helper = helpers.get(beanName);
			queryHelpers.put(helper.getCriteriaClass().getName(), helper);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Criteria> QueryHelper<T> getHelperByCriteria(Class<T> criteriaClass) {
		return queryHelpers.get(criteriaClass.getName());

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
