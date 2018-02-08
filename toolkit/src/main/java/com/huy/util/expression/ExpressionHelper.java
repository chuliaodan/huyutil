package com.huy.util.expression;


import com.huy.util.Switch;
import com.huy.util.assist.constants.Constant;
import com.huy.util.assist.constants.ExpressPrintFlag;
import com.huy.util.expression.core.ExpressionFactory;
import com.huy.util.expression.core.parser.Bracket;
import com.huy.util.expression.core.parser.ExpressParserCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 表达式计算的服务
 * <p>
 * 注意：此类，日志打印不要轻易开启，只能在测试环境调试使用。打印日志很消耗性能。
 * <p>
 * <p>
 * Created by huyong on 2017/5/27.
 */
public class ExpressionHelper {

    private static Logger logger = LoggerFactory.getLogger(ExpressionHelper.class);

    /**
     * 递归调用前 括号对象Bracket 缓存
     */
    private static Map<String, Bracket> cache = new HashMap<>();

    /**
     * 表达式计算
     *
     * @param express 表达式
     * @param env     数据
     * @return
     */
    public final static String calc(String express, Map<String, Object> env) {
        return calc(express, env, express, Constant.CALC_EXPRESS_ERROR_STRING_TYPE, 0);
    }

    /**
     * 表达式计算
     *
     * @param express      表达式
     * @param env          数据
     * @param defaultValue 表达式计算的默认值
     * @return
     */
    public final static String calc(String express, Map<String, Object> env, Object defaultValue) {
        return calc(express, env, express, defaultValue, 0);
    }

    /**
     * 表达式计算
     *
     * @param express        表达式(表达式嵌套，可能会发生变化)
     * @param env            数据
     * @param originExpress  最原始的表达式
     * @param defaultValue   表达式计算的默认值
     * @param recursiveCount 递归调用次数
     * @return
     */
    private static String calc(String express, Map<String, Object> env, String originExpress, Object defaultValue, int recursiveCount) {

        Bracket bracket = getBracket(express, recursiveCount);

        if (bracket != null) {

            String prefix = bracket.getLeftBracketPrefix();

            String tmpExpress = express.substring(bracket.getLeftBracketPosition() + 1, bracket.getRightBracketPosition());
            Object value = ExpressionFactory.fetchExpress(prefix).calcInternal(bracket, tmpExpress, env, originExpress, defaultValue);

            Switch.run(ExpressPrintFlag.expressCalcPrint, () -> {
                logger.debug("原始表达式-{},表达式-{},计算结果-{},数据:{}", originExpress, tmpExpress, value, env);
            });

            int prefixIndex = bracket.getLeftBracketPosition() - prefix.length();//前缀所在的位置
            int rightBracketIndex = bracket.getRightBracketPosition() + 1;//右括号所在的位置

            if (prefixIndex > 0 && rightBracketIndex <= express.length()) {
                express = new StringBuilder(express.substring(0, prefixIndex)).append(value).append(express.substring(rightBracketIndex)).toString();
                return calc(express, env, originExpress, defaultValue, ++recursiveCount);//递归
            }

            return value != null ? value.toString() : null;

        }

        return express;

    }


    /**
     * 获取括号对象
     *
     * @param express        表达式
     * @param recursiveCount 递归调用次数
     * @return
     */
    private static Bracket getBracket(String express, int recursiveCount) {

        Bracket bracket = cache.get(express);

        if (bracket == null) {
            bracket = ExpressParserCustom.getBracketDeepest(express);
            if (recursiveCount == 0
                    && bracket != null) {//首次调用，则进行缓存
                cache.put(express, bracket);
            }
        }

        return bracket;

    }

}
