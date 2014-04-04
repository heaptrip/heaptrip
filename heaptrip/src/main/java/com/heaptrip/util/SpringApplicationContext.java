package com.heaptrip.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SpringApplicationContext implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public <T> Map<String, T> getBeansOfType(Class<T> tClass) throws org.springframework.beans.BeansException {
        return context.getBeansOfType(tClass);
    }

}