package com.outfittery.stylistbooking.controller;

import com.outfittery.stylistbooking.controller.dto.AppointmentDto;
import com.outfittery.stylistbooking.exception.CustomerNotfoundException;
import com.outfittery.stylistbooking.service.AppointmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.MockitoAnnotations.initMocks;

public class AppointmentControllerTest {

  @Mock private AppointmentService appointmentServiceMock;
  @InjectMocks private AppointmentController underTest;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @Test
  public void shouldCreateAppointment() throws Exception {

    AppointmentDto appointmentDto = new AppointmentDto();
    appointmentDto.setCustomerId("ANY_CUSTOMER_ID");
    appointmentDto.setTime(12);

    underTest.createAppointment(appointmentDto);

    Mockito.verify(appointmentServiceMock, Mockito.times(1)).create(appointmentDto);
  }

  @Test
  public void shouldGetAllAppointments() {

    underTest.getAllAppointments();

    Mockito.verify(appointmentServiceMock, Mockito.times(1)).getAll();
  }

  @Test
  public void shouldGetAppointmentForCustomer() throws CustomerNotfoundException {

    underTest.getAppointmentForCustomer("ANY_NAME");

    Mockito.verify(appointmentServiceMock, Mockito.times(1)).getAppointmentByCustomer("ANY_NAME");
  }
}
