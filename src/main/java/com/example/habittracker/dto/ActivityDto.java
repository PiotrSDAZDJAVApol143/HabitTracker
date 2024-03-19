package com.example.habittracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ActivityDto {
    private Long id;
    private LocalDateTime timestamp;
}
