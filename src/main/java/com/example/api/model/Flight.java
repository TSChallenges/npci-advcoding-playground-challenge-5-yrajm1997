package com.example.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.*;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String flight;
    private String origin;
    private String destination;
    private List<Integer> speed_series;

public Integer getId(){
    return id;
}
    public String getFlight(){
        return flight;
    }

    public String getOrigin(){
        return origin;
    }

    public String getDestination(){
        return destination;
    }

    public List<Integer> getSpeedSeries(){
        return speed_series;
    }

    public void setId(Integer id){
        this.id=id;
    }
    public void setFlight(String flight){
        this.flight=flight;
    }

    public void setOrigin(String origin){
        this.origin=origin;
    }

    public void setDestination(String destination){
        this.destination=destination;
    }

    public void setSpeedSeries(List<Integer> speedSeries){
        speedSeries=speed_series;
    }
}
