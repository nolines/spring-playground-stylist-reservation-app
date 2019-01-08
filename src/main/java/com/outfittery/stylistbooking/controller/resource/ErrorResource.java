package com.outfittery.stylistbooking.controller.resource;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ErrorResource {

  @Getter private final List<ErrorItem> errors = new ArrayList<>();
}
