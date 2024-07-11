package com.tales.terra.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.f4b6a3.uuid.UuidCreator;

@Entity
@Table(name = "users")
public class User {
    @Id
    public UUID id = UuidCreator.getTimeOrderedEpoch();
    @Column(columnDefinition = "TEXT", nullable = false)
    public String firstName;
    @Column(columnDefinition = "TEXT", nullable = false)
    public String lastName;
    @Column(columnDefinition = "TEXT", nullable = false)
    public String emailAddress;
    @CreationTimestamp
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    public LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    public LocalDateTime updatedAt;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    public LocalDateTime deletedAt;
}
