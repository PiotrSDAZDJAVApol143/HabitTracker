package com.example.habittracker.service;

import com.example.habittracker.dto.HabitDto;
import com.example.habittracker.dto.HabitReqDto;
import com.example.habittracker.model.Goal;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.Statistics;
import com.example.habittracker.repository.GoalRepository;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.utils.mapper.HabitMapper;
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
    private final StatusService statusService;


  public HabitDto addHabit(HabitReqDto request) {
      Habit habit = mapper.toEntity(request); // obiekt nawyku
      Long goalId = request.getGoalId();  // pobieranie id celu z requesta nawyku
      Goal goal = goalRepository.findById(goalId) // na podstawie tego Id szukamy Obiekt celu
              .orElseThrow(() -> new EntityNotFoundException("Goal not found with id: " + goalId));
      habit.setGoal(goal);
      Statistics statistics = createStatisticsForGoal(goal, habit); //automatycznie generujemy nowa statystyke tego nawyku
      habit.setStatistics(statistics); // zapisanie tej statystyki do nawyku
      String progress = calculateProgress(habit); // inicjalizacja metody generującej info o postępach
      habit.setProgress(progress); // zapisanie postępu
      statusService.updateStatus(statistics); // zapisanie statustu dla statystyki nawyku
      Habit savedHabit = repository.save(habit); // zapisujemy nawyk do bazy danych
      return mapper.toDto(savedHabit); //mapujemy nawyk do DTO i zwracamy response;
  }
    private Statistics createStatisticsForGoal(Goal goal, Habit habit) {  // metoda generująca statystykę
        Statistics statistics = new Statistics();
        statistics.setGoal(goal);  // przypisanie odpowiedniego celu do statystyki
        statistics.setGoalName(goal.getGoalName());  // przypisanie odpowiedniej nazwy celu do statystyki
        statistics.setHabitName(habit.getHabitName());  // przypisanie odpowiedniego nawyku do statystyki
       String progress = calculateProgress(habit);
       statistics.setProgress(progress); // przypisanie postępu danego nawyku do statystyki
        statusService.updateStatus(statistics);  // sprawdzenie statusu statystyki
        return statistics;
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
        if (goal == null) {
            throw new IllegalArgumentException("Habit's goal is not set!");
        }
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
    public Habit getHabitFromId(Long habitId) {
        if (habitId == null) {
            throw new IllegalArgumentException("Habit ID cannot be null");
        }
        return repository.findById(habitId)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found with id: " + habitId));
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
