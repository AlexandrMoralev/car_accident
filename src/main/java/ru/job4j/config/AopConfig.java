package ru.job4j.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.job4j.aop.aspect.Logger;

@Configuration
@PropertySource("classpath:car_accident_app.properties")
@ComponentScan("ru.job4j.aop")
@EnableAspectJAutoProxy
public class AopConfig {
}
