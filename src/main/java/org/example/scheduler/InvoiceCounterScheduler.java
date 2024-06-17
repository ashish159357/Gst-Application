package org.example.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.example.config.InvoiceQueueManager;
import org.example.core.poller.InvoiceProcessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InvoiceCounterScheduler {

    // Task to be run every 3 seconds
    @Scheduled(fixedRate = 3000)
    public void reportCurrentTime() {
        InvoiceQueueManager invoiceQueueManager = InvoiceQueueManager.getInstance();
        log.info("In-Queue : {}, Success : {}", invoiceQueueManager.getInvoiceQueueSize(), invoiceQueueManager.getProcessInvoiceCount());
    }
}
