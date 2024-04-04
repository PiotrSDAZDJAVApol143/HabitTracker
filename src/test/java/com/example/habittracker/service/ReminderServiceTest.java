package com.example.habittracker.service;

import com.example.habittracker.dto.HabitReminderDto;
import com.example.habittracker.dto.ReminderReqDto;
import com.example.habittracker.model.Goal;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.Reminder;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.repository.ReminderRepository;
import com.example.habittracker.utils.mapper.ReminderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReminderServiceTest {
    @Mock
    private ReminderRepository reminderRepository;

    @Mock
    private HabitRepository habitRepository;

    @Mock
    private ReminderMapper reminderMapper;

    @InjectMocks
    private ReminderService reminderService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllReminders() {
        // given
        List<Reminder> reminders = new ArrayList<>();
        when(reminderRepository.findAll()).thenReturn(reminders);

        // when
        List<Reminder> result = reminderService.getAllReminders();

        // then
        assertEquals(reminders, result);
        verify(reminderRepository).findAll();
    }


    @Test
    public void addReminderTest() {
        // given
        Long habitId = 1L;
        String reminderText = "Test reminder";
        String reminderTime = "2022-12-31 10:00";
        ReminderReqDto request = new ReminderReqDto(habitId, reminderText, reminderTime);
        Habit habit = new Habit();
        Goal goal = new Goal();
        habit.setGoal(goal);
        Reminder reminder = new Reminder();
        when(habitRepository.findById(any())).thenReturn(Optional.of(habit));
        when(reminderMapper.toEntity(any(), any())).thenReturn(reminder);
        when(reminderRepository.save(any())).thenReturn(reminder);
        HabitReminderDto expectedDto = new HabitReminderDto();
        expectedDto.setMessage(reminderText);
        expectedDto.setReminderTime(LocalDateTime.parse(reminderTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        when(reminderMapper.toDto(any())).thenReturn(expectedDto);

        // when
        HabitReminderDto result = reminderService.addReminder(request);

        // then
        assertNotNull(result);
        assertEquals(expectedDto.getMessage(), result.getMessage());
        assertEquals(expectedDto.getReminderTime(), result.getReminderTime());
    }

    @Test
    void getReminderById() {
        // given
        Long id = 1L;
        Reminder reminder = new Reminder();
        when(reminderRepository.findById(any())).thenReturn(Optional.of(reminder));
        HabitReminderDto expectedDto = new HabitReminderDto();
        when(reminderMapper.toDto(any())).thenReturn(expectedDto);

        // when
        HabitReminderDto result = reminderService.getReminderById(id);

        // then
        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    public void updateReminderByIdTest() {
        // given
        Long id = 1L;
        String reminderText = "Updated reminder";
        String reminderTime = "2022-12-31 11:00";
        ReminderReqDto request = new ReminderReqDto(id, reminderText, reminderTime);
        Reminder reminder = new Reminder();
        when(reminderRepository.findById(any())).thenReturn(Optional.of(reminder));
        Habit habit = new Habit();
        when(habitRepository.findById(any())).thenReturn(Optional.of(habit));
        when(reminderRepository.save(any())).thenReturn(reminder);
        HabitReminderDto expectedDto = new HabitReminderDto();
        expectedDto.setMessage(reminderText);
        expectedDto.setReminderTime(LocalDateTime.parse(reminderTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        when(reminderMapper.toDto(any())).thenReturn(expectedDto);

        // when
        HabitReminderDto result = reminderService.updateReminderById(id, request);

        // then
        assertNotNull(result);
        assertEquals(expectedDto.getMessage(), result.getMessage());
        assertEquals(expectedDto.getReminderTime(), result.getReminderTime());
    }

    @Test
    public void deleteReminderByIdTest() {
        // given
        Long id = 1L;
        when(reminderRepository.existsById(any())).thenReturn(true);

        // when
        reminderService.deleteReminderById(id);

        // then
        verify(reminderRepository, times(1)).deleteById(id);
    }
}