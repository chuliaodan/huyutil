package com.huy.util.alarm;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * @author: huyong
 * @since: 2018/2/8 14:42
 */
public class AssertTest {

    private Random random;

    @Before
    public void setUp() {
        random = new Random();
    }

    @Test
    public void state() throws Exception {
        Assert.state(random.nextBoolean(), new RuntimeException("1...我想抛个异常试试！"));
    }

    @Test
    public void state2() throws Exception {
        Assert.state(random.nextBoolean(), () -> new RuntimeException("2...我想抛个异常试试！"));
    }

    @Test
    public void state3() throws Exception {
        Assert.state(() -> random.nextBoolean(), () -> new RuntimeException("3...我想抛个异常试试！"));
    }

    @Test
    public void state4() throws Exception {
        Assert.state(
                () -> random.nextBoolean(),
                () -> new RuntimeException("4-1...我想抛个异常试试！"),
                () -> new RuntimeException("4-2...我想抛个异常试试！")
        );
    }

}