package com.outfittery.stylistbooking.controller.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AppointmentResource {
  private String id;
  private String customerId;
  private String stylistId;
//  private Integer date;
}
