package com.example.habittracker.dto;


import com.example.habittracker.model.FrequencyUnit;
import com.example.habittracker.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class HabitDto {
    private Long id;
    private String name;
    private String description;
    private Integer frequency;
    private FrequencyUnit frequencyUnit;
    private String progress;
    private Status status;
    private List<ActivityDto> habitActivities;
    private List<HabitReminderDto> habitReminders;

}
