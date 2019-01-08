package com.outfittery.stylistbooking.repository;

import com.outfittery.stylistbooking.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
  Appointment findAppointmentByCustomerId(String customerId);
}
