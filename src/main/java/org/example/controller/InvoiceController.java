package org.example.controller;

import org.example.model.Invoice;
import org.example.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping(value = "/create")
    public ResponseEntity<String> createInvoice(@RequestBody Invoice invoice){
        try{
            invoiceService.createInvoice(invoice);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Successfully create Invoice");
    }
}
