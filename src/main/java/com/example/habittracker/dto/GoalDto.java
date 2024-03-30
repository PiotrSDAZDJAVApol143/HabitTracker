package com.example.habittracker.dto;

import com.example.habittracker.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GoalDto {
    private Long id;
    private String goalName;
    private String description;
    private Category category;
    private LocalDate startDate;
    private LocalDate endDate;

}
