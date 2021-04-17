package com.fred.node.app;

import com.fred.node.chain.StatusService;
import com.fred.node.chain.data.TransactionService;
import com.fred.node.monitor.MonitorService;
import com.fred.node.monitor.NotificationService;
import com.fred.node.monitor.TelegramMonitorBot;
import com.fred.node.monitor.price.PriceService;
import com.fred.node.ubix.RewardsService;
import com.fred.node.ubix.transactions.sync.TransactionWatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

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

}
