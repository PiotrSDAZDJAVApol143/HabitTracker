package com.example.habittracker.dto;

import com.example.habittracker.model.Goal;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatisticsDto {
    private Long id;
    private Goal goal;
    private Status goalStatus;
    private List<Habit> habits;
}
