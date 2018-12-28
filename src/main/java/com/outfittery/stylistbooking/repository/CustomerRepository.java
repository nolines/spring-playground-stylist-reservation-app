package com.outfittery.stylistbooking.repository;

import com.outfittery.stylistbooking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByName(String name);
}
