package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TicketDAOTest {
    private TicketDAO ticketDAO;

    @BeforeEach
    void setUp() {
        ticketDAO = new TicketDAO();
    }

    @Test
    void shouldSaveTicket() {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );

        Ticket ticket = new Ticket();
        ticket.setParkingSpot(new ParkingSpot(4, ParkingType.CAR, true));
        ticket.setVehicleRegNumber("JKL012");
        ticket.setPrice(10);
        ticket.setInTime(inTime);
        ticket.setOutTime(new Date());
        ticketDAO.saveTicket(ticket);

        Ticket savedTicket = ticketDAO.getTicket("JKL012");
        assertEquals("JKL012", savedTicket.getVehicleRegNumber());
    }

    @Test
    void shouldFindTicket() {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );

        Ticket ticket1 = new Ticket();
        ticket1.setId(2);
        ticket1.setParkingSpot(new ParkingSpot(2, ParkingType.BIKE, false));
        ticket1.setVehicleRegNumber("DA321DA");
        ticket1.setPrice(5);
        ticket1.setInTime(inTime);
        ticket1.setOutTime(new Date());
        ticketDAO.saveTicket(ticket1);

        Ticket ticket = ticketDAO.getTicket("DA321DA");

        assertEquals(ticket1.getVehicleRegNumber(), ticket.getVehicleRegNumber());
    }

    @Test
    void shouldUpdateTicket() {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );

        Ticket ticket = ticketDAO.getTicket("DA321DA");
        ticket.setPrice(120.0);
        ticketDAO.updateTicket(ticket);

        assertEquals(120.0, ticketDAO.getTicket("DA321DA").getPrice());
    }

    @Test
    void shouldGetTicketsByVehicleRegNumber() {
        double nticket = ticketDAO.getNbTicket("AB123CD").size() + 1;

        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );

        Ticket ticket = new Ticket();
        ticket.setId(2);
        ticket.setParkingSpot(new ParkingSpot(2, ParkingType.BIKE, false));
        ticket.setVehicleRegNumber("AB123CD");
        ticket.setPrice(5);
        ticket.setInTime(inTime);
        ticket.setOutTime(new Date());
        ticketDAO.saveTicket(ticket);

        assertEquals(nticket, ticketDAO.getNbTicket("AB123CD").size());
    }
}