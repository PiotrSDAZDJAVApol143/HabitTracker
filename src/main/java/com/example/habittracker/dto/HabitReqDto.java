package com.example.habittracker.dto;

import com.example.habittracker.model.Activity;
import com.example.habittracker.model.FrequencyUnit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HabitReqDto {

    private String name;
    private String description;
    private Integer frequency;
    private FrequencyUnit frequencyUnit;
    private List<Activity> activities;
}
