package com.outfittery.stylistbooking.controller.dto;

import com.outfittery.stylistbooking.model.StylistState;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Data
public class StylistDto {
  @NotBlank(message = "Name cannot be blank.")
  private String name;

  private StylistState stylistState = StylistState.READY;

  @Setter(AccessLevel.NONE)
  private List<Integer> timeSlots = new ArrayList<>();
}
