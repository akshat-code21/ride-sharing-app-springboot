package com.akshat.uber.controller;

import com.akshat.uber.dto.CreateRideRequest;
import com.akshat.uber.model.Ride;
import com.akshat.uber.model.User;
import com.akshat.uber.repository.UserRepository;
import com.akshat.uber.service.RideService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class RideController {
    @Autowired
    private RideService rideService;

    @Autowired
    private UserRepository userRepository;

    private User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/rides")
    public ResponseEntity<?> requestRide(@RequestBody @Valid CreateRideRequest request) {
        User user = getLoggedInUser();

        Ride ride = new Ride();
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());

        Ride savedRide = rideService.createRide(ride, user.getId());

        return ResponseEntity.ok(savedRide);
    }

    @GetMapping("/user/rides")
    public ResponseEntity<List<Ride>> getMyRides() {
        User user = getLoggedInUser();
        return ResponseEntity.ok(rideService.getMyRides(user.getId()));
    }

    @GetMapping("/driver/rides/requests")
    public ResponseEntity<List<Ride>> getPendingRides() {
        return ResponseEntity.ok(rideService.getAvailableRides());
    }

    @PostMapping("/driver/rides/{rideId}/accept")
    public ResponseEntity<?> acceptRide(@PathVariable String rideId) {
        User driver = getLoggedInUser();
        Ride ride = rideService.acceptRide(rideId, driver.getId());
        return ResponseEntity.ok(Map.of("message", "Ride Accepted", "ride", ride));
    }

    @PostMapping("/rides/{rideId}/complete")
    public ResponseEntity<?> completeRide(@PathVariable String rideId) {
        Ride ride = rideService.completeRide(rideId);
        return ResponseEntity.ok(Map.of("message", "Ride Completed", "ride", ride));
    }
}
