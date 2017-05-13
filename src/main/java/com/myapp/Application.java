package com.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.autoconfigure.SpringBootApplication; 
 
@EnableCaching //If caching is enabled in framework
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Application {
 
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

