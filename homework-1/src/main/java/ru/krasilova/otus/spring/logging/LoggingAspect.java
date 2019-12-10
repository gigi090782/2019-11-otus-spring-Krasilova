package ru.krasilova.otus.spring.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.w3c.dom.ls.LSOutput;

import java.util.Date;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* ru.krasilova.otus.spring.service.QuizServiceImpl.runQuiz(..))")
    public void logBefore(JoinPoint jointPoint) {
        System.out.println("Вызов метода BEFORE: " + jointPoint.getSignature().getName());

    }

    @Around("execution(* ru.krasilova.otus.spring.service.QuizServiceImpl.runQuiz(..))")
    public Object aroundBefore(ProceedingJoinPoint jointPoint) throws Throwable {
        System.out.println("\nВызов метода AROUND: " + jointPoint.getSignature().getName());
        Date dateNow = new Date();
        System.out.println("\nНачало логгирования " + String.format("Текущая дата и время: %tc", dateNow) + "\n");
        Object obj = jointPoint.proceed();

        dateNow = new Date();
        System.out.println("\nОкончание логгирования " + String.format("Текущая дата и время: %tc", dateNow) + "\n");
        return obj;

    }
}
