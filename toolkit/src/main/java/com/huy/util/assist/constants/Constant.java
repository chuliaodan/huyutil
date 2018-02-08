package com.huy.util.assist.constants;

/**
 * 常量
 *
 * @author huyong
 * @since 2017.03.31
 */
public class Constant {

    /**
     * 表达式解析引擎自定义函数的类名的后缀
     */
    public static final String FUNCTION_SUFFIX = "Function";

    /**
     * 字符串0
     */
    public static final String ZERO = "0";


    /**
     * 使用表达式计算引擎报错时，表达的计算结果
     */
    public static final String CALC_EXPRESS_ERROR_STRING_TYPE = ZERO;

    /**
     * 使用获取属性文件中key的值，获取不到时的默认值
     */
    public static final String CALC_PROP_EXPRESS_ERROR_STRING_TYPE = "-";

    /**
     * 左括号
     */
    public static final char LEFT_BRACKET = '(';

    /**
     * 右括号
     */
    public static final char RIGHT_BRACKET = ')';

    /**
     * logback property标签中 表达式计算 是否打印 的name
     */
    public static final String expressPrintKey = "_EXPRESS_PRINT";

    /**
     * logback property标签中 表达式解析过程 是否打印 的name
     */
    public static final String expressParsePrintKey = "_EXPRESS_PARSE";

}
