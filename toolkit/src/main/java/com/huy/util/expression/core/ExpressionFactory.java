package com.huy.util.expression.core;

import com.huy.util.expression.core.service.IExpress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 各种表达式计算对象创建工厂
 * <p>
 * Created by huyong on 2017/5/27.
 */
public class ExpressionFactory {

    private static Logger logger = LoggerFactory.getLogger(ExpressionFactory.class);

    /**
     * 缓存，beanPostProcessor时，把IExpress接口的实现类进行存储
     */
    private static Map<String, IExpress> expressMap = new HashMap<>();

    /**
     * 注册表达式计算处理类
     *
     * @param express 表达式计算处理对象
     * @return 注册的表达式计算处理
     */
    public static IExpress registExpress(IExpress express) {

        String prefix = express.fetchExpressPrefix();

        if (expressMap.containsKey(prefix)) {
            logger.warn("注意，你正在覆盖存在的表达式！表达式前缀为：{},对应的表达式处理类为：{}", prefix, express.getClass().getCanonicalName());
        }

        expressMap.put(prefix, express);

        return express;
    }

    /**
     * 根据表达式前缀获取表达式计算对象
     *
     * @param prefixEnum 前缀
     * @return 表达式计算对象
     */
    public static IExpress fetchExpress(String prefixEnum) {
        return expressMap.get(prefixEnum);
    }

    /**
     * 获取支持的所有表达式的前缀
     *
     * @return
     */
    public static Set<String> getPrefixAll() {
        return expressMap.keySet();
    }

    public static Map<String, IExpress> getExpressMap() {
        return expressMap;
    }
}
