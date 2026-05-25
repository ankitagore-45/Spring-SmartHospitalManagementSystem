package com.example.service;

import java.util.List;

import com.example.entity.Appointment;

public interface AppointmentService {

    public Appointment bookAppointment(Appointment appointment);

    public List<Appointment> getAllAppointments();

    public Appointment updateAppointment(Long id,
                                         Appointment appointment);

    public void deleteAppointment(Long id);

    public List<Appointment>getAppointmentsByDoctorName(String doctorName);

    List<Appointment> getAppointmentsByEmail(String email);

//	List<Appointment> getfindByDoctorName(String doctorName);

//	List<Appointment> getAppointmentsByDoctor(String doctorEmail);

}