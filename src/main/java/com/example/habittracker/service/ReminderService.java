package com.example.habittracker.service;

import com.example.habittracker.dto.HabitReminderDto;
import com.example.habittracker.dto.ReminderReqDto;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.Reminder;
import com.example.habittracker.repository.GoalRepository;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.repository.ReminderRepository;
import com.example.habittracker.utils.mapper.ReminderMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;
    private final HabitRepository habitRepository;


    public List<Reminder> getAllReminders() {
        return reminderRepository.findAll();
    }

    public HabitReminderDto addReminder(ReminderReqDto request) {
        Habit habit = getHabitFromId(request.getHabitId());
        Reminder reminder = reminderMapper.toEntity(request, habit);
        reminder = reminderRepository.save(reminder);
        return reminderMapper.toDto(reminder);
    }

    public HabitReminderDto getReminderById(Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reminder not found"));
        return reminderMapper.toDto(reminder);
    }


    public HabitReminderDto updateReminderById(Long id, ReminderReqDto request) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reminder not found"));
        reminder.setHabit(getHabitFromId(request.getHabitId()));
        reminderMapper.updateReminderFromDto(request, reminder);
        reminder = reminderRepository.save(reminder);
        return reminderMapper.toDto(reminder);
    }

    public void deleteReminderById(Long id) {
        if (!reminderRepository.existsById(id)) {
            throw new EntityNotFoundException("Reminder not found");
        }
        reminderRepository.deleteById(id);
    }

    /**
     * This method is used to get a habit by its id.
     * @param habitId This is the id of the habit.
     * @return Habit This returns the fetched habit.
     */
    private Habit getHabitFromId(Long habitId) {
        if (habitId == null) {
            return null;
        }
        return habitRepository.findById(habitId)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found with id: " + habitId));
    }

}
    /*
      @Scheduled(fixedRate = 3600000) // Uruchamia co godzinę
    public void sendReminders() {
        List<Reminder> reminders = reminderRepository.findAll();
        for (Reminder reminder : reminders) {
            if (reminder.getReminderTime().getTime() - reminder.getReminderFrequency() <= System.currentTimeMillis()) {
                // Wyślij przypomnienie do użytkownika
            }
        }
    }
     */
