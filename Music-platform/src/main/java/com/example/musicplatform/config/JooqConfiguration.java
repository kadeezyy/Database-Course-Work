package com.example.musicplatform.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"com.example.musicplatform.entity.tables"})
@EnableTransactionManagement
@PropertySource("classpath:application.yml")
public class JooqConfiguration {
    private final Environment environment;

    public JooqConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig dataSource = new HikariConfig();
        dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driverClassName"));
        dataSource.setMaximumPoolSize(Integer.parseInt(environment.getRequiredProperty("spring.datasource.hikari.maximum-pool-size")));
        dataSource.setMinimumIdle(Integer.parseInt(environment.getRequiredProperty("spring.datasource.hikari.minimum-idle")));
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));
        return new HikariDataSource(dataSource);
    }

    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(dataSource());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDataSource());
    }

    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    @Bean
    public DefaultConfiguration configuration() {
        DefaultConfiguration JooqConfiguration = new DefaultConfiguration();
        JooqConfiguration.set(connectionProvider());
//        JooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformer()));

        String sqlDialectName = "POSTGRES";
        SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        JooqConfiguration.set(dialect);

        return JooqConfiguration;
    }

}
