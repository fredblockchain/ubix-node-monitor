package com.fred.node.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.fred")
public class Watcher {

    public static void main(String[] args) {


        SpringApplication.run(Watcher.class);


    }


}
