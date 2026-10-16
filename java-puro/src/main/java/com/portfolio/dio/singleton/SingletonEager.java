package com.portfolio.dio.singleton;

/** Exemplo didático: instância criada quando a classe é carregada. */
public final class SingletonEager {
    private static final SingletonEager INSTANCE = new SingletonEager();

    private SingletonEager() {
    }

    public static SingletonEager getInstance() {
        return INSTANCE;
    }
}
