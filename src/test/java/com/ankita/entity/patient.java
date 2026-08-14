package com.ankita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class patient {
@Id
@GeneratedValue(startergy =  GenerationType.IDENTITY)
private long id;
}
