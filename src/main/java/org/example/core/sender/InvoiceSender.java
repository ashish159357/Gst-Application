package org.example.core.sender;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.example.core.storage.InvoiceQueue;
import org.example.model.Invoice;

public class InvoiceSender implements Runnable{
    private InvoiceQueue invoiceQueue;

    public InvoiceSender(InvoiceQueue invoiceQueue){
        this.invoiceQueue = invoiceQueue;
    }

    @Override
    public void run() {
        int amount = 1000;
        int invoiceId = 1;

        while (true){
            amount = amount + 100;
            invoiceId = invoiceId + 1;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            invoiceQueue.addInvoice(new Invoice(invoiceId, amount, 18));
        }
    }

    public InvoiceQueue getInvoiceQueue(){
        return invoiceQueue;
    }
}
