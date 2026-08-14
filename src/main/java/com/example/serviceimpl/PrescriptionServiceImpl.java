package com.example.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Prescription;
import com.example.repository.PrescriptionRepository;
import com.example.service.PrescriptionService;
@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<Prescription> searchByName(String keyword) {
        return prescriptionRepository
            .findByPatientNameContainingIgnoreCaseOrTokenContainingIgnoreCase(keyword, keyword);
    }

    public Prescription findByToken(String token) {
        return prescriptionRepository.findByToken(token);
    }

    public Prescription savePrescription(Prescription p) {

        Prescription savedPrescription =
                prescriptionRepository.save(p);

        String token =
                "MED" + (100 + savedPrescription.getId());

        savedPrescription.setToken(token);

        return prescriptionRepository.save(savedPrescription);
    }

	@Override
	public List<Prescription> findByPatientEmail(String patientEmail) {
		return prescriptionRepository.findByPatientEmail(patientEmail) ;
	}

	
}