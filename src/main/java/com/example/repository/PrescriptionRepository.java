package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Prescription;

public interface PrescriptionRepository
extends JpaRepository<Prescription, Long>{

    List<Prescription> findByPatientName(String patientName);

    List<Prescription> findByDoctorName(String doctorName);

    Prescription findByToken(String token);
    
    List<Prescription>   findByPatientNameContainingIgnoreCaseOrTokenContainingIgnoreCase( String name, String token );
}