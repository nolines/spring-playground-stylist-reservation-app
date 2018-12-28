package com.outfittery.stylistbooking.controller;

import com.outfittery.stylistbooking.controller.dto.AppointmentDto;
import com.outfittery.stylistbooking.controller.resource.AppointmentResource;
import com.outfittery.stylistbooking.exception.CustomerNotfoundException;
import com.outfittery.stylistbooking.model.Appointment;
import com.outfittery.stylistbooking.service.AppointmentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/appointments")
@Api(value="users", description="Endpoint for user management")
public class AppointmentController {

  @Autowired private AppointmentService appointmentService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<AppointmentResource> createAppointment(
      @RequestBody @Valid final AppointmentDto appointmentDto) throws Exception {

    log.info("Appointment creation request received!");
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(appointmentService.create(appointmentDto));
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<Appointment>> getAllAppointments() {

    log.info("Appointment get request received!");
    return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAll());
  }

  @GetMapping("/{name}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<AppointmentResource> getAppointmentForCustomer(@PathVariable String name) throws CustomerNotfoundException {

    log.info("Appointment get request received for customer" + name);
    return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAppointmentByCustomer(name));
  }
}
