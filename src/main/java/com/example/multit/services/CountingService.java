package com.example.multit.services;

public interface CountingService {
    void threadExecutorWithLatch(int threadNumber, int limit) throws InterruptedException;

}
