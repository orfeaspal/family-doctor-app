package org.example.familydoctor.service;

import org.example.familydoctor.model.Citizen;
import org.example.familydoctor.model.Doctor;
import org.example.familydoctor.model.Request;
import org.example.familydoctor.model.RequestStatus;
import org.example.familydoctor.repository.CitizenRepository;
import org.example.familydoctor.repository.DoctorRepository;
import org.example.familydoctor.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final CitizenRepository citizenRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository,
                          CitizenRepository citizenRepository,
                          DoctorRepository doctorRepository) {
        this.requestRepository = requestRepository;
        this.citizenRepository = citizenRepository;
        this.doctorRepository = doctorRepository;
    }

    public Request createRequest(Long citizenId, Long doctorId, RequestStatus status) {
        Optional<Citizen> citizenOptional = citizenRepository.findById(citizenId);
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);

        if (citizenOptional.isEmpty() || doctorOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid citizen or doctor ID");
        }

        Citizen citizen = citizenOptional.get();
        Doctor doctor = doctorOptional.get();

        Request request = new Request();
        request.setCitizen(citizen);
        request.setDoctor(doctor);
        request.setStatus(status);

        // Set the doctor's full name and address
        request.setDoctorFullName(doctor.getFirstName() + " " + doctor.getLastName());
        request.setDoctorAddress(doctor.getAddress());

        // Set the citizen's full name and email
        request.setCitizenFullName(citizen.getFirstName() + " " + citizen.getLastName());
        request.setCitizenEmail(citizen.getEmail());

        return requestRepository.save(request);
    }


    public List<Request> getRequestsByCitizenId(Long citizenId) {
        return requestRepository.findByCitizen_Id(citizenId);
    }

    public List<Request> getRequestsByDoctorId(Long doctorId) {
        return requestRepository.findByDoctor_Id(doctorId);
    }

    public Request updateRequestStatus(Long requestId, RequestStatus status) {
        Optional<Request> requestOptional = requestRepository.findById(requestId);

        if (requestOptional.isEmpty()) {
            throw new IllegalArgumentException("Request not found with ID: " + requestId);
        }

        Request request = requestOptional.get();
        request.setStatus(status);
        return requestRepository.save(request);
    }
}
