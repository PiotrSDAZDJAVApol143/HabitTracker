package com.example.habittracker.utils.mapper;

import com.example.habittracker.dto.ActivityDto;
import com.example.habittracker.dto.HabitDto;
import com.example.habittracker.dto.HabitReminderDto;
import com.example.habittracker.dto.HabitReqDto;
import com.example.habittracker.model.Activity;
import com.example.habittracker.model.FrequencyUnit;
import com.example.habittracker.model.Habit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HabitMapperTest {

    private final ActivityMapper activityMapper = mock(ActivityMapper.class);
    private final ReminderMapper reminderMapper = mock(ReminderMapper.class);
    private final HabitMapper habitMapper = new HabitMapper(activityMapper, reminderMapper);

    @Test
    public void toDtoTest() {
        // given
        Habit habit = new Habit();
        habit.setId(1L);
        habit.setHabitName("Test habit");
        habit.setDescription("Test description");
        habit.setFrequency(3);
        habit.setFrequencyUnit(FrequencyUnit.DAILY);
        habit.setProgress("50%");
        when(activityMapper.toDto(any())).thenReturn(new ActivityDto());
        when(reminderMapper.toDto(any())).thenReturn(new HabitReminderDto());

        // when
        HabitDto dto = habitMapper.toDto(habit);

        // then
        assertEquals(habit.getId(), dto.getId());
        assertEquals(habit.getHabitName(), dto.getName());
        assertEquals(habit.getDescription(), dto.getDescription());
        assertEquals(habit.getFrequency(), dto.getFrequency());
        assertEquals(habit.getFrequencyUnit(), dto.getFrequencyUnit());
        assertEquals(habit.getProgress(), dto.getProgress());
    }

    @Test
    public void toEntityTest() {
        // given
        Long goalId = 1L;
        String name = "Test habit";
        String description = "Test description";
        Integer frequency = 3;
        FrequencyUnit frequencyUnit = FrequencyUnit.DAILY;
        List<Activity> activities = new ArrayList<>();
        HabitReqDto dto = new HabitReqDto(goalId, name, description, frequency, frequencyUnit, activities);

        // when
        Habit habit = habitMapper.toEntity(dto);

        // then
        assertEquals(dto.getName(), habit.getHabitName());
        assertEquals(dto.getDescription(), habit.getDescription());
        assertEquals(dto.getFrequency(), habit.getFrequency());
        assertEquals(dto.getFrequencyUnit(), habit.getFrequencyUnit());
    }

    @Test
    public void updateHabitFromDtoTest() {
        // given
        Long goalId = 1L;
        String name = "Updated habit";
        String description = "Updated description";
        Integer frequency = 4;
        FrequencyUnit frequencyUnit = FrequencyUnit.WEEKLY;
        List<Activity> activities = new ArrayList<>();
        HabitReqDto dto = new HabitReqDto(goalId, name, description, frequency, frequencyUnit, activities);
        Habit habit = new Habit();

        // when
        habitMapper.updateHabitFromDto(dto, habit);

        // then
        assertEquals(dto.getName(), habit.getHabitName());
        assertEquals(dto.getDescription(), habit.getDescription());
        assertEquals(dto.getFrequency(), habit.getFrequency());
        assertEquals(dto.getFrequencyUnit(), habit.getFrequencyUnit());
    }
}