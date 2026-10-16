package com.portfolio.dio;

import com.portfolio.dio.singleton.SingletonEager;
import com.portfolio.dio.singleton.SingletonHolder;
import com.portfolio.dio.singleton.SingletonLazy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class SingletonTest {
    @Test
    void eagerReturnsOneInstance() {
        assertSame(SingletonEager.getInstance(), SingletonEager.getInstance());
    }

    @Test
    void lazyReturnsOneInstance() {
        assertSame(SingletonLazy.getInstance(), SingletonLazy.getInstance());
    }

    @Test
    void holderReturnsOneInstance() {
        assertSame(SingletonHolder.getInstance(), SingletonHolder.getInstance());
    }
}
