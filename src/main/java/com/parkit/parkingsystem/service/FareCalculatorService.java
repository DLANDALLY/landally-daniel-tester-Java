package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        double duration = getDuration(ticket);
        ticket.setPrice(duration * typeRatePerHour(ticket));
    }

    public double getDuration(Ticket ticket) {
        return convertMillisecondToHour(
                ticket.getOutTime().getTime() - ticket.getInTime().getTime());
    }

    public double convertMillisecondToHour(Long milliseconds){
        return milliseconds / (60.0 * 60.0 * 1000.0);
    }

    public double typeRatePerHour(Ticket ticket){
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: return Fare.CAR_RATE_PER_HOUR;
            case BIKE: return Fare.BIKE_RATE_PER_HOUR;
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}