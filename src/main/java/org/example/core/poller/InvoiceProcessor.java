package org.example.core.poller;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.example.core.storage.InvoiceQueue;
import org.example.model.Invoice;
import org.example.services.GstCalculator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InvoiceProcessor implements Runnable {
    private final InvoiceQueue invoiceQueue;
    private BasicThreadFactory factory ;
    ExecutorService workerThreadPool;
    int numberOfThreads = 3;


    public InvoiceProcessor(InvoiceQueue invoiceQueue) {
        this.invoiceQueue = invoiceQueue;
        factory =  new BasicThreadFactory.Builder().namingPattern("GST-Calculator-%d").daemon(true).priority(Thread.MAX_PRIORITY).build();
        workerThreadPool = Executors.newFixedThreadPool(numberOfThreads,factory);
    }

    @Override
    public void run() {
        while (true) {
            if(!invoiceQueue.isEmpty()){
                Invoice invoice = invoiceQueue.pollInvoice();
                workerThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (invoice != null) {
                            double gstAmount = GstCalculator.calculateGst(invoice.getAmount(), invoice.getGstRate());
                            invoice.setGstAmount(gstAmount);
                            System.out.println("Processed Invoice ID: " + invoice.getInvoiceId() + ", GST Amount: " + gstAmount);
                        }
                    }
                });

            }
        }
    }
}

