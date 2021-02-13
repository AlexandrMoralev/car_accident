package ru.job4j.aop.aspect;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Logger {

    private final static org.apache.logging.log4j.Logger LOG = LogManager.getLogger(Logger.class);

    @Pointcut("execution(* ru.job4j.controller.*.*(..)))")
    private void inWebLayer() {
    }

    @Pointcut("execution(* ru.job4j.service.*.*(..))")
    private void inServiceLayer() {
    }

    @Pointcut("within(ru.job4j.repository.*.*)")
    private void inDataAccessLayer() {
    }

    @Before("inServiceLayer())")
    public void inServiceLayer(JoinPoint joinPoint) {
        LOG.debug(">> Executed service method: {}", joinPoint.getSignature().toLongString().toString());
    }

    @Around("inWebLayer() && @annotation(ru.job4j.aop.annotation.ShowExecutionTime)")
    public Object executionTime(ProceedingJoinPoint joinPoint) {
        long start = System.nanoTime();
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            LOG.error(throwable);
        }
        long executionTime = System.nanoTime() - start;
        LOG.debug(">> Called {}, executionTime: {} ns", joinPoint.getSignature().toShortString(), executionTime);
        return proceed;
    }

}
