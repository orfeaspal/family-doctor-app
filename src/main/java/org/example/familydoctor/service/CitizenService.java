package org.example.familydoctor.service;

import jakarta.transaction.Transactional;
import org.example.familydoctor.dto.CitizenRequest;
import org.example.familydoctor.model.Citizen;
import org.example.familydoctor.model.FamilyMember;
import org.example.familydoctor.model.User;
import org.example.familydoctor.repository.CitizenRepository;
import org.example.familydoctor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitizenService {

    private final CitizenRepository citizenRepository;
    private final UserRepository userRepository;

    @Autowired
    public CitizenService(CitizenRepository citizenRepository, UserRepository userRepository) {
        this.citizenRepository = citizenRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Citizen createCitizenWithFamily(CitizenRequest citizenRequest) {
        // Create user
        User user = citizenRequest.getUser();
        userRepository.save(user);

        // Create citizen and associate user
        Citizen citizen = citizenRequest.getCitizen();
        citizen.setUser(user);

        // Set family members
        List<FamilyMember> familyMembers = citizenRequest.getCitizen().getFamilyMembers();
        for (FamilyMember familyMember : familyMembers) {
            familyMember.setCitizen(citizen);
        }
        citizen.setFamilyMembers(familyMembers);

        return citizenRepository.save(citizen);
    }
}