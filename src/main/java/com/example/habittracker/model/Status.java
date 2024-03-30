package com.example.habittracker.model;

import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Table(name = "STATUS")
public enum Status {
    IN_PROGRESS,
    PLANNED,
    COMPLETED,
    CANCELLED,
    FAILED
}
