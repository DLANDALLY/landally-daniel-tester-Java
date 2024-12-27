package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.Ticket;

public class Fare30MinutesCalculator extends FareCalculatorService{
    private final double fareFree  = 0.5;
    @Override
    public void calculateFare(Ticket ticket) {
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) )
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());

        Long inHour = ticket.getInTime().getTime();
        Long outHour = ticket.getOutTime().getTime();

        double duration = convertMillisecondToHour(outHour - inHour);
        System.out.println("duration: " + duration);

        if (duration > fareFree) super.calculateFare(ticket);
        else ticket.setPrice(0.0);
    }
}
