package com.example.habittracker.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class HabitReminderDto {
    private String message;
    private LocalDateTime reminderTime;
}
