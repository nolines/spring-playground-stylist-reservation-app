package com.outfittery.stylistbooking.controller.resource;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ErrorItem {
    private final String message;
}
