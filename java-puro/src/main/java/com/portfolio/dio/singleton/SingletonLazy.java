package com.portfolio.dio.singleton;

/** Exemplo didático: instância criada na primeira chamada, com sincronização segura. */
public final class SingletonLazy {
    private static volatile SingletonLazy instance;

    private SingletonLazy() {
    }

    public static SingletonLazy getInstance() {
        SingletonLazy local = instance;
        if (local == null) {
            synchronized (SingletonLazy.class) {
                local = instance;
                if (local == null) {
                    local = new SingletonLazy();
                    instance = local;
                }
            }
        }
        return local;
    }
}
