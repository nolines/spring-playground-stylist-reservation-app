package com.outfittery.stylistbooking.controller.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class CustomerDto {
  @NotBlank(message = "Name cannot be blank.")
  private String name;
  @NotBlank(message = "Email cannot be blank.")
  private String email;
}
