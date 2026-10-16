package com.portfolio.dio.strategy;

public class AggressiveBehavior implements MovementBehavior {
    @Override
    public String move() {
        return "Robô avançando para neutralizar a ameaça.";
    }
}
