package com.example.api.controller;

//public class FlightController {
//}

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.model.Flight;
import com.example.api.repository.FlightRepository;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightRepository flightRepository;

    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight savedFlight = flightRepository.save(flight);
        return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getFlights(
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "orderBy", required = false) String orderBy) {

        List<Flight> flights;
        if (origin != null) {
            flights = flightRepository.findByOrigin(origin);
        } else {
            flights = flightRepository.findAll();
        }

        if ("destination".equals(orderBy)) {
            flights.sort((f1, f2) -> f1.getDestination().compareTo(f2.getDestination()));
        } else if ("-destination".equals(orderBy)) {
            flights.sort((f1, f2) -> f2.getDestination().compareTo(f1.getDestination()));
        }

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Integer id) {
        return flightRepository.findById(id)
                .map(flight -> new ResponseEntity<>(flight, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
