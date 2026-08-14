package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Appointment;

public interface AppointmentRepository
extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorName(String doctorName);
    List<Appointment> findByEmail(String email);
}