package com.outfittery.stylistbooking.service;

import com.outfittery.stylistbooking.controller.dto.CustomerDto;
import com.outfittery.stylistbooking.controller.resource.CustomerResource;
import com.outfittery.stylistbooking.exception.CustomerNotfoundException;
import com.outfittery.stylistbooking.model.Customer;
import com.outfittery.stylistbooking.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CustomerServiceTest {

  @Mock private CustomerRepository customerRepositoryMock;
  @InjectMocks private CustomerService underTest;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @Test
  public void shouldCreateNewCustomer() {
    when(customerRepositoryMock.save(any(Customer.class))).thenReturn(createCustomerEntity());

    CustomerDto customerDto = new CustomerDto();
    customerDto.setEmail("ANY_EMAIL");
    customerDto.setName("ANY_NAME");

    CustomerResource expected = underTest.createNewCustomer(customerDto);

    Assert.assertEquals(expected.getEmail(), "ANY_EMAIL");
    Assert.assertEquals(expected.getName(), "ANY_NAME");
    Assert.assertNotNull(expected.getId());

    Mockito.verify(customerRepositoryMock, times(1)).save(any(Customer.class));
  }

  @Test
  public void shouldGetAllStylists() {
    when(customerRepositoryMock.findAll())
        .thenReturn(Collections.singletonList(createCustomerEntity()));

    List<Customer> customers = underTest.getAll();

    Assert.assertEquals(customers.size(), 1);
    Assert.assertEquals(customers.get(0).getEmail(), "ANY_EMAIL");
    Assert.assertEquals(customers.get(0).getName(), "ANY_NAME");
    Assert.assertNotNull(customers.get(0).getId());

    Mockito.verify(customerRepositoryMock, times(1)).findAll();
  }

  @Test
  public void shouldGetCustomer() throws CustomerNotfoundException {
    when(customerRepositoryMock.findById("anyId"))
        .thenReturn(java.util.Optional.ofNullable(createCustomerEntity()));

    Customer expected = underTest.getCustomer("anyId");

    Assert.assertEquals(expected.getEmail(), "ANY_EMAIL");
    Assert.assertEquals(expected.getName(), "ANY_NAME");
    Assert.assertNotNull(expected.getId());

    Mockito.verify(customerRepositoryMock, times(1)).findById(any(String.class));
  }

  @Test
  public void shouldDeleteCustomer() {
    underTest.deleteCustomer("anyId");

    verify(customerRepositoryMock, times(1)).deleteById("anyId");
  }

  @Test
  public void shouldUpdateCustomer() {}

  private Customer createCustomerEntity() {
    Customer customer = new Customer();
    customer.setEmail("ANY_EMAIL");
    customer.setName("ANY_NAME");
    customer.setId("ANY_ID");

    return customer;
  }
}
