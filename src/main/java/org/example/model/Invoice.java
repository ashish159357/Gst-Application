package org.example.model;

public class Invoice {
    private final int invoiceId;
    private final double amount;
    private final double gstRate;
    private double gstAmount;

    public Invoice(int invoiceId, double amount, double gstRate) {
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.gstRate = gstRate;
        this.gstAmount = 0.0;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public double getAmount() {
        return amount;
    }

    public double getGstRate() {
        return gstRate;
    }

    public synchronized double getGstAmount() {
        return gstAmount;
    }

    public synchronized void setGstAmount(double gstAmount) {
        this.gstAmount = gstAmount;
    }
}
