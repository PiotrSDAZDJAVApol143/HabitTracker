package com.example.habittracker.utils.mapper;

import com.example.habittracker.dto.HabitReminderDto;
import com.example.habittracker.dto.ReminderReqDto;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.Reminder;
import org.springframework.stereotype.Component;

@Component
public class ReminderMapper {
    public Reminder toEntity(ReminderReqDto request, Habit habit) {
        Reminder reminder = new Reminder();
        reminder.setMessage(request.getMessage());
        reminder.setReminderTime(request.getReminderTime());
        reminder.setHabit(habit);
        return reminder;
    }

    public HabitReminderDto toDto(Reminder reminder) {
        HabitReminderDto regDto = new HabitReminderDto();
        regDto.setMessage(reminder.getMessage());
        regDto.setReminderTime(reminder.getReminderTime());
        return regDto;
    }

    public void updateReminderFromDto(ReminderReqDto request, Reminder reminder) {
        reminder.setMessage(request.getMessage());
        reminder.setReminderTime(request.getReminderTime());
    }
}
