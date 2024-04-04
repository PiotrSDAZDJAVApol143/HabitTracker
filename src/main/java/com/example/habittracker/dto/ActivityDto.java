package com.example.habittracker.dto;

import com.example.habittracker.model.Habit;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class ActivityDto {
    private Long id;
    private String activityName;
    private LocalTime timeOfActivity;
    private LocalDate dateOfActivity;
    //private Habit habit;
}
