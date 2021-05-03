package com.fred.node.app;

import com.fred.node.app.db.DBRepository;
import com.fred.node.price.JDBCPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;


@SpringBootApplication
@ComponentScan("com.fred")
public class MonitorApp implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DBRepository dbRepo;

    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("App ready, let's do some init stuff");
        try {
            dbRepo.updateDBSchema();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SpringApplication.run(MonitorApp.class);


    }


}
