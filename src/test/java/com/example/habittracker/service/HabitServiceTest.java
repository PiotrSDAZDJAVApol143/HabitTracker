package com.example.habittracker.service;

import com.example.habittracker.dto.HabitDto;
import com.example.habittracker.dto.HabitReqDto;
import com.example.habittracker.model.Activity;
import com.example.habittracker.model.FrequencyUnit;
import com.example.habittracker.model.Goal;
import com.example.habittracker.model.Habit;
import com.example.habittracker.repository.GoalRepository;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.utils.mapper.HabitMapper;
import com.example.habittracker.utils.mapper.StatisticsMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HabitServiceTest {
    private final HabitRepository repository = mock(HabitRepository.class);
    private final HabitMapper mapper = mock(HabitMapper.class);
    private final GoalRepository goalRepository = mock(GoalRepository.class);
    private final StatusService statusService = mock(StatusService.class);
    private final HabitService habitService = new HabitService(repository, mapper, goalRepository, statusService);

    @Test
    public void addHabitTest() {
        // given
        Long goalId = 1L;
        String name = "Test habit";
        String description = "Test description";
        Integer frequency = 3;
        FrequencyUnit frequencyUnit = FrequencyUnit.DAILY;
        List<Activity> activities = new ArrayList<>();
        HabitReqDto request = new HabitReqDto(goalId, name, description, frequency, frequencyUnit, activities);
        Goal goal = new Goal();
        goal.setStartDate(LocalDate.now());
        goal.setEndDate(LocalDate.now().plusDays(30));
        when(goalRepository.findById(any())).thenReturn(Optional.of(goal));
        Habit habit = new Habit();
        habit.setFrequency(frequency);
        habit.setFrequencyUnit(frequencyUnit);
        when(mapper.toEntity(any())).thenReturn(habit);
        when(repository.save(any())).thenReturn(habit);
        HabitDto expectedDto = new HabitDto();
        when(mapper.toDto(any())).thenReturn(expectedDto);

        // when
        HabitDto dto = habitService.addHabit(request);

        // then
        assertEquals(expectedDto, dto);
    }

    @Test
    public void getHabitTest() {
        // given
        Long id = 1L;
        Habit habit = new Habit();
        habit.setId(id);
        habit.setHabitName("Test habit");
        habit.setDescription("Test description");
        habit.setFrequency(3);
        habit.setFrequencyUnit(FrequencyUnit.DAILY);
        habit.setProgress("50%");
        Goal goal = new Goal();
        goal.setStartDate(LocalDate.now());
        goal.setEndDate(LocalDate.now().plusDays(30));
        habit.setGoal(goal);
        when(repository.findById(any())).thenReturn(Optional.of(habit));
        HabitDto expectedDto = new HabitDto();
        when(mapper.toDto(any())).thenReturn(expectedDto);

        // when
        HabitDto dto = habitService.getHabit(id);

        // then
        assertEquals(expectedDto, dto);
    }

    @Test
    public void updateHabitTest() {
        // given
        Long id = 1L;
        Long goalId = 1L;
        String name = "Updated habit";
        String description = "Updated description";
        Integer frequency = 4;
        FrequencyUnit frequencyUnit = FrequencyUnit.WEEKLY;
        List<Activity> activities = new ArrayList<>();
        HabitReqDto request = new HabitReqDto(goalId, name, description, frequency, frequencyUnit, activities);
        Habit habit = new Habit();
        habit.setId(id);
        habit.setHabitName("Test habit");
        habit.setDescription("Test description");
        habit.setFrequency(3);
        habit.setFrequencyUnit(FrequencyUnit.DAILY);
        habit.setProgress("50%");
        when(repository.findById(any())).thenReturn(Optional.of(habit));
        Goal goal = new Goal();
        goal.setStartDate(LocalDate.now());
        goal.setEndDate(LocalDate.now().plusDays(30));
        when(goalRepository.findById(any())).thenReturn(Optional.of(goal));
        when(repository.save(any())).thenReturn(habit);
        HabitDto expectedDto = new HabitDto();
        when(mapper.toDto(any())).thenReturn(expectedDto);

        // when
        HabitDto dto = habitService.updateHabit(id, request);

        // then
        assertEquals(expectedDto, dto);
    }
    @Test
    public void deleteHabitTest() {
        // given
        Long id = 1L;
        when(repository.existsById(any())).thenReturn(true);

        // when
        habitService.deleteHabit(id);

        // then
        verify(repository, times(1)).deleteById(id);
    }
}