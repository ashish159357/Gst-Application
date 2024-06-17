package org.example.model;

import lombok.Data;

@Data
public class Invoice {

    private final int invoiceId;
    private final double amount;
    private final double gstRate;
    private double gstAmount;
}
