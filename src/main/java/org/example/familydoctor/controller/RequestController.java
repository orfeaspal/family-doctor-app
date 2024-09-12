package org.example.familydoctor.controller;

import org.example.familydoctor.model.Request;
import org.example.familydoctor.model.RequestStatus;
import org.example.familydoctor.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<Request> createRequest(
            @RequestParam Long citizenId,
            @RequestParam Long doctorId,
            @RequestParam RequestStatus status) { // Use RequestStatus enum here
        try {
            Request request = requestService.createRequest(citizenId, doctorId, status);
            return ResponseEntity.status(HttpStatus.CREATED).body(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<List<Request>> getRequestsByCitizenId(@PathVariable Long citizenId) {
        List<Request> requests = requestService.getRequestsByCitizenId(citizenId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Request>> getRequestsByDoctorId(@PathVariable Long doctorId) {
        List<Request> requests = requestService.getRequestsByDoctorId(doctorId);
        return ResponseEntity.ok(requests);
    }

    @PatchMapping("/updateRequest/{id}/{status}")
    public ResponseEntity<Request> updateRequestStatus(
            @PathVariable("id") Long requestId, // Ensure the path variable matches
            @PathVariable("status") RequestStatus status) {
        try {
            Request updatedRequest = requestService.updateRequestStatus(requestId, status);
            return ResponseEntity.ok(updatedRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
