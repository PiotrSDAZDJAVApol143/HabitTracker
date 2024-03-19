package com.example.habittracker.utils.mapper;

import com.example.habittracker.dto.ActivityDto;
import com.example.habittracker.dto.HabitReadDto;
import com.example.habittracker.model.Activity;
import com.example.habittracker.model.Habit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class HabitMapper {

    public HabitReadDto toDto (Habit habit){
        HabitReadDto dto = HabitReadDto.builder()
                .name(habit.getHabitName())
                .description(habit.getDescription())
                .frequency(habit.getFrequency())
                .frequencyUnit(habit.getFrequencyUnit())
                .build();
        List<Activity> activities = habit.getActivities();
        if(activities != null){
            List<ActivityDto> actv = activities.stream()
                    .map(this::mapActivityToDto)
                    .collect(Collectors.toList());
            dto.setActivities(actv);
        }
        return dto;
    }
    private ActivityDto mapActivityToDto(Activity activity) {
        ActivityDto dto = new ActivityDto();
        dto.setId(activity.getId());
        dto.setTimestamp(activity.getTimestamp());
        return dto;
    }
}
