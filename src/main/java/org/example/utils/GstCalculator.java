package org.example.utils;


public class GstCalculator {
    public static double calculateGst(double amount, double gstRate) {
        return amount * gstRate / 100;
    }
}
