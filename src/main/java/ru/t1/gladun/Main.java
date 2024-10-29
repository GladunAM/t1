package ru.t1.gladun;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.t1.gladun.service.MainService;

@EnableAspectJAutoProxy
@ComponentScan
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        context.getBean(MainService.class).startWork();
        context.getBean(MainService.class).hardlyWork(0);
        context.getBean(MainService.class).hardlyWork(-5);
        try {
            context.getBean(MainService.class).accidentallyRemoveSomething();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        context.getBean(MainService.class).goHome();
    }

}
