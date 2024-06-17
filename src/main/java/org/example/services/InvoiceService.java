package org.example.services;

import org.example.model.Invoice;
import org.springframework.stereotype.Service;

@Service
public interface InvoiceService {
    public void createInvoice(Invoice invoice);
}
