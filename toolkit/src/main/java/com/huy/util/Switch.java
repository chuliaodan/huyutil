package com.huy.util;

import com.huy.util.assist.infr.function.BooleanSupply;
import com.huy.util.assist.infr.function.NonParamConsumer;

/**
 * 仿三目运算
 *
 * @author: huyong
 * @since: 2018/2/8 10:44
 */
public class Switch {

    /**
     * flag 为true，执行s1，否者执行s2
     *
     * @param flag
     * @param s1
     * @param s2
     */
    public static void run(boolean flag, NonParamConsumer s1, NonParamConsumer s2) {
        if (flag) {
            s1.accept();
        } else {
            s2.accept();
        }
    }

    /**
     * flag 为true，执行s1
     *
     * @param flag
     * @param s1
     */
    public static void run(boolean flag, NonParamConsumer s1) {
        run(flag, s1, () -> {
        });
    }

    /**
     * bs 为true，执行s1
     *
     * @param bs
     * @param s1
     */
    public static void run(BooleanSupply bs, NonParamConsumer s1) {
        run(bs.get(), s1);
    }

    /**
     * bs 为true，执行s1，否者执行s2
     *
     * @param bs
     * @param s1
     * @param s2
     */
    public static void run(BooleanSupply bs, NonParamConsumer s1, NonParamConsumer s2) {
        run(bs.get(), s1, s2);
    }

}
