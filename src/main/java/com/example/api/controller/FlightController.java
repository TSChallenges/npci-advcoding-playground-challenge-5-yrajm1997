package com.example.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.api.model.Flight;

import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.api.repository.FlightRepository;

@RestController
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    @PostMapping
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
      flight.setId(null);  
        Flight savedFlight = flightRepository.save(flight);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFlight);
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getFlights(@RequestParam(required = false) String origin, @RequestParam(required = false) String orderBy) {
        List<Flight> flights = flightRepository.findAll();
        if (origin != null) {
            flights = flights.stream().filter(f -> f.getOrigin().equalsIgnoreCase(origin)).collect(Collectors.toList());
        }
        if (orderBy != null) {
            if ("destination".equals(orderBy)) {
                flights.sort(Comparator.comparing(Flight::getDestination).thenComparing(Flight::getId));
            } else if ("-destination".equals(orderBy)) {
                flights.sort(Comparator.comparing(Flight::getDestination).thenComparing(Flight::getId).reversed());
            }
        }
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Integer id) {
        Optional<Flight> flight = flightRepository.findById(id);
        return flight.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
