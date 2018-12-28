package com.outfittery.stylistbooking.service;

import com.outfittery.stylistbooking.controller.dto.AppointmentDto;
import com.outfittery.stylistbooking.controller.resource.AppointmentResource;
import com.outfittery.stylistbooking.exception.CustomerNotfoundException;
import com.outfittery.stylistbooking.exception.TimeSlotNotFoundException;
import com.outfittery.stylistbooking.model.Appointment;
import com.outfittery.stylistbooking.model.Customer;
import com.outfittery.stylistbooking.model.TimeSlot;
import com.outfittery.stylistbooking.repository.AppointmentRepository;
import com.outfittery.stylistbooking.repository.CustomerRepository;
import com.outfittery.stylistbooking.repository.StylistRepository;
import com.outfittery.stylistbooking.repository.TimeSlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AppointmentService {

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

    Customer customer =
        customerRepository
            .findById(appointmentDto.getCustomerId())
            .orElseThrow(() -> new Exception(""));

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

  public AppointmentResource getAppointmentByCustomer(String name)
      throws CustomerNotfoundException {

    Appointment appointment = appointmentRepository.findAppointmentByCustomerId(name);

    if (null == appointment) {
      throw new CustomerNotfoundException("No appointment found for customer " + name);
    }

    return convertEntityToResource(appointment);
  }

  private AppointmentResource convertEntityToResource(Appointment appointment) {
    return new AppointmentResource(
        appointment.getId(), appointment.getCustomer().getId(), appointment.getStylist().getId());
  }
}
