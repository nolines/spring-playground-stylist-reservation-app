package com.outfittery.stylistbooking.service;

import com.outfittery.stylistbooking.controller.dto.StylistDto;
import com.outfittery.stylistbooking.controller.resource.StylistResource;
import com.outfittery.stylistbooking.exception.InvalidTimeException;
import com.outfittery.stylistbooking.exception.StylistNotFoundException;
import com.outfittery.stylistbooking.model.Stylist;
import com.outfittery.stylistbooking.model.TimeSlot;
import com.outfittery.stylistbooking.repository.StylistRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StylistService {

  @Value("#{'${timeSlotFormats}'.split(',')}")
  private List<Integer> timeSlotFormats;

  private StylistRepository stylistRepository;

  public StylistService(StylistRepository stylistRepository) {
    this.stylistRepository = stylistRepository;
  }

  public StylistResource addNewStylist(StylistDto stylistDto) throws Exception {

    validateInput(stylistDto.getTimeSlots());

    Stylist stylist = new Stylist();
    stylist.setName(stylistDto.getName());

    stylistRepository.save(stylist);

    stylistDto
        .getTimeSlots()
        .forEach(
            (time) -> {
              stylist.getTimeSlots().add(new TimeSlot(time, stylist, null));
            });

    return convertEntityToResource(stylistRepository.save(stylist));
  }

  public Stylist getStylist(String id) throws StylistNotFoundException {
    return stylistRepository
        .findById(id)
        .orElseThrow(
            () -> new StylistNotFoundException("Stylist with id:" + id + " cannot be found!"));
  }

  public StylistResource updateStylist(String id, StylistDto stylistDto) {
    return null;
  }

  public void deleteStylist(String id) {
    stylistRepository.deleteById(id);
  }

  public List<Stylist> getAll() {
    return new ArrayList<>(stylistRepository.findAll());
  }

  private StylistResource convertEntityToResource(Stylist stylist) {

    return new StylistResource(stylist.getId(), stylist.getName());
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
