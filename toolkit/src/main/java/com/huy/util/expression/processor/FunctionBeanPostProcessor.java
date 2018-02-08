package com.huy.util.expression.processor;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * bean的后处理器，对表达式解析引擎(Aviator)自定义函数bean进行自动装载
 * Created by huyong on 2017/5/17.
 */
@Slf4j
public class FunctionBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AbstractFunction) {
            AbstractFunction function = (AbstractFunction) bean;
            AviatorEvaluator.addFunction(function);
            log.info("初始化 表达式解析引擎的 自定义函数,函数名-{},函数处理类-{}", function.getName(), bean.getClass().getCanonicalName());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
