package com.outfittery.stylistbooking.exception.handler;

import com.outfittery.stylistbooking.controller.resource.ErrorItem;
import com.outfittery.stylistbooking.controller.resource.ErrorResource;
import com.outfittery.stylistbooking.exception.CustomerNotfoundException;
import com.outfittery.stylistbooking.exception.InvalidTimeException;
import com.outfittery.stylistbooking.exception.StylistNotFoundException;
import com.outfittery.stylistbooking.exception.TimeSlotNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler {

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResource> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex) {
    return handleException(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(CustomerNotfoundException.class)
  public ResponseEntity<ErrorResource> handleCustomerNotfoundException(
      CustomerNotfoundException ex) {
    return handleException(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(InvalidTimeException.class)
  public ResponseEntity<ErrorResource> handleCustomerNotfoundException(InvalidTimeException ex) {
    return handleException(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(StylistNotFoundException.class)
  public ResponseEntity<ErrorResource> handleStylistNotFoundException(StylistNotFoundException ex) {
    return handleException(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(TimeSlotNotFoundException.class)
  public ResponseEntity<ErrorResource> handleTimeSlotNotFoundException(
      TimeSlotNotFoundException ex) {
    return handleException(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResource> handleUnexpectedException(Exception ex) {
    return handleException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  private ResponseEntity<ErrorResource> handleException(
      HttpStatus httpStatus, String errorMessage) {
    ErrorResource errorResource = new ErrorResource();
    ErrorItem errorItem = ErrorItem.builder().message(errorMessage).build();

    errorResource.getErrors().add(errorItem);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new ResponseEntity<>(errorResource, headers, httpStatus);
  }
}
