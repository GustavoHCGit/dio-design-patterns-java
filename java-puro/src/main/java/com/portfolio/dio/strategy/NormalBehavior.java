package com.portfolio.dio.strategy;

public class NormalBehavior implements MovementBehavior {
    @Override
    public String move() {
        return "Robô avançando em ritmo normal.";
    }
}
