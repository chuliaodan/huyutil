package com.huy.util.expression.core.ext;

import com.huy.util.Switch;
import com.huy.util.assist.constants.ExpressPrintFlag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import static com.huy.util.format.FormatJson.toJson;

/**
 * spring el表达式支持
 * Created by huyong on 2017/5/14.
 */
@Slf4j
public class SpringELCustom {

    /**
     * spring el解析器
     */
    private ExpressionParser parser;

    /**
     * 保证一个线程一个springEL的上下文，防止数据蹿
     */
    ThreadLocal<EvaluationContext> threadLocal;

    public SpringELCustom() {
        parser = new SpelExpressionParser();
        threadLocal = ThreadLocal.withInitial(() -> new StandardEvaluationContext());
    }

    /**
     * 存储数据在springEL的上下文中
     *
     * @param name 存储名字
     * @param data 存储的数据
     */
    public void setData(String name, Object data) {

        Switch.run(ExpressPrintFlag.expressAllPrint, () -> {
            log.debug("线程名-[{}],存储的变量名-[{}],存储对象数据:{}", Thread.currentThread().getName(), name, toJson(data));
        });

        EvaluationContext context = threadLocal.get();

        if (context == null) {
            context = new StandardEvaluationContext();
            threadLocal.set(context);
        }

        context.setVariable(name, data);

    }

    /**
     * 获取存入EvaluationContext中指定key对应的数据
     *
     * @param key 存储在上下文中的key
     * @return
     */
    public Object getData(String key) {

        EvaluationContext context = threadLocal.get();

        if (context != null) {
            return context.lookupVariable(key);
        }

        return null;

    }

    /**
     * 获取springEL的上下文
     *
     * @return
     */
    public EvaluationContext getEvaluationContext() {

        EvaluationContext context = threadLocal.get();

        if (context == null) {
            context = new StandardEvaluationContext();
            threadLocal.set(context);
        }

        return context;

    }

    /**
     * 清除当前线程的springEL上下文中的数据
     */
    public void clear() {
        Switch.run(ExpressPrintFlag.expressAllPrint, () -> {
            log.debug("清除当前线程的springEL上下文中的数据，线程名-[{}]", Thread.currentThread().getName());
        });
        threadLocal.remove();
    }


    /**
     * 根据el表达式获取值
     *
     * @param express springEL表达式
     * @return
     */
    public Object getValue(String express) {

        Object value = parser.parseExpression(express).getValue(getEvaluationContext());

        Switch.run(ExpressPrintFlag.expressAllPrint, () -> {
            log.debug("线程名-[{}],表达式-[{}],获取到值:{}", Thread.currentThread().getName(), express, toJson(value));
        });

        return value;
    }

    /**
     * 根据el表达式获取值，异常时，得到默认值
     *
     * @param express      表达式
     * @param defaultValue 默认值
     * @return
     */
    public Object getValueNoException(String express, Object defaultValue) {

        try {
            return parser.parseExpression(express).getValue(getEvaluationContext());
        } catch (Exception e) {
            Switch.run(ExpressPrintFlag.expressAllPrint, () -> {
                log.error("SpringEL获取表达式的值时发生异常!express-[{}]", express, e);
            });
        }

        return defaultValue;

    }

    /**
     * 根据el表达式获取值
     *
     * @param express    springEL表达式
     * @param returnType 返回值类型
     * @param <T>
     * @return
     */
    public <T> T getValue(String express, Class<T> returnType) {

        T value = parser.parseExpression(express).getValue(getEvaluationContext(), returnType);

        Switch.run(ExpressPrintFlag.expressAllPrint, () -> {
            log.debug("线程名-[{}],表达式-[{}],返回值类型:{},获取到值:{}", Thread.currentThread().getName(), express, returnType.getCanonicalName(), toJson(value));
        });

        return value;
    }

    /**
     * 根据el表达式获取值，支持模板，如：my name is #{#user.name}
     *
     * @param express springEL表达式
     * @return
     */
    public Object getTemplateValue(String express) {

        Object value = parser.parseExpression(express, new TemplateParserContext()).getValue(getEvaluationContext());

        Switch.run(ExpressPrintFlag.expressAllPrint, () -> {
            log.debug("线程名-[{}],表达式-[{}],获取到值:{}", Thread.currentThread().getName(), express, toJson(value));
        });

        return value;
    }


    /**
     * 根据el表达式获取值，支持模板，如：my name is #{#user.name}
     *
     * @param express    springEL表达式
     * @param returnType 返回值类型
     * @param <T>
     * @return
     */
    public <T> T getTemplateValue(String express, Class<T> returnType) {

        T value = parser.parseExpression(express, new TemplateParserContext()).getValue(getEvaluationContext(), returnType);

        Switch.run(ExpressPrintFlag.expressAllPrint, () -> {
            log.debug("线程名-[{}],表达式-[{}],返回值类型:{},获取到值:{}", Thread.currentThread().getName(), express, returnType.getCanonicalName(), toJson(value));
        });

        return value;
    }

}
