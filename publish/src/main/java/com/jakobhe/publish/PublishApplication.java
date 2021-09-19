package com.jakobhe.publish;

import com.jakobhe.publish.controller.HealthCheckController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = HealthCheckController.class)
public class PublishApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublishApplication.class, args);
    }
}
