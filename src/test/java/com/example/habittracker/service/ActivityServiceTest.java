package com.example.habittracker.service;

import com.example.habittracker.dto.ActivityDto;
import com.example.habittracker.dto.ActivityReqDto;
import com.example.habittracker.model.Activity;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.Statistics;
import com.example.habittracker.repository.ActivityRepository;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.repository.StatisticsRepository;
import com.example.habittracker.utils.mapper.ActivityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ActivityServiceTest {
    @InjectMocks
    private ActivityService activityService;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private ActivityMapper activityMapper;
    @Mock
    private HabitService habitService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private static ActivityReqDto getActivityReqDto() {
        Long habitId = 1L;
        String activityName = "testActivity";
        LocalTime timeOfActivity = LocalTime.of(15, 30);
        LocalDate dateOfActivity = LocalDate.of(2024, 5, 2);

        ActivityReqDto request = new ActivityReqDto(habitId, activityName, timeOfActivity, dateOfActivity);
        request.setHabitId(habitId);
        request.setActivityName(activityName);
        request.setTimeOfActivity(timeOfActivity);
        request.setDateOfActivity(dateOfActivity);

        return request;
    }

    @Test
    void addActivity() {
        //given
        ActivityReqDto request = getActivityReqDto();
        Habit habit = new Habit();
        Activity activity = new Activity();
        Statistics statistics = new Statistics();
        habit.setStatistics(statistics);
        ActivityDto expected = new ActivityDto();
        //when
        when(habitService.getHabitFromId(anyLong())).thenReturn(habit);
        when(activityMapper.toEntity(any(ActivityReqDto.class), any(Habit.class))).thenReturn(activity);
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);
        when(activityMapper.toDto(any(Activity.class))).thenReturn(expected);
        //then
        ActivityDto result = activityService.addActivity(request);

        assertEquals(expected, result);
        verify(habitService).getHabitFromId(request.getHabitId());
        verify(activityMapper).toEntity(request, habit);
        verify(activityRepository).save(activity);
        verify(activityMapper).toDto(activity);
    }

    @Test
    void updateActivity() {
        //given
        Long id = 1L;
        ActivityReqDto request = getActivityReqDto();
        Activity activity = new Activity();
        ActivityDto expected = new ActivityDto();
        //when
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(activity));
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);
        when(activityMapper.toDto(any(Activity.class))).thenReturn(expected);
        //then
        ActivityDto result = activityService.updateActivity(id, request);

        assertEquals(expected, result);
        verify(activityRepository).findById(id);
        verify(activityMapper).updateFromDto(request, activity);
        verify(activityRepository).save(activity);
        verify(activityMapper).toDto(activity);
    }

    @Test
    void getActivity() {
        //given
        Long id = 1L;
        Activity activity = new Activity();
        ActivityDto expected = new ActivityDto();
        //when
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(activity));
        when(activityMapper.toDto(any(Activity.class))).thenReturn(expected);
        //then
        ActivityDto result = activityService.getActivity(id);

        assertEquals(expected, result);
        verify(activityRepository).findById(id);
        verify(activityMapper).toDto(activity);
    }

    @Test
    void deleteActivity() {
        //given
        Long id = 1L;
        Activity activity = new Activity();
        Habit habit = new Habit();
        activity.setHabit(habit);
        //when
        when(activityRepository.existsById(anyLong())).thenReturn(true);
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(activity));
        //then
        activityService.deleteActivity(id);

        verify(activityRepository).existsById(id);
        verify(activityRepository).findById(id);
        verify(activityRepository).deleteById(id);
    }
}