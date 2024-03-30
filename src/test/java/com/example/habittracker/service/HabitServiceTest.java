package com.example.habittracker.service;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HabitServiceTest {

    @Test
    void calculateProgress() {
        Habit habit = mock(Habit.class);
        HabitRepository habitRepository = mock(HabitRepository.class);
        HabitMapper habitMapper = mock(HabitMapper.class);
        GoalRepository goalRepository = mock(GoalRepository.class);
        StatisticsService statisticsService = mock(StatisticsService.class);
        StatisticsMapper statisticsMapper = mock(StatisticsMapper.class);

        Goal goal = mock(Goal.class);
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        when(goal.getStartDate()).thenReturn(startDate);
        when(goal.getEndDate()).thenReturn(endDate);

        when(habit.getFrequency()).thenReturn(1);
        when(habit.getFrequencyUnit()).thenReturn(FrequencyUnit.DAILY);
        when(habit.getGoal()).thenReturn(goal);

        HabitService habitService = new HabitService(habitRepository, habitMapper, goalRepository, statisticsService, statisticsMapper);


        String progress = habitService.calculateProgress(habit);

        assertEquals("0 / 10", progress);
    }
}