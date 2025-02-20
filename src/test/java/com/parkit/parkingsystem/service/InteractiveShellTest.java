package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class InteractiveShellTest {
    private InteractiveShell interactiveShell;

    @Test
    void shouldLoadInterface() {
        InputReaderUtil inputReaderUtil = mock(InputReaderUtil.class);
        ParkingService parkingService = mock(ParkingService.class);

        when(inputReaderUtil.readSelection()).thenReturn(3);

        InteractiveShell parkingSystem;

        verify(parkingService, never()).processIncomingVehicle();
        verify(parkingService, never()).processExitingVehicle();
    }
}