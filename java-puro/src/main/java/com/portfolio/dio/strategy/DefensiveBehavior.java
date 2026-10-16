package com.portfolio.dio.strategy;

public class DefensiveBehavior implements MovementBehavior {
    @Override
    public String move() {
        return "Robô recuando para uma posição segura.";
    }
}
