package com.anshishagua.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * User: lixiao
 * Date: 2018/5/3
 * Time: 上午9:58
 */

@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public  void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<?> clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    public static Object getBean(String BeanName){
        return applicationContext.getBean(BeanName);
    }
}