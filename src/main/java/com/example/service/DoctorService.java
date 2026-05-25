package com.example.service;

import java.util.List;

import com.example.entity.Appointment;
import com.example.entity.Doctor;

public interface DoctorService  {


    Doctor addDoctor(Doctor doctor);

    List<Doctor> getAllDoctors();

    Doctor getDoctorById(Long id);

    Doctor updateDoctor(Long id, Doctor updatedDoctor);

    void deleteDoctor(Long id);
    
    Doctor loginDoctor(String email, String password);
   
}
