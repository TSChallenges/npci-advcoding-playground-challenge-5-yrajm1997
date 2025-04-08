package com.example.api.repository;

import java.util.List;

import com.example.api.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    List<Flight> findByOrigin(String origin);
}
