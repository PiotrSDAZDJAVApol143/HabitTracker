package com.example.habittracker.utils.mapper;

import com.example.habittracker.dto.ActivityDto;
import com.example.habittracker.dto.HabitDto;
import com.example.habittracker.dto.HabitReminderDto;
import com.example.habittracker.dto.HabitReqDto;
import com.example.habittracker.model.Activity;
import com.example.habittracker.model.Habit;

import com.example.habittracker.model.Reminder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HabitMapper {
    private final ActivityMapper activityMapper;
    private final ReminderMapper reminderMapper;

    public HabitDto toDto(Habit habit) {
        HabitDto dto = new HabitDto();
        dto.setId(habit.getId());
        dto.setName(habit.getHabitName());
        dto.setDescription(habit.getDescription());
        dto.setFrequency(habit.getFrequency());
        dto.setFrequencyUnit(habit.getFrequencyUnit());
        dto.setProgress(habit.getProgress());  // TEST
        dto.setHabitActivities(habit.getActivities().stream()
                .map(activityMapper::toDto)
                .collect(Collectors.toList()));
        dto.setHabitReminders(habit.getReminders().stream()
                .map(reminderMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public Habit toEntity(HabitReqDto dto) {
        Habit habit = new Habit();
        habit.setHabitName(dto.getName());
        habit.setDescription(dto.getDescription());
        habit.setFrequency(dto.getFrequency());
        habit.setFrequencyUnit(dto.getFrequencyUnit());
        return habit;
    }

    public void updateHabitFromDto(HabitReqDto habitReqDto, Habit habit){
        habit.setHabitName(habitReqDto.getName());
        habit.setDescription(habitReqDto.getDescription());
        habit.setFrequency(habitReqDto.getFrequency());
        habit.setFrequencyUnit(habitReqDto.getFrequencyUnit());
    }
}
