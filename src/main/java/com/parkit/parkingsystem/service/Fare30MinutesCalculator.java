package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.model.Ticket;

public class Fare30MinutesCalculator extends FareCalculatorService{
    @Override
    public void calculateFare(Ticket ticket) {
        double fareFree = 0.5;
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) )
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());

        double duration = getDuration(ticket);
        getFareFree(ticket, fareFree, duration);
    }

    public void getFareFree(Ticket ticket, double fareFree, double duration){
        if (duration > fareFree) super.calculateFare(ticket);
        else ticket.setPrice(0.0);
    }

}
