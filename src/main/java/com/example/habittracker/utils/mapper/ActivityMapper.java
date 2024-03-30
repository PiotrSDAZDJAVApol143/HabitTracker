package com.example.habittracker.utils.mapper;

import com.example.habittracker.dto.ActivityDto;
import com.example.habittracker.dto.ActivityReqDto;
import com.example.habittracker.model.Activity;
import com.example.habittracker.model.Habit;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {
    public ActivityDto toDto(Activity activity) {
        ActivityDto dto = new ActivityDto();
        dto.setId(activity.getId());
        dto.setActivityName(activity.getActivityName());
        dto.setTimeOfActivity(activity.getTimeOfActivity());
        dto.setDateOfActivity(activity.getDateOfActivity());
        dto.setHabit(activity.getHabit());
        return dto;
    }

    public Activity toEntity(ActivityReqDto dto, Habit habit) {
        Activity activity = new Activity();
        activity.setActivityName(dto.getActivityName());
        activity.setTimeOfActivity(dto.getTimeOfActivity());
        activity.setDateOfActivity(dto.getDateOfActivity());
        activity.setHabit(habit);
        return activity;
    }

    public void updateFromDto(ActivityReqDto activityDto, Activity activity) {
        activity.setActivityName(activityDto.getActivityName());
        activity.setTimeOfActivity(activityDto.getTimeOfActivity());
        activity.setDateOfActivity(activityDto.getDateOfActivity());
    }
}
