package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareDiscountCalculator extends Fare30MinutesCalculator{
    private final TicketDAO ticketDAO;

    public FareDiscountCalculator(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    @Override
    public void calculateFare(Ticket ticket) {
        Ticket ticketBdd = findVehicleRegNumber(ticket.getVehicleRegNumber());
        if (ticketBdd!= null) calculateFare(ticketBdd, true);
        else calculateFare(ticket, false);
    }

    /**
     * Calculate fare with discount for regular users
     * @param ticket ticket to calculate
     * @param discount discount for regular users
     */
    public void calculateFare(Ticket ticket, boolean discount) {
        double duration = getDuration(ticket);
        if (!discount || duration < 0.5) super.calculateFare(ticket);

        System.out.println("Heureux de vous revoir ! En tant qu’utilisateur régulier de notre parking, vous allez obtenir une remise de 5%");
        System.out.println("Get Reg number:  "+ ticket.getVehicleRegNumber());

        ticket.setPrice(duration);
        System.out.println("Get price:  "+ ticket.getPrice());
        ticket.setPrice(applyDiscount(ticket.getPrice(), 5));
        // Il manque le prix de la voiture
    }

    /**
     * Search vehicle in the database
     * @param vehicleRegNumber reg Number of the vehicle
     * @return ticket
     */
    public Ticket findVehicleRegNumber(String vehicleRegNumber) {
        return ticketDAO.getTicket(vehicleRegNumber);
    }

    /**
     * Applies a discount to the original price of a ticket.
     * @param originalPrice The original price of the ticket.
     * @param discountPercentage The discount percentage to be applied.
     * @return The discounted price.
     */
    public double applyDiscount(double originalPrice, double discountPercentage) {
        return originalPrice * (1 - discountPercentage / 100);
    }

}
