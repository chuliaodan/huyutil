package com.huy.util.assist.infr.function;

import java.util.function.Supplier;

/**
 * @author: huyong
 * @since: 2018/1/26 10:17
 */
@FunctionalInterface
public interface BooleanSupply extends Supplier<Boolean> {

    @Override
    Boolean get();
}
