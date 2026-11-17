package com.bush.skygate.common;

import com.bush.skygate.flight.Flight;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id",
            nullable = false,
            updatable = false,
            unique = true)
    private UUID publicId = UUID.randomUUID();

    @CreationTimestamp
    @Column(name="created_at",
            nullable = false,
            updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at",
            nullable = false)
    private Instant updatedAt;

    public Long getId() {return id;}
    public UUID getPublicId() {return publicId;}
    public Instant getCreatedAt() {return createdAt;}
    public Instant getUpdatedAt() {return updatedAt;}
}
