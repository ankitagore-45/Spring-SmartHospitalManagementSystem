package com.example.service;

import java.util.List;

import com.example.entity.Prescription;

public interface PrescriptionService {
    List<Prescription> searchByName(String keyword);
    Prescription findByToken(String token);
    Prescription savePrescription(Prescription p);
}
