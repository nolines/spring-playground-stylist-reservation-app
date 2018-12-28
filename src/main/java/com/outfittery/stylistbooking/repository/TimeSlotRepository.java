package com.outfittery.stylistbooking.repository;

import com.outfittery.stylistbooking.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, String> {
  List<TimeSlot> findTimeSlotByTimeGreaterThanEqual(Integer time);
}
