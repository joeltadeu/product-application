package com.product.consumer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.kinesis.coordinator.Scheduler;

@Component
@RequiredArgsConstructor
public class KinesisRunner {
    private final Scheduler scheduler;

    @PostConstruct
    public void run() {
        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.setDaemon(true);
        schedulerThread.start();
    }
}
