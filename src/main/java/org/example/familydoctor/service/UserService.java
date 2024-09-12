package org.example.familydoctor.service;

import org.example.familydoctor.dto.DoctorDTO;
import org.example.familydoctor.dto.FamilyMemberRequest;
import org.example.familydoctor.dto.UserRegistrationRequest;
import org.example.familydoctor.model.Citizen;
import org.example.familydoctor.model.Doctor;
import org.example.familydoctor.model.FamilyMember;
import org.example.familydoctor.model.Role;
import org.example.familydoctor.model.User;
import org.example.familydoctor.repository.CitizenRepository;
import org.example.familydoctor.repository.DoctorRepository;
import org.example.familydoctor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CitizenRepository citizenRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       CitizenRepository citizenRepository,
                       DoctorRepository doctorRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.citizenRepository = citizenRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserRegistrationRequest> getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(this::convertToDTO);
    }

    @Transactional
    public User registerUser(UserRegistrationRequest request) {
        // Create User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        // Save User
        user = userRepository.save(user);

        // Depending on role, create a Citizen or Doctor
        if (request.getRole() == Role.CITIZEN) {
            Citizen citizen = new Citizen();
            citizen.setFirstName(request.getFirstName());
            citizen.setLastName(request.getLastName());
            citizen.setAddress(request.getAddress());
            citizen.setPhone(request.getPhone());
            citizen.setEmail(request.getEmail());
            citizen.setUser(user);

            // Check for family members
            if (request.getFamilyMembers() != null && !request.getFamilyMembers().isEmpty()) {
                List<FamilyMember> familyMembers = request.getFamilyMembers().stream()
                        .map(memberRequest -> {
                            FamilyMember member = new FamilyMember();
                            member.setFirstName(memberRequest.getFirstName());
                            member.setLastName(memberRequest.getLastName());
                            member.setRelationship(memberRequest.getRelationship());
                            member.setCitizen(citizen); // Set citizen for the family member
                            return member;
                        })
                        .collect(Collectors.toList());

                citizen.setFamilyMembers(familyMembers);
            } else {
                // Initialize an empty list to avoid nulls in database
                citizen.setFamilyMembers(List.of());
            }

            citizenRepository.save(citizen);
        } else if (request.getRole() == Role.DOCTOR) {
            Doctor doctor = new Doctor();
            doctor.setFirstName(request.getFirstName());
            doctor.setLastName(request.getLastName());
            doctor.setAddress(request.getAddress());
            doctor.setPhone(request.getPhone());
            doctor.setSpecialty(request.getSpecialty());
            doctor.setEmail(request.getEmail());
            doctor.setUser(user);
            doctorRepository.save(doctor);
        }

        return user;
    }

    @Transactional
    public Optional<UserRegistrationRequest> updateUser(Long id, UserRegistrationRequest request) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return Optional.empty(); // User not found
        }

        User user = userOptional.get();
        user.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setEmail(request.getEmail());

        // Update role-specific details
        if (user.getRole() == Role.CITIZEN && user.getCitizen() != null) {
            Citizen citizen = user.getCitizen();
            citizen.setFirstName(request.getFirstName());
            citizen.setLastName(request.getLastName());
            citizen.setAddress(request.getAddress());
            citizen.setPhone(request.getPhone());
            citizenRepository.save(citizen);
        } else if (user.getRole() == Role.DOCTOR && user.getDoctor() != null) {
            Doctor doctor = user.getDoctor();
            doctor.setFirstName(request.getFirstName());
            doctor.setLastName(request.getLastName());
            doctor.setAddress(request.getAddress());
            doctor.setPhone(request.getPhone());
            doctor.setSpecialty(request.getSpecialty());
            doctorRepository.save(doctor);
        }

        user = userRepository.save(user); // Save updated user details

        return Optional.of(convertToDTO(user));
    }

    @Transactional
    public boolean deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return false; // User not found
        }

        User user = userOptional.get();

        // Handle cascading deletion if necessary (should be automatic with proper JPA config)
        userRepository.delete(user);
        return true;
    }

    public List<UserRegistrationRequest> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doctor -> new DoctorDTO(
                        doctor.getId(),
                        doctor.getFirstName(),
                        doctor.getLastName(),
                        doctor.getSpecialty(),
                        doctor.getAddress(),
                        doctor.getPhone(),
                        doctor.getEmail()
                ))
                .collect(Collectors.toList());
    }

    private UserRegistrationRequest convertToDTO(User user) {
        UserRegistrationRequest userDTO = new UserRegistrationRequest();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());

        // Convert based on role
        if (user.getRole() == Role.CITIZEN && user.getCitizen() != null) {
            Citizen citizen = user.getCitizen();
            userDTO.setFirstName(citizen.getFirstName());
            userDTO.setLastName(citizen.getLastName());
            userDTO.setPhone(citizen.getPhone());
            userDTO.setAddress(citizen.getAddress());
            userDTO.setFamilyMembers(
                    citizen.getFamilyMembers().stream()
                            .map(familyMember -> {
                                FamilyMemberRequest fmRequest = new FamilyMemberRequest();
                                fmRequest.setFirstName(familyMember.getFirstName());
                                fmRequest.setLastName(familyMember.getLastName());
                                fmRequest.setRelationship(familyMember.getRelationship());
                                return fmRequest;
                            })
                            .collect(Collectors.toList())
            );
        } else if (user.getRole() == Role.DOCTOR && user.getDoctor() != null) {
            Doctor doctor = user.getDoctor();
            userDTO.setFirstName(doctor.getFirstName());
            userDTO.setLastName(doctor.getLastName());
            userDTO.setSpecialty(doctor.getSpecialty());
            userDTO.setAddress(doctor.getAddress());
            userDTO.setPhone(doctor.getPhone());
        }

        return userDTO;
    }

    private UserRegistrationRequest convertDoctorToDTO(Doctor doctor) {
        UserRegistrationRequest userDTO = new UserRegistrationRequest();
        User user = doctor.getUser(); // Get the associated user for username and email
        userDTO.setId(doctor.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(Role.DOCTOR);
        userDTO.setFirstName(doctor.getFirstName());
        userDTO.setLastName(doctor.getLastName());
        userDTO.setAddress(doctor.getAddress());
        userDTO.setPhone(doctor.getPhone());
        userDTO.setSpecialty(doctor.getSpecialty());
        return userDTO;
    }
}
