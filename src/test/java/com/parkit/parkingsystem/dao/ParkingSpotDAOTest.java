package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSpotDAOTest {
    ParkingSpotDAO parkingSpotDAO;
    ParkingSpot parkingSpot;

    @BeforeEach
    void setUp() {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpot = new ParkingSpot(10, ParkingType.CAR, false);
        //ParkingSpot parkingSpotFalse = parkingSpot;
        //parkingSpotFalse.setAvailable(false);
    }

    @Test
    void shouldNextAvailableSlotForCar() {
        int testInt = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
        assertEquals(2 , testInt);
    }

    @Test
    void updateParking() {
        ParkingSpot parkingSpotTrue = parkingSpot;
        //parkingSpotTrue.setAvailable(true);

        boolean result = parkingSpotDAO.updateParking(parkingSpotTrue);
        assertTrue(result);
    }
}