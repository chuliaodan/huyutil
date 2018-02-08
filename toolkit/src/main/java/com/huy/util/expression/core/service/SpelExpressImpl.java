package com.huy.util.expression.core.service;

import com.huy.util.Switch;
import com.huy.util.assist.constants.ExpressPrintFlag;
import com.huy.util.expression.core.ext.SpringELCustom;
import com.huy.util.expression.core.parser.Bracket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.huy.util.format.FormatJson.toJson;


/**
 * springEL 表达式计算实现
 * <p>
 * Created by huyong on 2017/5/27.
 */
@Data
@AllArgsConstructor
@Slf4j
public class SpelExpressImpl implements IExpress {

    private SpringELCustom spel;

    @Override
    public Object calcInternal(Bracket bracket, String expression, Map<String, Object> data, String originExpress, Object defaultValue) {

        Object value = spel.getValueNoException(expression, defaultValue);

        Switch.run(ExpressPrintFlag.expressCalcPrint, () -> {
            log.info("表达式计算引擎计算：expression-[{}]，originExpression-[{}], value-[{}], env-[{}]", expression, originExpress, value, toJson(data));
        });

        return value;
    }

    @Override
    public String fetchExpressPrefix() {
        return ExpressPrefixEnum.spel.code;
    }

}
