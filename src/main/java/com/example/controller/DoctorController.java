package com.example.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Appointment;
import com.example.entity.Doctor;
import com.example.repository.AppointmentRepository;
import com.example.service.AppointmentService;
import com.example.service.DoctorService;	

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/doctor")
@CrossOrigin("*")

public class DoctorController {
	@Autowired
	private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentService appointmentService;

    // Add Doctor
    @ResponseBody
    @PostMapping("/add")
    public Doctor addDoctor(@RequestBody Doctor doctor) {

        return doctorService.addDoctor(doctor);
    }
 

    // Get All Doctors
    @ResponseBody
    @GetMapping("/all")
    public List<Doctor> getAllDoctors() {

        return doctorService.getAllDoctors();
    }
    
    @GetMapping("/all-doctors")
    public String allDoctorsPage() {
        return "all-doctors";
    }


    // Get Doctor By Id
    @ResponseBody
    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {

        return doctorService.getDoctorById(id);
    }


    // Update Doctor

    @PutMapping("/update/{id}")
    public Doctor updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor updatedDoctor
    ) {
    	
        return doctorService.updateDoctor(id, updatedDoctor);
    }


    // Delete Doctor

    @DeleteMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return "Doctor Deleted Successfully";
    }
    
    @ResponseBody
    @PostMapping("/login")
    public String loginDoctor(@RequestBody Doctor doctor, HttpSession session) {

        Doctor validDoctor =
                doctorService.loginDoctor(doctor.getEmail(), doctor.getPassword());

        if(validDoctor != null){
            session.setAttribute("doctorEmail", validDoctor.getEmail());
            session.setAttribute("doctorName", validDoctor.getDoctorName());
            return "SUCCESS";
        }

        return "FAIL";
    }
    
    @GetMapping("/doctor-dashboard")
    public String doctorDashboard(HttpSession session) {

        if(session.getAttribute("doctorEmail") == null){
            return "redirect:/doctor-login.html";
        }

        return "doctor-dashboard";
    }
    
    @GetMapping("/my-appointments")
    @ResponseBody
    public List<Appointment> getMyAppointments(HttpSession session) {

        String doctorName = (String) session.getAttribute("doctorName");

        if (doctorName == null) {
            return new ArrayList<>();
        }

        return appointmentService.getAppointmentsByDoctorName(doctorName);
    }
//to show dr name in corner
    @GetMapping("/logged-doctor")
    @ResponseBody
    public String getLoggedDoctor(HttpSession session) {

        String doctorName =
                (String) session.getAttribute("doctorName");

        if(doctorName == null){
            return "Doctor";
        }

        return doctorName;
    }
    
    //to change apointment sts

        // APPROVE or REJECT appointment
    @ResponseBody
    @PutMapping("/appointment/status/{id}")
    public String updateAppointmentStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Appointment appointment =
                appointmentRepository.findById(id)
                .orElseThrow(() ->
                new RuntimeException("Appointment not found"));

        appointment.setStatus(status);

        appointmentRepository.save(appointment);

        return "Appointment " + status;
    }
    
    //to fectch specific dr apoitment and patient 
    @GetMapping("/appointments")
    public String doctorAppointmentsPage() {

        return "doctor-appointments";
    }

    @GetMapping("/patients")
    public String doctorPatientsPage() {

        return "doctor-patients";
    }
    

}