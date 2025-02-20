package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketDAOTest {
    private TicketDAO ticketDAO = new TicketDAO();


    @Test
    void shouldSaveTicket() {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );

        Ticket ticket = new Ticket();
        ticket.setParkingSpot(new ParkingSpot(4, ParkingType.CAR, true));
        ticket.setVehicleRegNumber("JKL012");
        ticket.setPrice(10);
        ticket.setInTime(inTime);
        System.out.println("## intime = " + inTime);
        ticket.setOutTime(new Date());
        ticketDAO.saveTicket(ticket);

        Ticket savedTicket = ticketDAO.getTicket("JKL012");
        assertEquals("JKL012", savedTicket.getVehicleRegNumber());
    }

    @Test
    void shouldFindTicket() {
        Ticket ticket = ticketDAO.getTicket("DEF456");
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );

        Ticket ticket1 = new Ticket();
        ticket1.setId(2);
        ticket1.setParkingSpot(new ParkingSpot(2, ParkingType.BIKE, false));
        ticket1.setVehicleRegNumber("DEF456");
        ticket1.setPrice(5);
        ticket1.setInTime(inTime);
        ticket1.setOutTime(new Date());

        assertEquals(ticket1.getVehicleRegNumber(), ticket.getVehicleRegNumber());
    }

    @Test
    void shouldUpdateTicket() {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );

        Ticket ticket = ticketDAO.getTicket("DEF456");
        ticket.setParkingSpot(new ParkingSpot(2, ParkingType.BIKE, false));
        ticket.setVehicleRegNumber("DEF456");
        ticket.setPrice(12);
        ticket.setInTime(inTime);
        ticket.setOutTime(new Date());
        System.out.println("## UpDate Ticket getTicket() => " + ticket.toString());
        ticketDAO.updateTicket(ticket);

        Ticket updatedTicket = ticketDAO.getTicket("DEF456");
        System.out.println("## UpdateTicket : "+ updatedTicket.getPrice());
        assertEquals(12, updatedTicket.getPrice());
    }

    @Test
    void shouldGetTicketsByVehicleRegNumber() {
        List<Ticket> tickets = ticketDAO.getNbTicket("DLY190");

        assertEquals(12, tickets.size());
    }

}