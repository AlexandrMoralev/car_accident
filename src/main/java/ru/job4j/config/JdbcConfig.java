package ru.job4j.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.job4j.repository.accident.AccidentJdbcTemplate;

import javax.sql.DataSource;

//@Configuration // disabled to switch on hibernate store
//@PropertySource("classpath:car_accident_app.properties")
//@EnableTransactionManagement
public class JdbcConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource(@Value("${jdbc.driver}") String driver,
                                 @Value("${jdbc.url}") String url,
                                 @Value("${jdbc.username}") String username,
                                 @Value("${jdbc.password}") String password
    ) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setValidationQuery("SELECT 1");
        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

//    @Bean("accidentJdbcTemplate")
//    public AccidentJdbcTemplate accidentJdbcTemplate(JdbcTemplate jdbcTemplate,
//                                                     DataSource dataSource
//    ) {
//        return new AccidentJdbcTemplate(jdbcTemplate, dataSource);
//    }

    @Bean("accidentJdbcTemplate")
    public AccidentJdbcTemplate accidentJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate,
                                                     DataSource dataSource
    ) {
        return new AccidentJdbcTemplate(jdbcTemplate, dataSource);
    }

}
