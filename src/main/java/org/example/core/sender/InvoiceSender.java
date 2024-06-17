//package org.example.core.sender;
//
//import org.apache.commons.lang3.concurrent.BasicThreadFactory;
//import org.example.core.storage.InvoiceQueue;
//import org.example.model.Invoice;
//
//import java.util.concurrent.atomic.AtomicLong;
//
//public class InvoiceSender implements Runnable{
//    private InvoiceQueue invoiceQueue;
//    private long recieveCounter;
//
//    public InvoiceSender(InvoiceQueue invoiceQueue){
//        this.invoiceQueue = invoiceQueue;
//    }
//
//    @Override
//    public void run() {
//        int amount = 1000;
//        int invoiceId = 1;
//
//        while (!Thread.currentThread().isInterrupted()){
//            amount = amount + 100;
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            invoiceQueue.addInvoice(new Invoice(invoiceId, amount, 18));
//            recieveCounter += 1;
//            invoiceId = invoiceId + 1;
//        }
//    }
//
//    public InvoiceQueue getInvoiceQueue(){
//        return invoiceQueue;
//    }
//
//    public long getRecieveCounter(){
//        return recieveCounter;
//    }
//}
