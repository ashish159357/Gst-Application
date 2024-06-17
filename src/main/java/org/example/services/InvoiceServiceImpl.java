package org.example.services;

import org.example.config.InvoiceQueueManager;
import org.example.model.Invoice;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Override
    public void createInvoice(Invoice invoice) {
        InvoiceQueueManager invoiceQueueManager = InvoiceQueueManager.getInstance();
        invoiceQueueManager.addInvoice(invoice);
    }
}
