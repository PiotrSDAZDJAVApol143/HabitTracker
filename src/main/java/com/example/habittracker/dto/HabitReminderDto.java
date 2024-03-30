package com.example.habittracker.dto;

import com.example.habittracker.model.FrequencyUnit;
import com.example.habittracker.model.Habit;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class HabitReminderDto {
    private String message;
    private LocalDateTime reminderTime;
}
