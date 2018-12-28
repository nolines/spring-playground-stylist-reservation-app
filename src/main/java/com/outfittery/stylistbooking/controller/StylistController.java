package com.outfittery.stylistbooking.controller;

import com.outfittery.stylistbooking.controller.dto.StylistDto;
import com.outfittery.stylistbooking.controller.resource.StylistResource;
import com.outfittery.stylistbooking.model.Stylist;
import com.outfittery.stylistbooking.service.StylistService;
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
@RequestMapping(value = "/api/v1/stylist")
@Api
public class StylistController {

  private StylistService stylistService;

  public StylistController(StylistService stylistService) {
    this.stylistService = stylistService;
  }

  @RequestMapping(
    method = RequestMethod.POST,
    produces = APPLICATION_JSON_VALUE,
    consumes = APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<StylistResource> create(@RequestBody @Valid final StylistDto stylistDto) {

    log.info("Stylist creation request received!");
    return ResponseEntity.status(HttpStatus.CREATED).body(stylistService.addNewStylist(stylistDto));
  }

  @RequestMapping(
    value = "/{id}",
    method = RequestMethod.PUT,
    produces = APPLICATION_JSON_VALUE,
    consumes = APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<StylistResource> update(
      @PathVariable final String id, @RequestBody @Valid final StylistDto stylistDto) {

    log.info("Stylist update request received!");
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(stylistService.updateStylist(id, stylistDto));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Stylist> get(@PathVariable final String id) throws Exception {

    log.info("Stylist get request received!");
    return ResponseEntity.ok().body(stylistService.getStylist(id));
  }

  @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<Stylist>> getAll() throws Exception {

    log.info("Stylist getAll request received!");
    return ResponseEntity.ok().body(stylistService.getAll());
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(NO_CONTENT)
  public void delete(@PathVariable final String id) {
    stylistService.deleteStylist(id);
  }
}
