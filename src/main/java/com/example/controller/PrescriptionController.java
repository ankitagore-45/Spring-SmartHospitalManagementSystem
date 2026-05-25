package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Prescription;
import com.example.repository.PrescriptionRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/prescription")
@CrossOrigin("*")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    // add prescription
    @PostMapping("/add")
    public Prescription addPrescription(@RequestBody Prescription prescription) {

        return prescriptionRepository.save(prescription);
    }

    // patient prescriptions
    @ResponseBody
    @GetMapping("/my")
    public List<Prescription> getMyPrescriptions(
            HttpSession session) {

        String patientName =
                (String) session.getAttribute("name");

        System.out.println("SESSION NAME = " + patientName);

        return prescriptionRepository
                .findByPatientName(patientName);
    }

    // doctor prescriptions
    @GetMapping("/doctor")
    public List<Prescription> getDoctorPrescriptions(
            HttpSession session) {

        String doctorName =
                (String) session.getAttribute("doctorName");

        return prescriptionRepository
                .findByDoctorName(doctorName);
    }
    
    //to get all prescription
    @ResponseBody
    @GetMapping("/all")
    public List<Prescription> getAllPrescriptions() {

        return prescriptionRepository.findAll();
    }
    
    @ResponseBody
    @PutMapping("/dispense/{id}")
    public String dispenseMedicine(@PathVariable Long id) {

        Prescription prescription =
                prescriptionRepository.findById(id)
                .orElseThrow(() ->
                new RuntimeException("Prescription not found"));

        prescription.setDeliveryStatus("DISPENSED");

        prescriptionRepository.save(prescription);

        return "Medicine Dispensed Successfully";
    }
}