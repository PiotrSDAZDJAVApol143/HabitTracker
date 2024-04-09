package com.example.habittracker.utils.mapper;

import com.example.habittracker.dto.ActivityDto;
import com.example.habittracker.dto.ActivityReqDto;
import com.example.habittracker.model.Activity;
import com.example.habittracker.model.Habit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ActivityMapperTest {

    private final ActivityMapper activityMapper = new ActivityMapper();

    @Test
    void toDto() {
        //given
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setActivityName("Test Activity");
        activity.setDateOfActivity(LocalDate.of(2024,4,8));
        activity.setTimeOfActivity(LocalTime.of(18,30));
        Habit habit = new Habit();
        activity.setHabit(habit);
        //when
        ActivityDto dto = activityMapper.toDto(activity);
        //then
        assertEquals(activity.getId(), dto.getId());
        assertEquals(activity.getActivityName(), dto.getActivityName());
        assertEquals(activity.getTimeOfActivity(), dto.getTimeOfActivity());
        assertEquals(activity.getDateOfActivity(), dto.getDateOfActivity());
    }

    @Test
    public void toEntityTest() {
        //given
        ActivityReqDto dto = new ActivityReqDto(1L,"TestActivity",LocalTime.of(10,0),LocalDate.of(2022,1,1));
        dto.setHabitId(1L);
        dto.setActivityName("Test Activity");
        dto.setTimeOfActivity(LocalTime.of(10, 0));
        dto.setDateOfActivity(LocalDate.of(2022, 1, 1));
        Habit habit = new Habit();
        //when
        Activity activity = activityMapper.toEntity(dto, habit);
        //then
        assertEquals(dto.getActivityName(), activity.getActivityName());
        assertEquals(dto.getTimeOfActivity(), activity.getTimeOfActivity());
        assertEquals(dto.getDateOfActivity(), activity.getDateOfActivity());
        assertEquals(habit, activity.getHabit());
    }


    @Test
    void updateFromDto() {
        ActivityReqDto dto = new ActivityReqDto(1L,"TestActivity",LocalTime.of(10,0),LocalDate.of(2022,1,1));
        dto.setHabitId(1L);
        dto.setActivityName("Test Activity");
        dto.setTimeOfActivity(LocalTime.of(10, 0));
        dto.setDateOfActivity(LocalDate.of(2022, 1, 1));

        Activity activity = new Activity();
        activityMapper.updateFromDto(dto, activity);

        assertEquals(dto.getActivityName(), activity.getActivityName());
        assertEquals(dto.getTimeOfActivity(), activity.getTimeOfActivity());
        assertEquals(dto.getDateOfActivity(), activity.getDateOfActivity());
    }

}