package ru.job4j.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.job4j.repository.accident.AccidentMem;
import ru.job4j.repository.accident.AccidentStore;
import ru.job4j.repository.accidenttype.AccidentTypeStore;
import ru.job4j.repository.rule.RuleStore;
import ru.job4j.service.*;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:car_accident_app.properties")
@Import({HbmConfig.class, AopConfig.class})
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public SpringLiquibase liquibase(DataSource ds) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/master.xml");
        liquibase.setDataSource(ds);
        liquibase.setClearCheckSums(true);
        liquibase.setDropFirst(true);
        return liquibase;
    }

    @Bean
    @Lazy
    public AccidentMem accidentMem() {
        return new AccidentMem();
    }

    @Bean
    public RuleService ruleService(RuleStore ruleHibernate) {
        return new RuleServiceImpl(ruleHibernate);
    }

    @Bean
    public AccidentTypeService accidentTypeService(AccidentTypeStore accidentTypeHibernate) {
        return new AccidentTypeServiceImpl(accidentTypeHibernate);
    }

    @Bean
    public AccidentService accidentService(AccidentStore accidentHibernate) {
        return new AccidentServiceImpl(accidentHibernate);
    }

}
