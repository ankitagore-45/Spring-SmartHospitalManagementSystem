package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Entity
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientName;

    private String doctorName;

    private String medicines;

    private String notes;

    private String date;
   
    private String deliveryStatus = "PENDING";
    
    private String token;
    
    private String patientEmail;
}

   