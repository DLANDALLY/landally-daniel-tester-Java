package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.Fare30MinutesCalculator;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.FareDiscountCalculator;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {
    @Mock
    private ParkingSpotDAO parkingSpotDAO;
    @Mock
    private TicketDAO ticketDAO;
    @Mock
    private InputReaderUtil inputReaderUtil;
    @Mock
    private Ticket ticket;
    @InjectMocks
    private ParkingService parkingService;
    private ParkingSpot parkingSpot;
    private FareCalculatorService fareCalculatorService;
    private FareCalculatorService fareDiscountCalculator;

    @BeforeEach
    public void setUpPerTest() throws Exception {
        fareCalculatorService = new Fare30MinutesCalculator();
        fareDiscountCalculator = new FareDiscountCalculator();

        String vehicleRegNumber = "AZ123AZ";
        inputReaderUtil.readSelection();
        parkingService.getVehicleRegNumber();

        ticket = new Ticket();
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        parkingSpot.setId(1);

        // initialize ticket
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("AZ123AZ");
        ticket.setPrice(0);
        ticket.setInTime(inTime);
        ticket.setOutTime(null);

        //fareCalculatorService.calculateFare(ticket);
    }

    @Test
    public void testProcessIncomingVehicle() throws Exception {
        // Arrange
        //ParkingSpot mockSpot = new ParkingSpot(1, ParkingType.CAR, true); TODO a supp
        when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(10);

        String vehicleRegNumber = "AZ123AZ";
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingService.getVehicleRegNumber()).thenReturn(vehicleRegNumber);

        Ticket mockTicketBDD = ticket;
        when(ticketDAO.getTicket(vehicleRegNumber)).thenReturn(mockTicketBDD);
        when(ticketDAO.getNbTicket(vehicleRegNumber)).thenReturn(new ArrayList<>());

        // Act
        parkingService.processIncomingVehicle();

        // Assert
        Mockito.verify(parkingSpotDAO).updateParking(Mockito.any(ParkingSpot.class));
        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        Mockito.verify(ticketDAO).saveTicket(ticketCaptor.capture());
        assertEquals(vehicleRegNumber, ticketCaptor.getValue().getVehicleRegNumber());
    }

    @Test
    public void processExitingVehicleTest() throws Exception {
        // Arrange
        Ticket ticket1 = this.ticket;
        String vehicleRegNumber = ticket1.getVehicleRegNumber();
        when(parkingService.getVehicleRegNumber()).thenReturn(vehicleRegNumber);
        when(ticketDAO.getTicket(vehicleRegNumber)).thenReturn(ticket1);

        List<Ticket> mockList = mock(List.class);
        when(ticketDAO.getNbTicket(vehicleRegNumber)).thenReturn(mockList);
        when(mockList.size()).thenReturn(1);

        ticket1.setOutTime(new Date());
        fareCalculatorService.calculateFare(ticket1);
        when(ticketDAO.updateTicket(ticket1)).thenReturn(true);

        parkingSpot.setAvailable(true);
        ParkingSpot spot = parkingSpot;
        when(parkingSpotDAO.updateParking(spot)).thenReturn(true);

        // Act
        parkingService.processExitingVehicle();

        // Assert
        assertEquals(1.5, ticket1.getPrice(), 0.01);
        assertEquals("AZ123AZ", ticket1.getVehicleRegNumber());
        verify(ticketDAO).updateTicket(ticket1);
        verify(parkingSpotDAO).updateParking(parkingSpot);
    }

    @Test
   public void processExitingVehicleTestUnableUpdate() throws Exception {
        // Arrange
        String vehicleRegNumber = ticket.getVehicleRegNumber();
        when(parkingService.getVehicleRegNumber()).thenReturn(vehicleRegNumber);
        when(ticketDAO.getTicket(vehicleRegNumber)).thenReturn(ticket);

        List<Ticket> mockList = mock(List.class);
        when(ticketDAO.getNbTicket(vehicleRegNumber)).thenReturn(mockList);
        when(mockList.size()).thenReturn(1);

        ticket.setOutTime(new Date());
        fareCalculatorService.calculateFare(ticket);
        when(ticketDAO.updateTicket(ticket)).thenReturn(false);

        // Act
        parkingService.processExitingVehicle();

        // Assert
        verify(ticketDAO, times(1)).updateTicket(ticket);
        verify(parkingSpotDAO, never()).updateParking(any(ParkingSpot.class));
        assertEquals(false, ticketDAO.updateTicket(ticket));
    }

    @Test
    public void testGetNextParkingNumberIfAvailable1() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);

        ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();

        assertNotNull(parkingSpot);
        assertEquals(1, parkingSpot.getId());
        assertTrue(parkingSpot.isAvailable());
    }

    @Test
    public void testGetNextParkingNumberIfAvailableParkingNumberNotFound(){
        //test de l’appel de la méthode getNextParkingNumberIfAvailable() avec pour résultat aucun spot disponible
        // (la méthode renvoie null).
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(0);

        assertNull(parkingService.getNextParkingNumberIfAvailable());
        verify(parkingSpotDAO, times(1)).getNextAvailableSlot(ParkingType.CAR);
    }

    @Test
    public void testGetNextParkingNumberIfAvailableParkingNumberWrongArgument(){
        //test de l’appel de la méthode getNextParkingNumberIfAvailable() avec pour résultat aucun spot
        // (la méthode renvoie null) car l’argument saisi par l’utilisateur concernant le type de véhicule est erroné
        // (par exemple, l’utilisateur a saisi 3).

        when(inputReaderUtil.readSelection()).thenReturn(3);
        when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
        assertNull(parkingService.getNextParkingNumberIfAvailable());
    }




}
