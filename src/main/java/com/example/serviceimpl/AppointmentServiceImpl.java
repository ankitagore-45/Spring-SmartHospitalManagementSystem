package com.example.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Appointment;
import com.example.repository.AppointmentRepository;
import com.example.service.AppointmentService;

@Service
public class AppointmentServiceImpl
implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment bookAppointment(Appointment appointment) {

        appointment.setStatus("Booked");

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {

        return appointmentRepository.findAll();
    }

    @Override
    public Appointment updateAppointment(
            Long id,
            Appointment updatedAppointment) {

        Appointment appointment =
                appointmentRepository.findById(id)
                .orElseThrow(() ->
                new RuntimeException("Appointment Not Found"));

        appointment.setFullName(updatedAppointment.getFullName());
        appointment.setEmail(updatedAppointment.getEmail());
        appointment.setPhone(updatedAppointment.getPhone());
        appointment.setGender(updatedAppointment.getGender());
        appointment.setDepartment(updatedAppointment.getDepartment());
        appointment.setDoctorName(updatedAppointment.getDoctorName());
        appointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        appointment.setAppointmentTime(updatedAppointment.getAppointmentTime());
        appointment.setSymptoms(updatedAppointment.getSymptoms());

        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long id) {

        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorName(String doctorName) {

        return appointmentRepository.findByDoctorName(doctorName);
    }
    
    @Override
    public List<Appointment> getAppointmentsByEmail(String email) {
        return appointmentRepository.findByEmail(email);
    }
}