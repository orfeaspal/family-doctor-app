package org.example.familydoctor.dto;

import org.example.familydoctor.model.Doctor;
import org.example.familydoctor.model.User;

public class DoctorRequest {

    private User user;
    private Doctor doctor;

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}