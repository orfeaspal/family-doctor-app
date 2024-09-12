package org.example.familydoctor.dto;

import org.example.familydoctor.model.Citizen;
import org.example.familydoctor.model.User;

public class CitizenRequest {

    private User user;
    private Citizen citizen;

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }
}
