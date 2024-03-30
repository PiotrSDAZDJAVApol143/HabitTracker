package com.example.habittracker.model;

import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Table(name = "FREQUENCY_UNIT")
public enum FrequencyUnit {
    DAILY,
    WEEKLY,
    MONTHLY
}