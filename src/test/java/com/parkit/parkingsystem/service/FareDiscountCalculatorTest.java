package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FareDiscountCalculatorTest {
    private static FareDiscountCalculator fareDiscountCalculator;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
        fareDiscountCalculator = new FareDiscountCalculator();
    }

    @BeforeEach
    void setUpPerTest() {
        ticket = new Ticket();
    }

    @Test
    public void calculateFareCarWithDiscountDescription(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("AA-123-BB");

        fareDiscountCalculator.calculateFare(ticket, true);
        assertEquals( ( 1.43) , ticket.getPrice());
        // PAs endessous de 30 minutes
        //assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithDiscountDescription(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date halfHour = new Date();
        halfHour.setTime( System.currentTimeMillis() - (  30 * 60 * 1000) ); //30 munites
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("AA-123-BB");

        fareDiscountCalculator.calculateFare(ticket, true);

        assertEquals( (0.95 * Fare.BIKE_RATE_PER_HOUR) , ticket.getPrice());
        //assertThrows(IllegalArgumentException.class, () -> fareDiscountCalculator.calculateFare(ticket));
    }



    @Test
    void applyDiscount() {
        double originalPrice = 1.0;
        int discountPercentage = 5;
        double fare = 1.5;

        double discountFare = fare - ((fare * discountPercentage) /100);
        double expected = originalPrice * discountFare;
        double expectedRoud = Math.round(expected * 100.0) / 100.0;

        double result = fareDiscountCalculator.applyDiscount(originalPrice, discountPercentage, fare);
        assertEquals(expectedRoud, result);
    }
}