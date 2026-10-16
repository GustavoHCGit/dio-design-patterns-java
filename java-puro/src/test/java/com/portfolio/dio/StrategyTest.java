package com.portfolio.dio;

import com.portfolio.dio.strategy.DefensiveBehavior;
import com.portfolio.dio.strategy.NormalBehavior;
import com.portfolio.dio.strategy.Robot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StrategyTest {
    @Test
    void robotCanChangeMovementBehaviorAtRuntime() {
        Robot robot = new Robot(new NormalBehavior());
        assertTrue(robot.move().contains("ritmo normal"));
        robot.setBehavior(new DefensiveBehavior());
        assertTrue(robot.move().contains("posição segura"));
    }
}
