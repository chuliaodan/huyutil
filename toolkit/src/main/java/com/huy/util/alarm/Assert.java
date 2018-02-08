package com.huy.util.alarm;

import com.huy.util.assist.infr.function.BooleanSupply;
import com.huy.util.assist.infr.function.RuntimeExceptionSupply;

/**
 * 告警
 *
 * @author: huyong
 * @since: 2018/1/26 10:11
 */
public class Assert {

    /**
     * 如果flag为true，则抛出异常
     *
     * @param flag
     * @param ex
     */
    public static void state(boolean flag, RuntimeException ex) {
        if (flag == true) {
            throw ex;
        }
    }

    /**
     * 如果flag为true，抛出异常ex1，否者抛出异常ex2
     *
     * @param flag
     * @param ex1
     * @param ex2
     */
    public static void state(boolean flag, RuntimeException ex1, RuntimeException ex2) {
        if (flag == true) {
            throw ex1;
        } else {
            throw ex2;
        }
    }

    /**
     * 如果flag为true，则抛出异常
     *
     * @param flag
     * @param res
     */
    public static void state(boolean flag, RuntimeExceptionSupply res) {
        state(flag, res.get());
    }

    /**
     * 如果flag为true，抛出异常ex1，否者抛出异常ex2
     *
     * @param flag
     * @param res1
     */
    public static void state(boolean flag, RuntimeExceptionSupply res1, RuntimeExceptionSupply res2) {
        state(flag, res1.get(), res2.get());
    }


    /**
     * 如果bs结果true，则返回es返回的异常
     *
     * @param bs
     * @param res
     */
    public static void state(BooleanSupply bs, RuntimeExceptionSupply res) {
        state(bs.get(), res);
    }

    /**
     * 如果bs结果true，则返回es返回的异常
     *
     * @param bs
     * @param res1
     * @param res2
     */
    public static void state(BooleanSupply bs, RuntimeExceptionSupply res1, RuntimeExceptionSupply res2) {
        state(bs.get(), res1, res2);
    }

}
