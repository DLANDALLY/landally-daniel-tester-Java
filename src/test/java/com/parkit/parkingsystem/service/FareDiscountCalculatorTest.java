package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FareDiscountCalculatorTest {
    private static FareDiscountCalculator fareDiscountCalculator;
    private static TicketDAO ticketDAO;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
        ticketDAO = mock(TicketDAO.class);
        fareDiscountCalculator = new FareDiscountCalculator(ticketDAO);
    }

    @BeforeEach
    void setUpPerTest() {
        ticket = new Ticket();
    }


    @Test
    void calculateFare() {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareDiscountCalculator.calculateFare(ticket);
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
    }

    @Test
    public void calculateFareCarWithDiscountDescription(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date halfHour = new Date();
        halfHour.setTime( System.currentTimeMillis() - (  30 * 60 * 1000) ); //30 munites
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("AA-123-BB");

        fareDiscountCalculator.calculateFare(ticket, true);
        //assertEquals(true, ticket.getVehicleRegNumber());
        assertEquals( (0.95 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
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
        //fareCalculatorService.calculateFare(ticket, true);
        //assertEquals(true, ticket.getVehicleRegNumber());
        assertEquals( (0.95 * Fare.BIKE_RATE_PER_HOUR) , ticket.getPrice());
        assertThrows(IllegalArgumentException.class, () -> fareDiscountCalculator.calculateFare(ticket));
    }

    @Test
    void testCalculateFare() {
    }

    @Test
    void findVehicleRegNumber() {
    }

    @Test
    void applyDiscount() {
    }
}