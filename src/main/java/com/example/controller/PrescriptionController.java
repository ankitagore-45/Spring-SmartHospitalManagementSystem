package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Appointment;
import com.example.entity.Prescription;
import com.example.repository.PrescriptionRepository;
import com.example.service.PrescriptionService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/prescription")
@CrossOrigin("*")
public class PrescriptionController {
 
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionService prescriptionService;
    // add prescription
    @PostMapping("/add")
    public Prescription addPrescription(@RequestBody Prescription prescription) {
        return prescriptionService.savePrescription(prescription);
    }

    // patient prescriptions
    @GetMapping("/myPrescription")
    public List<Prescription> getMyPrescriptions(HttpSession session) {

        String patientName =
                (String) session.getAttribute("name");

        System.out.println("SESSION NAME = " + patientName);

        return prescriptionRepository
                .findByPatientName(patientName);
   }
   
    
//    @GetMapping("/my")
//    public List<Prescription> getMyPrescriptions(HttpSession session) {
//
//        String email =
//                (String) session.getAttribute("email");
//
//        if(email == null) {
//            return List.of();
//        }
//
//        return prescriptionRepository.findByPatientEmail(email);
//    }
    // doctor prescriptions
    @GetMapping("/doctor")
    public List<Prescription> getDoctorPrescriptions(
            HttpSession session) {

        String doctorName =
                (String) session.getAttribute("name");

        System.out.println("DOCTOR NAME = " + doctorName);

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

//    //for search

    
    @GetMapping("/search")
    public List<Prescription> search(@RequestParam String keyword) {
        return prescriptionRepository
            .findByPatientNameContainingIgnoreCaseOrTokenContainingIgnoreCase(keyword, keyword);
    }
    
    @GetMapping("/test-session")
    @ResponseBody
    public String testSession(HttpSession session){

        return "SESSION NAME = " +
                session.getAttribute("name");
    }
    
    @GetMapping("/getbyemail/{patientEmail}")
    public List<Prescription> findByEmail(@PathVariable String patientEmail){
    	return prescriptionService.findByPatientEmail(patientEmail);
    }
    
}