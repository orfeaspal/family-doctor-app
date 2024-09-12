package org.example.familydoctor.repository;

import org.example.familydoctor.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    // Find requests by the ID of the associated Citizen
    List<Request> findByCitizen_Id(Long citizenId);

    // Find requests by the ID of the associated Doctor
    List<Request> findByDoctor_Id(Long doctorId);
}
