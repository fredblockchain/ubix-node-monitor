package com.fred.node.app;

import com.fred.node.chain.StatusService;
import com.fred.node.chain.data.TransactionService;
import com.fred.node.monitor.NotificationService;
import com.fred.node.ubix.RewardsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
public class Appconfig {

    @Bean
    @Qualifier("statusService")
    public StatusService syncStatus() {
        return new StatusService();
    }

    @Bean
    @Qualifier("transactionService")
    public TransactionService UBIXInMemoryDB() {
        return new TransactionService();
    }

    @Bean
    @Qualifier("rewardsService")
    public RewardsService rewardsService() { return new RewardsService();}

    @Bean
    @Qualifier("notificationService")
    public NotificationService notificationService() { return new NotificationService();}

    @Bean
    public DataSource sqliteDataSource(
            @Value("${db.path}") String dbPath) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:"+dbPath+"dbtelegrambot.sqlite");
        dataSource.setUsername("na");
        dataSource.setPassword("na");

        return dataSource;
    }
}
