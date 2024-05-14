package com.example.multit.controller;
import com.example.multit.services.CountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountingController {

    private final CountingService countingService;

    @Autowired
    public CountingController(CountingService countingService) {
        this.countingService = countingService;
    }

    @GetMapping("/executeThreads")
    public String executeThreads(
            @RequestParam(value = "threadsNumber", defaultValue = "1") int threadsNumber,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        try {
            countingService.threadExecutorWithLatch(threadsNumber, limit);
            return "Thread execution initiated. Check logs for details.";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Error executing threads: " + e.getMessage();
        }
    }
}