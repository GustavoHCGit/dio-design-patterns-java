package com.portfolio.dio.singleton;

/** Exemplo didático: inicialização lazy e thread-safe garantida pelo class loader. */
public final class SingletonHolder {
    private SingletonHolder() {
    }

    private static class InstanceHolder {
        private static final SingletonHolder INSTANCE = new SingletonHolder();
    }

    public static SingletonHolder getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
