package com.outfittery.stylistbooking.controller.dto;

import lombok.Data;

@Data
public class AppointmentDto {
  private String customerId;

  /** Example Input ( 900,930,1000,1030,1100) */
  private Integer time;
}
