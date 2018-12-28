package com.outfittery.stylistbooking.controller;

import com.outfittery.stylistbooking.controller.dto.CustomerDto;
import com.outfittery.stylistbooking.controller.resource.CustomerResource;
import com.outfittery.stylistbooking.model.Customer;
import com.outfittery.stylistbooking.service.CustomerService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer")
@Api
public class CustomerController {

  private CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @RequestMapping(
    method = RequestMethod.POST,
    produces = APPLICATION_JSON_VALUE,
    consumes = APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CustomerResource> create(
      @RequestBody @Valid final CustomerDto customerDto) {

    log.info("Customer creation request received!");
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(customerService.createNewCustomer(customerDto));
  }

  @RequestMapping(
    value = "/{id}",
    method = RequestMethod.PUT,
    produces = APPLICATION_JSON_VALUE,
    consumes = APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<CustomerResource> update(
      @PathVariable final String id, @RequestBody @Valid final CustomerDto customerDto)
      throws Exception {

    log.info("Customer update request received!");
    return ResponseEntity.status(HttpStatus.OK)
        .body(customerService.updateCustomer(id, customerDto));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Customer> get(@PathVariable final String id) throws Exception {

    log.info("Customer get request received!");
    return ResponseEntity.ok().body(customerService.getCustomer(id));
  }

  @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<Customer>> getAll() throws Exception {

    log.info("Customer getAll request received!");
    return ResponseEntity.ok().body(customerService.getAll());
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(NO_CONTENT)
  public void delete(@PathVariable final String id) {
    customerService.deleteCustomer(id);
  }
}
