package com.example.habittracker.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class ActivityReqDto {
    private Long habitId;
    private String activityName;
    private LocalTime timeOfActivity;
    private LocalDate dateOfActivity;
}
