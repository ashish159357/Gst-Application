package org.example.core.storage;

import org.example.model.Invoice;

import java.util.concurrent.ConcurrentLinkedQueue;

public class InvoiceQueue {
    private final ConcurrentLinkedQueue<Invoice> queue = new ConcurrentLinkedQueue<>();

    public void addInvoice(Invoice invoice) {
        queue.add(invoice);
    }

    public Invoice pollInvoice() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

