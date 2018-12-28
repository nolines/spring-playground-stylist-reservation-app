package com.outfittery.stylistbooking.service;

import com.outfittery.stylistbooking.controller.dto.CustomerDto;
import com.outfittery.stylistbooking.controller.resource.CustomerResource;
import com.outfittery.stylistbooking.exception.CustomerNotfoundException;
import com.outfittery.stylistbooking.model.Customer;
import com.outfittery.stylistbooking.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomerService {

  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public CustomerResource createNewCustomer(CustomerDto customerDto) {

    Customer customer = new Customer();
    customer.setEmail(customerDto.getEmail());
    customer.setName(customerDto.getName());

    return convertEntityToResource(customerRepository.save(customer));
  }

  public CustomerResource updateCustomer(String id, CustomerDto customerDto) throws CustomerNotfoundException {

    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new CustomerNotfoundException("Customer with id: " + id + " cannot be found!"));

    customer.setName(customerDto.getName());
    customer.setEmail(customerDto.getEmail());

    return convertEntityToResource(customerRepository.save(customer));
  }

  public List<Customer> getAll() {
    return new ArrayList<>(customerRepository.findAll());
  }

  public Customer getCustomer(String id) throws CustomerNotfoundException {
    return customerRepository
        .findById(id)
        .orElseThrow(() -> new CustomerNotfoundException("Customer with id: " + id + " cannot be found!"));
  }

  public void deleteCustomer(String id) {
    customerRepository.deleteById(id);
  }

  private CustomerResource convertEntityToResource(Customer customer) {
    return new CustomerResource(customer.getId(), customer.getName(), customer.getEmail());
  }
}
