package com.outfittery.stylistbooking.service;

import com.outfittery.stylistbooking.controller.dto.StylistDto;
import com.outfittery.stylistbooking.controller.resource.StylistResource;
import com.outfittery.stylistbooking.exception.InvalidTimeException;
import com.outfittery.stylistbooking.exception.StylistNotFoundException;
import com.outfittery.stylistbooking.model.Stylist;
import com.outfittery.stylistbooking.model.StylistState;
import com.outfittery.stylistbooking.model.TimeSlot;
import com.outfittery.stylistbooking.repository.StylistRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class StylistServiceTest {

  @Mock private StylistRepository stylistRepositoryMock;
  @InjectMocks private StylistService underTest;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @Test
  public void shouldAddNewStylist() throws Exception {

    List<Integer> integers = Arrays.asList(900,930,1000,1030,1100,1130,1200,1230,1300,1330,1400,1430,1500,1530,1600);

    ReflectionTestUtils.setField(underTest, "timeSlotFormats", integers);
    Stylist stylist = createStylistEntity();

    when(stylistRepositoryMock.save(any())).thenReturn(stylist);

    StylistResource stylistResource = new StylistResource("anyId", "anyName");

    StylistResource expected = underTest.addNewStylist(createStylistDto());

    Mockito.verify(stylistRepositoryMock, Mockito.times(2)).save(any(Stylist.class));

    Assert.assertEquals(expected.getId(), stylistResource.getId());
    Assert.assertEquals(expected.getName(), stylistResource.getName());
  }

  @Test
  public void shouldGetStylist() throws StylistNotFoundException {
    when(stylistRepositoryMock.findById("anyId"))
        .thenReturn(java.util.Optional.ofNullable(createStylistEntity()));

    Stylist expected = underTest.getStylist("anyId");

    Assert.assertEquals(expected.getId(), "anyId");
    Assert.assertEquals(java.util.Optional.ofNullable(expected.getTimeSlots().get(0).getTime()), Optional.of(900));
    Assert.assertEquals(java.util.Optional.ofNullable(expected.getTimeSlots().get(1).getTime()), Optional.of(930));
    Assert.assertEquals(java.util.Optional.ofNullable(expected.getTimeSlots().get(2).getTime()), Optional.of(1000));
    Assert.assertEquals(java.util.Optional.ofNullable(expected.getTimeSlots().get(3).getTime()), Optional.of(1030));
    Assert.assertEquals(java.util.Optional.ofNullable(expected.getTimeSlots().get(4).getTime()), Optional.of(1100));
    Assert.assertEquals(java.util.Optional.ofNullable(expected.getTimeSlots().get(5).getTime()), Optional.of(1130));
    Assert.assertEquals(java.util.Optional.ofNullable(expected.getTimeSlots().get(6).getTime()), Optional.of(1200));
    Assert.assertEquals(java.util.Optional.ofNullable(expected.getTimeSlots().get(7).getTime()), Optional.of(1230));
  }

  @Test
  public void shouldDeleteStylist() {

    underTest.deleteStylist("anyId");

    Mockito.verify(stylistRepositoryMock, Mockito.times(1)).deleteById("anyId");
  }

  @Test
  public void shouldGetAllStylists() {
    when(stylistRepositoryMock.findAll())
            .thenReturn(Collections.singletonList(createStylistEntity()));

    List<Stylist> stylistList = underTest.getAll();

    Mockito.verify(stylistRepositoryMock, Mockito.times(1)).findAll();
    Assert.assertEquals(stylistList.size(), 1);
  }

  @Test
  public void shouldUpdateStylist() {}

  private StylistDto createStylistDto() {
    StylistDto stylistDto = new StylistDto();
    stylistDto.setStylistState(StylistState.READY);
    stylistDto.setName("anyName");

    stylistDto.getTimeSlots().add(900);
    stylistDto.getTimeSlots().add(930);
    stylistDto.getTimeSlots().add(1000);
    stylistDto.getTimeSlots().add(1030);
    stylistDto.getTimeSlots().add(1100);
    stylistDto.getTimeSlots().add(1130);
    stylistDto.getTimeSlots().add(1200);
    stylistDto.getTimeSlots().add(1230);

    return stylistDto;
  }

  private Stylist createStylistEntity() {
    Stylist stylist = new Stylist();
    stylist.setName("anyName");
    stylist.setId("anyId");

    stylist.getTimeSlots().add(new TimeSlot(900, null, null));
    stylist.getTimeSlots().add(new TimeSlot(930, null, null));
    stylist.getTimeSlots().add(new TimeSlot(1000, null, null));
    stylist.getTimeSlots().add(new TimeSlot(1030, null, null));
    stylist.getTimeSlots().add(new TimeSlot(1100, null, null));
    stylist.getTimeSlots().add(new TimeSlot(1130, null, null));
    stylist.getTimeSlots().add(new TimeSlot(1200, null, null));
    stylist.getTimeSlots().add(new TimeSlot(1230, null, null));

    return stylist;
  }
}
