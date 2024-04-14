package com.example.habittracker.service;

import com.example.habittracker.dto.HabitReminderDto;
import com.example.habittracker.dto.ReminderReqDto;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.Reminder;
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

    /**
     * Retrieves all reminders.
     * @return A list of all reminders.
     */
    public List<Reminder> getAllReminders() {
        return reminderRepository.findAll();
    }

    /**
     * Adds a new reminder.
     * @param request DTO object with reminder data.
     * @return DTO of the added reminder.
     */
    public HabitReminderDto addReminder(ReminderReqDto request) {
        Habit habit = getHabitFromId(request.getHabitId());
        Reminder reminder = reminderMapper.toEntity(request, habit);
        Long goalId = habit.getGoal().getId();
        reminder.setGoalId(goalId);
        reminder = reminderRepository.save(reminder);
        return reminderMapper.toDto(reminder);
    }
    /**
     * Retrieves a reminder by its ID.
     * @param id  of the reminder.
     * @return DTO of the reminder.
     * @throws EntityNotFoundException if id is not found.
     */
    public HabitReminderDto getReminderById(Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reminder not found"));
        return reminderMapper.toDto(reminder);
    }

    /**
     * Updates a reminder by its ID.
     * @param id ID of the reminder.
     * @throws EntityNotFoundException if id is not found in reminderRepository.
     * @param request DTO object with updated reminder data.
     * @return DTO of the updated reminder.
     */
    public HabitReminderDto updateReminderById(Long id, ReminderReqDto request) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reminder not found"));
        reminder.setHabit(getHabitFromId(request.getHabitId()));
        reminderMapper.updateReminderFromDto(request, reminder);
        reminder = reminderRepository.save(reminder);
        return reminderMapper.toDto(reminder);
    }
    /**
     * This method deletes a reminder by its ID from the database.
     * @param id ID of the reminder.
     * @throws EntityNotFoundException if id is not found in reminderRepository.
     */
    public void deleteReminderById(Long id) {
        if (!reminderRepository.existsById(id)) {
            throw new EntityNotFoundException("Reminder not found");
        }
        reminderRepository.deleteById(id);
    }

    /**
     * This is a helper method used by the addReminder method to get a habit by its id.
     * @param habitId This is the id of the habit.
     * @return Habit This returns the fetched habit.
     */
    private Habit getHabitFromId(Long habitId) {
        if (habitId == null) {
            throw new IllegalArgumentException("Habit ID cannot be null");
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
