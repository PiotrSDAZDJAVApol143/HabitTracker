package com.example.habittracker.utils.mapper;

import com.example.habittracker.dto.HabitReminderDto;
import com.example.habittracker.dto.ReminderReqDto;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.Reminder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ReminderMapperTest {
    private ReminderMapper reminderMapper = new ReminderMapper();

    @Test
    public void toEntityTest() {
        // given
        Long habitId = 1L;
        String reminderText = "Test reminder";
        String reminderTime = "2022-12-31 10:00";
        ReminderReqDto request = new ReminderReqDto(habitId, reminderText, reminderTime);
        Habit habit = new Habit();

        // when
        Reminder reminder = reminderMapper.toEntity(request, habit);

        // then
        assertEquals(request.getMessage(), reminder.getMessage());
        assertEquals(LocalDateTime.parse(request.getReminderTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), reminder.getReminderTime());
        assertEquals(habit, reminder.getHabit());
    }

    @Test
    public void toDtoTest() {
        // given
        Reminder reminder = new Reminder();
        reminder.setMessage("Test reminder");
        reminder.setReminderTime(LocalDateTime.parse("2022-12-31 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        // when
        HabitReminderDto dto = reminderMapper.toDto(reminder);

        // then
        assertEquals(reminder.getMessage(), dto.getMessage());
        assertEquals(reminder.getReminderTime(), dto.getReminderTime());
    }

    @Test
    public void updateReminderFromDtoTest() {
        // given
        Long habitId = 1L;
        String reminderText = "Updated reminder";
        String reminderTime = "2022-12-31 11:00";
        ReminderReqDto request = new ReminderReqDto(habitId, reminderText, reminderTime);
        Reminder reminder = new Reminder();

        // when
        reminderMapper.updateReminderFromDto(request, reminder);

        // then
        assertEquals(request.getMessage(), reminder.getMessage());
        assertEquals(LocalDateTime.parse(request.getReminderTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), reminder.getReminderTime());
    }
}