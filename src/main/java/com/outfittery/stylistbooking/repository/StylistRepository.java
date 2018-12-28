package com.outfittery.stylistbooking.repository;

import com.outfittery.stylistbooking.model.Stylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StylistRepository extends JpaRepository<Stylist, String> {}
