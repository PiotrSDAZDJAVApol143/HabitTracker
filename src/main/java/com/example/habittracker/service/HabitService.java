package com.example.habittracker.service;

import com.example.habittracker.dto.HabitDto;
import com.example.habittracker.dto.HabitReqDto;
import com.example.habittracker.dto.StatisticsDto;
import com.example.habittracker.dto.StatisticsReqDto;
import com.example.habittracker.model.Goal;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.Statistics;
import com.example.habittracker.repository.GoalRepository;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.utils.mapper.HabitMapper;
import com.example.habittracker.utils.mapper.StatisticsMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;


@Service
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository repository;
    private final HabitMapper mapper;
    private final GoalRepository goalRepository;
    private final StatisticsService statisticsService;
    private final StatisticsMapper statisticsMapper;


  public HabitDto addHabit(HabitReqDto request) {
      // Tworzymy nowy obiekt nawyku
      Habit habit = mapper.toEntity(request);

      // Pobieramy ID celu przypisanego do nawyku
      Long goalId = request.getGoalId();

      // Pobieramy cel na podstawie jego ID
      Goal goal = goalRepository.findById(goalId)
              .orElseThrow(() -> new EntityNotFoundException("Goal not found with id: " + goalId));

      // Tworzymy nową statystykę dla tego celu
      Statistics statistics = createStatisticsForGoal(goal);

      // Przypisujemy statystykę do nawyku
      habit.setStatistics(statistics);
      habit.setGoal(goal);
      String progress = calculateProgress(habit);
      habit.setProgress(progress);
      // Zapisujemy nawyk w bazie danych
      Habit savedHabit = repository.save(habit);

      // Mapujemy zapisany nawyk do obiektu DTO i zwracamy
      return mapper.toDto(savedHabit);
  }
    private Statistics createStatisticsForGoal(Goal goal) {
        Statistics statistics = new Statistics();
        statistics.setGoal(goal);
        statistics.setGoalName(goal.getGoalName());
        return statistics;
    }

    private Goal getGoalFromId(Long goalId) {
        if (goalId == null) {
            throw new IllegalArgumentException("Goal ID cannot be null");
        }
        return goalRepository.findById(goalId)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found with id: " + goalId));
    }

    public HabitDto getHabit(Long id) {
        Habit habit = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found!"));
        String progress = calculateProgress(habit);
        habit.setProgress(progress);
        return mapper.toDto(habit);
    }

    public HabitDto updateHabit(Long id, HabitReqDto request) {
        Habit habit = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found!"));
        habit.setGoal(convertGoalFromId(request.getGoalId()));
        mapper.updateHabitFromDto(request, habit);
        habit = repository.save(habit);
        habit.setProgress(calculateProgress(habit));
        return mapper.toDto(habit);
    }

    public void deleteHabit(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Habit not found!");
        }
        repository.deleteById(id);
    }

    private Goal convertGoalFromId(Long goalId) {
        if (goalId == null) {
            return null;
        }
        return goalRepository.findById(goalId)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found with id: " + goalId));
    }

    public String calculateProgress(Habit habit) {
        Goal goal = habit.getGoal();
        Long totalDays = ChronoUnit.DAYS.between(goal.getStartDate(), goal.getEndDate());
        Long totalActivities;
        switch (habit.getFrequencyUnit()) {
            case DAILY -> totalActivities = totalDays * habit.getFrequency() +1;
            case WEEKLY -> totalActivities = (totalDays / 7) * habit.getFrequency();
            case MONTHLY -> totalActivities = (totalDays / 30) * habit.getFrequency();
            default -> throw new IllegalArgumentException("Use only frequencyUnit (DAILY/WEEKLY/MONTHLY");
        }
        int currentActivities = habit.getActivities().size();
        return currentActivities + " / " + totalActivities;

    }
}

//public Habit updateHabit(Long id, HabitReqDto request) {
//    Habit habit = habitRepository.findById(id)
//            .orElseThrow(()-> new EntityNotFoundException("Habit not found"));
//    if(request.getName() !=null) {
//        habit.setHabitName(request.getName());
//    }
//    if(request.getDescription() !=null){
//        habit.setDescription(request.getDescription());
//    }
//    if(request.getFrequency() !=null){
//        habit.setFrequency(request.getFrequency());
//    }
//    if(request.getFrequencyUnit() !=null){
//        habit.setFrequencyUnit(request.getFrequencyUnit());
//    }
//    if(request.getActivities() != null){
//        habit.getActivities().addAll(request.getActivities());
//    }
//    habitRepository.save(habit);
//    return habit;

//}


// public HabitDto addHabit(HabitReqDto request) {
//     Habit habit = mapper.toEntity(request);
//     habit.setGoal(getGoalFromId(request.getGoalId()));
//     habit = repository.save(habit);

//     StatisticsReqDto statisticsReqDto = new StatisticsReqDto(habit.getGoal().getId());
//     StatisticsDto statisticsDto = statisticsService.addStatistics(statisticsReqDto);
//     habit.setStatistics(statisticsMapper.toEntityFromDto(statisticsDto));

//     return mapper.toDto(habit);
// }
