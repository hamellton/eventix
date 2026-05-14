package com.eventix.eventix.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @jakarta.persistence.GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

