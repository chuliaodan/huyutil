package com.huy.util.assist.infr.function;

import java.util.function.Supplier;

/**
 * 异常生成器
 *
 * @author: huyong
 * @since: 2018/1/26 10:16
 */
@FunctionalInterface
public interface RuntimeExceptionSupply extends Supplier<RuntimeException> {

    @Override
    RuntimeException get();

}
