package com.javalab.movieapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Dauren_Altynbekov on 22-Apr-19.
 */
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class},
        scanBasePackages = "com.javalab.movieapp")
@ComponentScan(basePackages = {"com.javalab.movieapp"})
public class MovieApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieApplication.class);
    }
}
