package com.huy.util.expression.core.service;


import com.huy.util.expression.core.parser.Bracket;

import java.util.Map;

/**
 * 表达式计算类型接口，自定义的表达式，需要需要该接口
 * <p>
 * Created by huyong on 2017/5/27.
 */
public interface IExpress {

    /**
     * 计算
     *
     * @param bracket       (表达式中含有前缀的)括号对象
     * @param expression    某个具体的前缀表达式
     * @param data          数据
     * @param originExpress 原始表达式(未进行加工的，可能有多个前缀表达式)
     * @param defaultValue  计算结果默认值
     * @return
     */
    Object calcInternal(Bracket bracket, String expression, Map<String, Object> data, String originExpress, Object defaultValue);

    /**
     * 获取表达式前缀
     *
     * @return
     */
    String fetchExpressPrefix();

    /**
     * 表达式计算的前缀
     */
    enum ExpressPrefixEnum {

        /**
         * Aviator表达式计算前缀
         */
        exp("exp", "Aviator表达式计算前缀"),

        /**
         * springEl表达式计算前缀
         */
        spel("spel", "springEL表达式计算前缀");

        public final String code;
        public final String desc;

        ExpressPrefixEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
