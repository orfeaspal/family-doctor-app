package org.example.familydoctor.service;

import jakarta.transaction.Transactional;
import org.example.familydoctor.dto.DoctorRequest;
import org.example.familydoctor.model.Doctor;
import org.example.familydoctor.model.User;
import org.example.familydoctor.repository.DoctorRepository;
import org.example.familydoctor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

//    @Transactional
//    public Doctor createDoctor(DoctorRequest doctorRequest) {
//        // Validate doctor request
//        if (doctorRequest == null || doctorRequest.getUser() == null || doctorRequest.getDoctor() == null) {
//            throw new IllegalArgumentException("Doctor request and its components must not be null.");
//        }
//
//        // Create and save user
//        User user = doctorRequest.getUser();
//        // Check if the user already exists based on unique fields like email
//        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
//        if (existingUser.isPresent()) {
//            throw new IllegalArgumentException("A user with the same email already exists.");
//        }
//        user = userRepository.save(user);
//
//        // Create doctor and associate with user
//        Doctor doctor = doctorRequest.getDoctor();
//        doctor.setUser(user);
//
//        // Save doctor
//        return doctorRepository.save(doctor);
//    }

    public Optional<Doctor> getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor updateDoctor(Long doctorId, DoctorRequest doctorRequest) {
        if (doctorRequest == null || doctorRequest.getDoctor() == null) {
            throw new IllegalArgumentException("Doctor request and doctor details must not be null.");
        }

        return doctorRepository.findById(doctorId)
                .map(existingDoctor -> {
                    Doctor updatedDoctor = doctorRequest.getDoctor();
                    existingDoctor.setFirstName(updatedDoctor.getFirstName());
                    existingDoctor.setLastName(updatedDoctor.getLastName());
                    existingDoctor.setSpecialty(updatedDoctor.getSpecialty());
                    existingDoctor.setAddress(updatedDoctor.getAddress());
                    existingDoctor.setPhone(updatedDoctor.getPhone());
                    existingDoctor.setEmail(updatedDoctor.getEmail());

                    return doctorRepository.save(existingDoctor);
                })
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + doctorId));
    }

    public void deleteDoctor(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new IllegalArgumentException("Doctor not found with id: " + doctorId);
        }
        doctorRepository.deleteById(doctorId);
    }
}
