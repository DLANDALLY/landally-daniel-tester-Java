package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {
    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown(){

    }

    @Test
    public void testParkingACar(){
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();

        assertEquals("ABCDEF", ticketDAO.getTicket("ABCDEF").getVehicleRegNumber());
    }

    @Test
    public void testParkingLotExit(){
        getIncomingVehicle().processIncomingVehicle();
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - ( 60 * 60 * 1000) );
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        ticket.setInTime(inTime);
        ticketDAO.saveTicket(ticket);

        getIncomingVehicle().processExitingVehicle();

        assertEquals( Fare.CAR_RATE_PER_HOUR, ticketDAO.getTicket("ABCDEF").getPrice());
        assertNotNull(ticketDAO.getTicket("ABCDEF").getOutTime());
    }

    @Test
    public void testParkingLotExitRecurringUser(){
        for (int i = 0; i < 3; i++) {
            getIncomingVehicle().processIncomingVehicle();
            getIncomingVehicle().processExitingVehicle();
        }

        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - ( 60 * 60 * 1000) );
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        ticket.setInTime(inTime);
        ticketDAO.saveTicket(ticket);

        getIncomingVehicle().processExitingVehicle();

        double value = ticketDAO.getTicket("ABCDEF").getPrice();
        double rounded = Math.round(value * 100.0) / 100.0;

        assertTrue(ticketDAO.getNbTicket("ABCDEF").size() > 2);
        assertEquals( 1.43 , rounded);
    }

    public ParkingService getIncomingVehicle(){
        return new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    }
}