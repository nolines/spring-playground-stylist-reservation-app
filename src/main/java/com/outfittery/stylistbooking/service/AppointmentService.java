package com.outfittery.stylistbooking.service;

import com.outfittery.stylistbooking.controller.dto.AppointmentDto;
import com.outfittery.stylistbooking.controller.resource.AppointmentResource;
import com.outfittery.stylistbooking.exception.CustomerNotfoundException;
import com.outfittery.stylistbooking.exception.InvalidTimeException;
import com.outfittery.stylistbooking.exception.TimeSlotNotFoundException;
import com.outfittery.stylistbooking.model.Appointment;
import com.outfittery.stylistbooking.model.Customer;
import com.outfittery.stylistbooking.model.TimeSlot;
import com.outfittery.stylistbooking.repository.AppointmentRepository;
import com.outfittery.stylistbooking.repository.CustomerRepository;
import com.outfittery.stylistbooking.repository.StylistRepository;
import com.outfittery.stylistbooking.repository.TimeSlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppointmentService {

  @Value("#{'${timeSlotFormats}'.split(',')}")
  private List<Integer> timeSlotFormats;

  private AppointmentRepository appointmentRepository;
  private CustomerRepository customerRepository;
  private CustomerService customerService;
  private StylistRepository stylistRepository;
  private StylistService stylistService;
  private TimeSlotRepository timeSlotRepository;

  public AppointmentService(
      AppointmentRepository appointmentRepository,
      CustomerRepository customerRepository,
      StylistService stylistService,
      StylistRepository stylistRepository,
      CustomerService customerService,
      TimeSlotRepository timeSlotRepository) {
    this.appointmentRepository = appointmentRepository;
    this.customerRepository = customerRepository;
    this.stylistService = stylistService;
    this.stylistRepository = stylistRepository;
    this.customerService = customerService;
    this.timeSlotRepository = timeSlotRepository;
  }

  public AppointmentResource create(AppointmentDto appointmentDto) throws Exception {
    validateInput(Collections.singletonList(appointmentDto.getTime()));
    Customer customer =
        customerRepository
            .findById(appointmentDto.getCustomerId())
            .orElseThrow(() -> new CustomerNotfoundException("Customer cannot be found!"));

    List<TimeSlot> timeSlots =
        timeSlotRepository.findTimeSlotByTimeGreaterThanEqual(appointmentDto.getTime());

    if (null == timeSlots) {
      throw new TimeSlotNotFoundException("This timeslot cannot be found.");
    }

    Optional<TimeSlot> optionalTimeSlot =
        timeSlots.stream().filter((_timeSlot) -> null == _timeSlot.getAppointment()).findFirst();

    if (!optionalTimeSlot.isPresent()) {
      throw new TimeSlotNotFoundException("Timeslot cannot be reserved.");
    }

    TimeSlot timeSlot = optionalTimeSlot.get();
    Appointment appointment = new Appointment();
    appointment.setCustomer(customer);
    appointment.setStylist(timeSlot.getStylist());
    appointment.setTimeSlot(timeSlot);

    timeSlot.setAppointment(appointment);
    timeSlotRepository.save(timeSlot);

    return convertEntityToResource(timeSlot.getAppointment());
  }

  public List<Appointment> getAll() {
    return new ArrayList<>(appointmentRepository.findAll());
  }

  public AppointmentResource getAppointmentByCustomer(String customerId)
      throws CustomerNotfoundException {

    Appointment appointment = appointmentRepository.findAppointmentByCustomerId(customerId);

    if (null == appointment) {
      throw new CustomerNotfoundException("No appointment found for customer " + customerId);
    }

    return convertEntityToResource(appointment);
  }

  public List<TimeSlot> getAvailableTimeSlots() {

    List<TimeSlot> timeSlots = timeSlotRepository.findAll();
    List<Appointment> appointments = appointmentRepository.findAll();
    List<TimeSlot> reservedTimeSlots = new ArrayList<>();

    appointments
        .stream()
        .filter((appointment -> reservedTimeSlots.add(appointment.getTimeSlot())))
        .collect(Collectors.toList());

    for (int i = 0; i < reservedTimeSlots.size(); i++) {
      for (int j = 0; j < timeSlots.size(); j++) {
        if (reservedTimeSlots.get(i).getId().equals(timeSlots.get(j).getId())) {
          timeSlots.remove(j);
        }
      }
    }

    return timeSlots;
  }

  private AppointmentResource convertEntityToResource(Appointment appointment) {
    return new AppointmentResource(
        appointment.getId(), appointment.getCustomer().getId(), appointment.getStylist().getId());
  }

  private void validateInput(List<Integer> timeSlots) throws Exception {
    boolean match = false;
    for (Integer timeSlot : timeSlots) {
      if (timeSlot < 900 || timeSlot > 1600) {
        throw new InvalidTimeException("Time should between 900 and 1600");
      }

      match = timeSlotFormats.contains(timeSlot);

      if (!match) {
        throw new InvalidTimeException(
            "Time should be formatted like 9AM:900,9:30AM:930,10AM:1000,14PM:1400");
      }
    }
  }
}
