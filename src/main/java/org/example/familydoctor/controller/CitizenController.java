package org.example.familydoctor.controller;

import org.example.familydoctor.dto.CitizenRequest;
import org.example.familydoctor.model.Citizen;
import org.example.familydoctor.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

    private final CitizenService citizenService;

    @Autowired
    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @PostMapping
    public ResponseEntity<Citizen> createCitizenWithFamily(@RequestBody CitizenRequest citizenRequest) {
        Citizen citizen = citizenService.createCitizenWithFamily(citizenRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(citizen);
    }
}