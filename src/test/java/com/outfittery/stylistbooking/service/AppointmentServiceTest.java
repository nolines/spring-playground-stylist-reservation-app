package com.outfittery.stylistbooking.service;

import com.outfittery.stylistbooking.controller.dto.AppointmentDto;
import com.outfittery.stylistbooking.controller.resource.AppointmentResource;
import com.outfittery.stylistbooking.exception.CustomerNotfoundException;
import com.outfittery.stylistbooking.exception.TimeSlotNotFoundException;
import com.outfittery.stylistbooking.model.Appointment;
import com.outfittery.stylistbooking.model.Customer;
import com.outfittery.stylistbooking.model.Stylist;
import com.outfittery.stylistbooking.model.TimeSlot;
import com.outfittery.stylistbooking.repository.AppointmentRepository;
import com.outfittery.stylistbooking.repository.CustomerRepository;
import com.outfittery.stylistbooking.repository.StylistRepository;
import com.outfittery.stylistbooking.repository.TimeSlotRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AppointmentServiceTest {

  @Mock private AppointmentRepository appointmentRepositoryMock;
  @Mock private CustomerRepository customerRepositoryMock;
  @Mock private StylistRepository stylistRepositoryMock;
  @Mock private TimeSlotRepository timeSlotRepositoryMock;
  @InjectMocks private AppointmentService underTest;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @Test
  public void shouldCreateAppointment() throws Exception {
    when(customerRepositoryMock.findById(any())).thenReturn(createCustomerEntity());
    when(timeSlotRepositoryMock.findTimeSlotByTimeGreaterThanEqual(1400))
        .thenReturn(Collections.singletonList(new TimeSlot(1400, createStyleEntity(), null)));

    when(appointmentRepositoryMock.save(any())).thenReturn(createAppointmentEntity());

    AppointmentDto appointmentDto = createAppointmentDto();
    appointmentDto.setTime(1400);

    AppointmentResource expected = underTest.create(appointmentDto);

    Assert.assertEquals(expected.getCustomerId(), "anyCustomerId");
    Assert.assertEquals(expected.getStylistId(), "anyStylistId");
  }

  @Test(expected = TimeSlotNotFoundException.class)
  public void shouldThrowExceptionWhenCreateAppointment() throws Exception {
    when(customerRepositoryMock.findById(any())).thenReturn(createCustomerEntity());
    when(timeSlotRepositoryMock.findTimeSlotByTimeGreaterThanEqual(any())).thenReturn(null);

    when(appointmentRepositoryMock.save(any())).thenReturn(createAppointmentEntity());

    underTest.create(createAppointmentDto());
  }

  @Test
  public void shouldGetAllAppointments() {
    when(appointmentRepositoryMock.findAll())
        .thenReturn(Collections.singletonList(createAppointmentEntity()));

    List<Appointment> appointments = underTest.getAll();

    Assert.assertEquals(appointments.size(), 1);
    Assert.assertNotNull(appointments);
    Assert.assertEquals(appointments.get(0).getCustomer().getId(), "anyCustomerId");
    Assert.assertEquals(appointments.get(0).getStylist().getId(), "anyStylistId");
  }

  @Test
  public void shouldGetAppointmentByCustomer() throws CustomerNotfoundException {

    when(appointmentRepositoryMock.findAppointmentByCustomerId(anyString()))
        .thenReturn(createAppointmentEntity());

    AppointmentResource expected = underTest.getAppointmentByCustomer("anyCustomerName");

    Assert.assertEquals(expected.getCustomerId(), "anyCustomerId");
    Assert.assertEquals(expected.getStylistId(), "anyStylistId");
  }

  @Test(expected = CustomerNotfoundException.class)
  public void shouldThrowExceptionWhenThereIsNoRelatedCustomer() throws CustomerNotfoundException {

    when(appointmentRepositoryMock.findAppointmentByCustomerId(anyString())).thenReturn(null);

    underTest.getAppointmentByCustomer("anyCustomerName");
  }

  private AppointmentDto createAppointmentDto() {
    AppointmentDto appointmentDto = new AppointmentDto();
    appointmentDto.setTime(930);
    appointmentDto.setCustomerId("anyCustomerId");

    return appointmentDto;
  }

  private Optional<Customer> createCustomerEntity() {
    Customer customer = new Customer();
    customer.setId("anyCustomerId");
    customer.setName("anyCustomerName");
    customer.setEmail("anyCustomerEmail");

    return Optional.of(customer);
  }

  private Appointment createAppointmentEntity() {

    Appointment appointment = new Appointment();
    appointment.setTimeSlot(createTimeSlotEntity());
    appointment.setStylist(createStyleEntity());

    Customer customer = new Customer();
    customer.setEmail("anyEmail");
    customer.setName("anyCustomerName");
    customer.setId("anyCustomerId");

    appointment.setCustomer(customer);
    appointment.setId("anyId");

    return appointment;
  }

  private TimeSlot createTimeSlotEntity() {

    TimeSlot timeSlot = new TimeSlot();
    timeSlot.setAppointment(new Appointment());
    timeSlot.setStylist(createStyleEntity());
    timeSlot.setTime(1400);

    return timeSlot;
  }

  private Stylist createStyleEntity() {
    Stylist stylist = new Stylist();
    stylist.setId("anyStylistId");
    stylist.setName("anyStylistName");

    return stylist;
  }
}
