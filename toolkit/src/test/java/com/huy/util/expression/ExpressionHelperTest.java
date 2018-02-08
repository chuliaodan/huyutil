package com.huy.util.expression;

import com.huy.util.assist.constants.Constant;
import com.huy.util.expression.core.ExpressionFactory;
import com.huy.util.expression.core.ext.SpringELCustom;
import com.huy.util.expression.core.service.AviatorExpressImpl;
import com.huy.util.expression.core.service.SpelExpressImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: huyong
 * @since: 2018/2/8 14:12
 */
@Slf4j
public class ExpressionHelperTest {

    @Before
    public void setUp() {
        System.setProperty(Constant.expressPrintKey, "true");
        System.setProperty(Constant.expressParsePrintKey, "true");
    }

    /**
     * aviator表达式测试
     *
     * @throws Exception
     */
    @Test
    public void calc() throws Exception {

        ExpressionFactory.registExpress(new AviatorExpressImpl());

        String express = "exp(a+b/5)";

        Map<String, Object> env = new HashMap<>();
        env.put("a", 10);
        env.put("b", 20);

        log.debug(ExpressionHelper.calc(express, env));
    }

    /**
     * spring el 表达式测试
     */
    @Test
    public void calc2() {
        SpringELCustom spel = new SpringELCustom();
        ExpressionFactory.registExpress(new SpelExpressImpl(spel));
        spel.setData("request", new Request("huyong55", 100));
        System.out.println(ExpressionHelper.calc("spel(#request.erp)", null));
    }

    /**
     * aviator + SpringEL 混合表达式测试
     *
     * @throws Exception
     */
    @Test
    public void calc3() throws Exception {

        SpringELCustom spel = new SpringELCustom();

        ExpressionFactory.registExpress(new AviatorExpressImpl());
        ExpressionFactory.registExpress(new SpelExpressImpl(spel));

        Map<String, Object> env = new HashMap<>();
        env.put("a", 10);
        env.put("b", 2);

        spel.setData("request", new Request("huyong55", 100));

        System.out.println(ExpressionHelper.calc("exp( spel(#request.erp)=='huyong55' ? a*b*a : a+b )", env));
        System.out.println(ExpressionHelper.calc("exp( 'spel(#request.erp)'=='huyong55' ? a*b*a : a+b )", env));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Request {
        String erp;
        int level;
    }

}
