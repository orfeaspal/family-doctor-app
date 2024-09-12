package org.example.familydoctor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore // Ignore the full Citizen object in JSON response
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    @ManyToOne
    @JsonIgnore // Ignore the full Doctor object in JSON response
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private String doctorFullName; // New field for doctor's full name
    private String doctorAddress; // New field for doctor's address

    private String citizenFullName; // New field for citizen's full name
    private String citizenEmail; // New field for citizen's email

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
    }

    public String getDoctorAddress() {
        return doctorAddress;
    }

    public void setDoctorAddress(String doctorAddress) {
        this.doctorAddress = doctorAddress;
    }

    public String getCitizenFullName() {
        return citizenFullName;
    }

    public void setCitizenFullName(String citizenFullName) {
        this.citizenFullName = citizenFullName;
    }

    public String getCitizenEmail() {
        return citizenEmail;
    }

    public void setCitizenEmail(String citizenEmail) {
        this.citizenEmail = citizenEmail;
    }

    // Custom getter for the Doctor ID
    @JsonProperty("doctorId")
    public Long getDoctorId() {
        return doctor != null ? doctor.getId() : null;
    }

    // Custom getter for the Citizen ID
    @JsonProperty("citizenId")
    public Long getCitizenId() {
        return citizen != null ? citizen.getId() : null;
    }
}
