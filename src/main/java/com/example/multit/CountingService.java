package com.example.multit;

import java.util.concurrent.CountDownLatch;

public interface CountingService {
    void threadExecutorWithLatch() throws InterruptedException;
}
