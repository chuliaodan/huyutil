package com.huy.util.expression.core.service;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.huy.util.Switch;
import com.huy.util.assist.constants.ExpressPrintFlag;
import com.huy.util.expression.core.parser.Bracket;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

import static com.huy.util.format.FormatJson.toJson;

/**
 * 使用aviator表达式解析引擎计算
 * <p/>
 * Created by huyong on 2017/5/27.
 */
@Slf4j
public class AviatorExpressImpl implements IExpress {

    @Override
    public Object calcInternal(Bracket bracket, String expression, Map<String, Object> data, String originExpress, Object defaultValue) {

        try {

            Expression express = AviatorEvaluator.compile(expression, true);

            Object value = express.execute(data);

            Switch.run(ExpressPrintFlag.expressCalcPrint, () -> {
                log.debug("表达式计算引擎计算：expression-[{}]，originExpression-[{}], value-[{}], env-[{}]", expression, originExpress, value, toJson(data));
            });

            return Optional.ofNullable(value).orElse(defaultValue);

        } catch (Exception e) {
            Switch.run(ExpressPrintFlag.expressCalcPrint, () -> {
                log.error("表达式计算引擎计算异常！expression-[{}]，originExpression-[{}], defaultValue-[{}], env-[{}], error-[{}]", expression, originExpress, defaultValue, data, e);
            });
        }

        return defaultValue;

    }

    @Override
    public String fetchExpressPrefix() {
        return ExpressPrefixEnum.exp.code;
    }

}
