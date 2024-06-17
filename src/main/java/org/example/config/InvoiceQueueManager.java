package org.example.config;

import org.example.core.poller.InvoiceProcessor;
import org.example.model.Invoice;

import java.util.concurrent.ConcurrentLinkedQueue;

public class InvoiceQueueManager {

    private ConcurrentLinkedQueue<Invoice> invoiceQueue;
    private InvoiceProcessor invoiceProcessor;
    private static InvoiceQueueManager invoiceQueueManager = null;

    private InvoiceQueueManager() {
        invoiceQueue = new ConcurrentLinkedQueue<>();
        invoiceProcessor = new InvoiceProcessor(invoiceQueue);
    }

    public static synchronized InvoiceQueueManager getInstance()
    {
        if (invoiceQueueManager == null)
            invoiceQueueManager = new InvoiceQueueManager();

        return invoiceQueueManager;
    }

    public void addInvoice(Invoice invoice) {
        invoiceQueue.offer(invoice);
        if(!invoiceProcessor.getPollerInstanceRunning()){
            invoiceProcessor.setPollerInstanceRunning(true);
            Thread thread = new Thread(invoiceProcessor);
            thread.setName("invoice-processing");
            thread.start();
        }
    }

    public Invoice getInvoice() {
        return invoiceQueue.poll();
    }

    public boolean isEmpty() {
        return invoiceQueue.isEmpty();
    }

    public long getInvoiceQueueSize(){
        return invoiceQueue.size();
    }

    public long getProcessInvoiceCount(){
        return invoiceProcessor.getSuccessCounter();
    }
}