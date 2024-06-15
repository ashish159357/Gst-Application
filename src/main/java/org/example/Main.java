package org.example;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.example.core.poller.InvoiceProcessor;
import org.example.core.sender.InvoiceSender;
import org.example.core.storage.InvoiceQueue;
import org.example.model.Invoice;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Main implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        InvoiceQueue invoiceQueue = new InvoiceQueue();

        Thread invoiceSender = new Thread(new InvoiceSender(invoiceQueue));
        invoiceSender.setName("invoice-sender");
        invoiceSender.start();

        Thread invoicePoller = new Thread(new InvoiceProcessor(invoiceQueue));
        invoicePoller.setName("invoice-poller");
        invoicePoller.start();
    }
}