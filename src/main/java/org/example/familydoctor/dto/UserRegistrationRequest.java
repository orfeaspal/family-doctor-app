package org.example.familydoctor.dto;

import org.example.familydoctor.model.Role;
import java.util.List;

public class UserRegistrationRequest {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;

    // Citizen or Doctor specific fields
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String specialty; // Optional: Only for doctors

    // Family members for Citizen
    private List<FamilyMemberRequest> familyMembers;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<FamilyMemberRequest> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<FamilyMemberRequest> familyMembers) {
        this.familyMembers = familyMembers;
    }
}
