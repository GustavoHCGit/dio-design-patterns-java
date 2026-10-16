package com.portfolio.dio;

import com.portfolio.dio.facade.CepApi;
import com.portfolio.dio.facade.CrmService;
import com.portfolio.dio.facade.CustomerMigrationFacade;
import com.portfolio.dio.facade.LocalCepApi;
import com.portfolio.dio.singleton.SingletonEager;
import com.portfolio.dio.singleton.SingletonHolder;
import com.portfolio.dio.singleton.SingletonLazy;
import com.portfolio.dio.strategy.AggressiveBehavior;
import com.portfolio.dio.strategy.DefensiveBehavior;
import com.portfolio.dio.strategy.NormalBehavior;
import com.portfolio.dio.strategy.Robot;

public class CoursePatternsDemo {
    public static void main(String[] args) {
        System.out.println("=== Singleton ===");
        System.out.println("Eager devolve a mesma instância: " + (SingletonEager.getInstance() == SingletonEager.getInstance()));
        System.out.println("Lazy devolve a mesma instância: " + (SingletonLazy.getInstance() == SingletonLazy.getInstance()));
        System.out.println("Holder devolve a mesma instância: " + (SingletonHolder.getInstance() == SingletonHolder.getInstance()));

        System.out.println("\n=== Strategy ===");
        Robot robot = new Robot(new NormalBehavior());
        System.out.println(robot.move());
        robot.setBehavior(new DefensiveBehavior());
        System.out.println(robot.move());
        robot.setBehavior(new AggressiveBehavior());
        System.out.println(robot.move());

        System.out.println("\n=== Facade ===");
        CrmService crm = new CrmService();
        CepApi cepApi = new LocalCepApi();
        CustomerMigrationFacade facade = new CustomerMigrationFacade(crm, cepApi);
        System.out.println(facade.migrate("Ana Silva", "01001-000"));
    }
}
