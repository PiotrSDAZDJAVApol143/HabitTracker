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
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final HabitRepository habitRepository;
    private final HabitService habitService;
    private final StatisticsRepository statisticsRepository;


    public ActivityDto addActivity(ActivityReqDto request) {
        Habit habit = habitService.getHabitFromId(request.getHabitId()); // Tutaj używamy metody getHabitFromId z HabitService
        Activity activity = activityMapper.toEntity(request, habit);
        activity = activityRepository.save(activity);
        updateHabitProgress(habit);
        updateStatisticsProgress(habit);
        return activityMapper.toDto(activity);
    }

    public ActivityDto updateActivity(Long id, ActivityReqDto activityDto) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
        activityMapper.updateFromDto(activityDto, activity);
        activity = activityRepository.save(activity);
        return activityMapper.toDto(activity);
    }

    public ActivityDto getActivity(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
        return activityMapper.toDto(activity);
    }


    public void deleteActivity(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new EntityNotFoundException("Activity not found");
        }
        Activity activity = activityRepository.findById(id).get();
        Habit habit = activity.getHabit();

        activityRepository.deleteById(id);
        String progress = habitService.calculateProgress(habit);
        habit.setProgress(progress);
        habitRepository.save(habit);
    }

    private Habit getHabitFromId(Long habitId) {
        if (habitId == null) {
            return null;
        }
        return habitRepository.findById(habitId)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found with id: " + habitId));
    }
    private void updateHabitProgress(Habit habit) {
        String progress = habitService.calculateProgress(habit);
        habit.setProgress(progress);
        habitRepository.save(habit);
    }
    private void updateStatisticsProgress(Habit habit) {
        Statistics statistics = habit.getStatistics(); // Pobieramy statystyki z nawyku
        String progress = habitService.calculateProgress(habit); // Obliczamy nowy postęp
        statistics.setProgress(progress); // Aktualizujemy postęp w statystykach
        statisticsRepository.save(statistics); // Zapisujemy zmienione statystyki
    }


}

// public ActivityDto addActivity(ActivityReqDto request) {
//     Habit habit = getHabitFromId(request.getHabitId());
//     Activity activity = activityMapper.toEntity(request, habit);
//     activity = activityRepository.save(activity);
//     habit.getActivities().add(activity);
//     String progress = habitService.calculateProgress(habit);
//     habit.setProgress(progress);
//     habitRepository.save(habit);
//     return activityMapper.toDto(activity);
// }