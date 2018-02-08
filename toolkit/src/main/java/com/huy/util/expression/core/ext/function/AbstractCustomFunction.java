package com.huy.util.expression.core.ext.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.huy.util.assist.constants.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * (Aviator)表达式解析引擎自定义函数抽象类，目的：对函数名字进行规范化管理.
 * <p>
 * 规则为：函数类名，截取末尾的Function，然后首字母小写得到
 * <p>
 * Created by huyong on 2017/5/22.
 */
public abstract class AbstractCustomFunction extends AbstractFunction {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getName() {

        //函数类名
        String className = this.getClass().getSimpleName();

        //Function关键字所在的位置
        int functionIndex = className.lastIndexOf(Constant.FUNCTION_SUFFIX);

        if (functionIndex == -1) {
            return firstCharLower(className);
        }

        return firstCharLower(className.substring(0, functionIndex));

    }

    /**
     * 使首字母小写
     *
     * @param data 首字母待转换的数据
     * @return
     */
    private String firstCharLower(String data) {
        if (StringUtils.isEmpty(data)) {
            return data;
        }
        return new StringBuilder().append(data.substring(0, 1).toLowerCase()).append(data.substring(1)).toString();
    }

}
