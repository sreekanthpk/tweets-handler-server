package org.sree.rest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(value="org.sree")
@EnableAutoConfiguration
@PropertySource("classpath:/properties/env.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);              
    }
}