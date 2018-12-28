package com.outfittery.stylistbooking.controller;

import com.outfittery.stylistbooking.controller.dto.StylistDto;
import com.outfittery.stylistbooking.exception.InvalidTimeException;
import com.outfittery.stylistbooking.model.StylistState;
import com.outfittery.stylistbooking.service.StylistService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;

public class StylistControllerTest {

  @Mock StylistService stylistServiceMock;
  @InjectMocks private StylistController underTest;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @Test
  public void shouldCreateStylist() throws Exception {
    underTest.create(createStylistDto());

    Mockito.verify(stylistServiceMock, Mockito.times(1)).addNewStylist(createStylistDto());
  }

  @Test
  public void shouldGetStylist() throws Exception {
    underTest.get("anyId");

    Mockito.verify(stylistServiceMock, Mockito.times(1)).getStylist("anyId");
  }

  @Test
  public void shouldGetAllStylists() throws Exception {
    underTest.getAll();

    Mockito.verify(stylistServiceMock, Mockito.times(1)).getAll();
  }

  @Test
  public void shouldDeleteStylists() {
    underTest.delete("anyId");

    Mockito.verify(stylistServiceMock, Mockito.times(1)).deleteStylist("anyId");
  }

  private StylistDto createStylistDto() {
    StylistDto stylistDto = new StylistDto();
    stylistDto.setName("anyName");
    stylistDto.setStylistState(StylistState.READY);

    return stylistDto;
  }
}
