package com.example.habittracker.dto;

import com.example.habittracker.model.Activity;
import com.example.habittracker.model.FrequencyUnit;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class HabitReqDto {
    private Long goalId;
    private String name;
    private String description;
    private Integer frequency;
    private FrequencyUnit frequencyUnit;
    private List<Activity> activities;
}
