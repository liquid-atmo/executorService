package com.example.multit;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RequiredArgsConstructor
public class MultiTApplication {

private final CountingService countingService;

    public static void main(String[] args) {
        SpringApplication.run(MultiTApplication.class, args);
    }

    @PostConstruct
    public void executeThreadExecutorWithLatch() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        System.out.println("**** Thread Pool Started: " + startTime + " **** ");
        countingService.threadExecutorWithLatch();
        long endTime = System.currentTimeMillis();
        System.out.println("**** Thread Pool Finished: " + endTime + " **** ");
        System.out.println("**** Total time for all Threads: " + (endTime - startTime) + " **** ");
    }

}