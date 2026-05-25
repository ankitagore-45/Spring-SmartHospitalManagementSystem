package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Appointment;
import com.example.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
	Doctor findByEmailAndPassword(String email, String password);
}
