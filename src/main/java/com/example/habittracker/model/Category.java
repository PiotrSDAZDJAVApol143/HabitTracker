package com.example.habittracker.model;

import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Table(name = "CATEGORY")
public enum Category {
    HEALTH,
    EDUCATION,
    FINANCE
}
