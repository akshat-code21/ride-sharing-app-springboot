package com.akshat.uber.service;

import com.akshat.uber.exception.NotFoundException;
import com.akshat.uber.model.Ride;
import com.akshat.uber.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RideService {
    @Autowired
    private RideRepository rideRepository;

    public Ride createRide(Ride ride, String userId) {
        ride.setUserId(userId);
        ride.setStatus("REQUESTED");
        ride.setCreatedAt(LocalDate.now());
        return rideRepository.save(ride);
    }

    public List<Ride> getAvailableRides() {
        return rideRepository.findByStatus("REQUESTED");
    }

    public Ride acceptRide(String rideId, String driverId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));
        if (!"REQUESTED".equals(ride.getStatus())) {
            throw new RuntimeException("Ride is not available");
        }
        ride.setDriverId(driverId);
        ride.setStatus("ACCEPTED");
        return rideRepository.save(ride);
    }

    public Ride completeRide(String rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!"ACCEPTED".equals(ride.getStatus())) {
            throw new RuntimeException("Ride cannot be completed unless accepted");
        }

        ride.setStatus("COMPLETED");
        return rideRepository.save(ride);
    }

    public List<Ride> getMyRides(String userId) {
        return rideRepository.findByUserId(userId);
    }

}
