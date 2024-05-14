package com.example.multit;

import com.example.multit.services.CountingService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MultiTApplication {


    public static void main(String[] args) {
        SpringApplication.run(MultiTApplication.class, args);
    }



}