package com.example.serviceimpl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Appointment;
import com.example.entity.Doctor;
import com.example.repository.DoctorRepository;
import com.example.service.DoctorService;
import com.example.repository.AppointmentRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    // Add Doctor

    @Override
    public Doctor addDoctor(Doctor doctor) {

        return doctorRepository.save(doctor);
    }


    // Get All Doctors

    @Override
    public List<Doctor> getAllDoctors() {

        return doctorRepository.findAll();
    }


    // Get Doctor By Id

    @Override
    public Doctor getDoctorById(Long id) {

        return doctorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Doctor Not Found"));
    }


    // Update Doctor

    @Override
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {

        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Doctor Not Found"));

        //existingDoctor.setDoctorName(updatedDoctor.);
        existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
        existingDoctor.setExperience(updatedDoctor.getExperience());
      //  existingDoctor.setQualification(updatedDoctor.getQualification());
        existingDoctor.setEmail(updatedDoctor.getEmail());
        existingDoctor.setPhone(updatedDoctor.getPhone());
        //existingDoctor.setAvailableTime(updatedDoctor.getAvailableTime());

        return doctorRepository.save(existingDoctor);
    }


    // Delete Doctor

    @Override
    public void deleteDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Doctor Not Found"));

        doctorRepository.delete(doctor);
    }
    
    @Override
    public Doctor loginDoctor(
            String email,
            String password
    ) {

        return doctorRepository
                .findByEmailAndPassword(
                        email,
                        password
                );

    }
 
}