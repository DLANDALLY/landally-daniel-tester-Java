package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.model.Ticket;

public class FareDiscountCalculator extends Fare30MinutesCalculator{

    /**
     * Calculate fare with discount for regular users
     * @param ticket ticket to calculate
     * @param discount discount for regular users
     */
    public void calculateFare(Ticket ticket, boolean discount) {
        double duration = getDuration(ticket);
        if (!discount || duration <= 0.5) {
            super.calculateFare(ticket);
            return;
        }

        ticket.setPrice(duration);
        ticket.setPrice(applyDiscount(ticket.getPrice(), 5, typeRatePerHour(ticket)));
    }


    /**
     * Applies a discount to the original price of a ticket.
     * @param originalPrice The original price of the ticket.
     * @param discountPercentage The discount percentage to be applied.
     * @param typeRatePerHour The type rate per hour to be applied
     * @return The discounted price.
     */
    public double applyDiscount(double originalPrice, int discountPercentage, double typeRatePerHour) {
        double price = (typeRatePerHour - (typeRatePerHour * discountPercentage / 100)) * originalPrice;
        return aroud(price);
    }

    private double aroud(double originalPrice){
        return Math.round(originalPrice * 100.0) / 100.0;
    }

}
