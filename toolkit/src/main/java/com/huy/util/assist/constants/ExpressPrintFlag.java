package com.huy.util.assist.constants;

import ch.qos.logback.core.util.OptionHelper;

/**
 * 表达式打印标记
 * <p>
 * logback 的配置
 * <property name="_EXPRESS_PRINT" value="true" scope="system" />
 * <property name="_EXPRESS_PARSE" value="true" scope="system" />
 *
 * @author: huyong
 * @since: 2018/2/8 12:07
 */
public class ExpressPrintFlag {

    /**
     * 打印表达式解析过程
     */
    public static boolean expressAllPrint = Boolean.valueOf(OptionHelper.getSystemProperty(Constant.expressParsePrintKey));

    /**
     * 打印表达式计算过程
     */
    public static boolean expressCalcPrint = Boolean.valueOf(OptionHelper.getSystemProperty(Constant.expressPrintKey))
            || Boolean.valueOf(OptionHelper.getSystemProperty(Constant.expressParsePrintKey));

}
