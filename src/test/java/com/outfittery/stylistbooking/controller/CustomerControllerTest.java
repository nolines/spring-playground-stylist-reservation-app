package com.outfittery.stylistbooking.controller;

import com.outfittery.stylistbooking.controller.dto.CustomerDto;
import com.outfittery.stylistbooking.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;

public class CustomerControllerTest {

  @Mock private CustomerService customerServiceMock;
  @InjectMocks private CustomerController underTest;

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void shouldCreateCustomer() {

    underTest.create(createCustomerDto());

    Mockito.verify(customerServiceMock, Mockito.times(1)).createNewCustomer(any(CustomerDto.class));
  }

  @Test
  public void shouldGetCustomer() throws Exception {
    underTest.get("anyId");

    Mockito.verify(customerServiceMock, Mockito.times(1)).getCustomer("anyId");
  }

  @Test
  public void shouldGetAllCustomer() throws Exception {
    underTest.getAll();

    Mockito.verify(customerServiceMock, Mockito.times(1)).getAll();
  }

  @Test
  public void shouldDeleteCustomer() {
    underTest.delete("anyId");
    Mockito.verify(customerServiceMock, Mockito.times(1)).deleteCustomer("anyId");
  }

  //TODO:asd
  @Test
  public void shouldUpdateCustomer() {}

  private CustomerDto createCustomerDto() {
    CustomerDto customerDto = new CustomerDto();
    customerDto.setName("anyName");
    customerDto.setEmail("anyEmail");

    return customerDto;
  }
}
