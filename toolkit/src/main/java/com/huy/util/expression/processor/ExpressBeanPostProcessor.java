package com.huy.util.expression.processor;

import com.huy.util.expression.core.ExpressionFactory;
import com.huy.util.expression.core.service.IExpress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * bean的后处理器，对自定义表达式计算对象自动加载
 * <p>
 * Created by huyong on 2017/5/27.
 */
@Slf4j
public class ExpressBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof IExpress) {
            IExpress express = (IExpress) bean;
            ExpressionFactory.registExpress(express);
            log.info("初始化自定义表达式计算对象,表达式前缀-{},实现类-{}", express.fetchExpressPrefix(), bean.getClass().getCanonicalName());
        }

        return bean;

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
