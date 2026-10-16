package com.portfolio.dio.strategy;

import java.util.Objects;

public class Robot {
    private MovementBehavior behavior;

    public Robot(MovementBehavior behavior) {
        setBehavior(behavior);
    }

    public void setBehavior(MovementBehavior behavior) {
        this.behavior = Objects.requireNonNull(behavior, "A estratégia de movimento é obrigatória.");
    }

    public String move() {
        return behavior.move();
    }
}
