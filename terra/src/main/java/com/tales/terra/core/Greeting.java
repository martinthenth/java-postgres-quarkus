package com.tales.terra.core;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Greeting {
    @Id
    @GeneratedValue
    public Long id;
    public String name;
}
