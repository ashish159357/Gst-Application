package org.example.core.poller;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.example.config.DatabaseConfig;
import org.example.dao.InvoiceDao;
import org.example.model.Invoice;
import org.example.utils.GstCalculator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;


public class InvoiceProcessor implements Runnable {

    private final ConcurrentLinkedQueue<Invoice> invoiceQueue;
    private BasicThreadFactory factory ;
    private ExecutorService workerThreadPool;
    private final AtomicLong successCounter;
    private final InvoiceDao invoiceDao = new InvoiceDao(DatabaseConfig.getDataSource());
    private boolean pollerInstanceRunning;
    int numberOfThreads = 3;

    public InvoiceProcessor(ConcurrentLinkedQueue<Invoice> invoiceQueue) {
        this.invoiceQueue = invoiceQueue;
        factory =  new BasicThreadFactory.Builder().namingPattern("GST-Calculator-%d").daemon(true).priority(Thread.MAX_PRIORITY).build();
        workerThreadPool = Executors.newFixedThreadPool(numberOfThreads,factory);
        successCounter = new AtomicLong();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if(!invoiceQueue.isEmpty()){
                Invoice invoice = invoiceQueue.poll();

                // Worker Thread
                workerThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (invoice != null) {
                            double gstAmount = GstCalculator.calculateGst(invoice.getAmount(), invoice.getGstRate());
                            invoice.setGstAmount(gstAmount);
                            invoiceDao.insertInvoice(invoice);
                            successCounter.incrementAndGet();
                        }
                    }
                });
            }
            else {
                // Adding a small delay to prevent busy spinning
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public long getSuccessCounter(){
        return successCounter.get();
    }

    public boolean getPollerInstanceRunning(){
        return this.pollerInstanceRunning;
    }

    public void setPollerInstanceRunning(boolean flag){
        this.pollerInstanceRunning = flag;
    }

}

