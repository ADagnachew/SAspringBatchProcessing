package com.example.saspringbatchapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SaSpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaSpringBatchApplication.class, args);
    }

}
