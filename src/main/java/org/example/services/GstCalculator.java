package org.example.services;


public class GstCalculator {
    public static double calculateGst(double amount, double gstRate) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return amount * gstRate / 100;
    }
}
