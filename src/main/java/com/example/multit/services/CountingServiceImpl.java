package com.example.multit.services;

import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;


@Service
public class CountingServiceImpl implements CountingService {

     static class Worker implements Runnable {
        private final CountDownLatch latch;
        private final String name;
        private final int limit;
    private final int threadsNumber;

        public Worker(CountDownLatch latch, String name, int limit, int threadsNumber) {
            this.latch = latch;
            this.name = name;
            this.limit = limit;
            this.threadsNumber = threadsNumber;
        }
        @Override
        public void run() {
            int counter = 0;
            while (counter < (limit / threadsNumber)) {
                doWork();
                counter++;
            }
            latch.countDown();
            System.out.println(name + " finished; counter ="  + counter );
        }
        public void doWork() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name + " doing work");
        }
    }
    public void threadExecutorWithLatch(int threadsNumber, int limit) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadsNumber);
        long start = Instant.now().toEpochMilli();
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new Worker(latch, "Thread-" + i, limit, threadsNumber));
            long startOneThread = Instant.now().toEpochMilli();
            System.out.println(("Thread-" + i) + " started at " + startOneThread + " ms");
            thread.start();
        }
        latch.await();
        long end = Instant.now().toEpochMilli();
        System.out.println("Thread pool finished at " + end + " ms");
        System.out.println("Total time used: " + (end - start));
    }



}
