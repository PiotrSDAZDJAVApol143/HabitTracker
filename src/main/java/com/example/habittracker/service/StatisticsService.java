package com.example.habittracker.service;

import com.example.habittracker.dto.StatisticsDto;
import com.example.habittracker.dto.StatisticsReqDto;
import com.example.habittracker.model.Goal;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.Statistics;
import com.example.habittracker.model.Status;
import com.example.habittracker.repository.GoalRepository;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.repository.StatisticsRepository;
import com.example.habittracker.utils.mapper.StatisticsMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final StatisticsMapper statisticsMapper;
    private final GoalRepository goalRepository;
    private final HabitRepository habitRepository;

    /**
     * This method is used to add a new statistics.
     *
     * @param request This is a request object which contains the details of the statistics to be added.
     * @return StatisticsDto This returns the added statistics.
     */
    public StatisticsDto addStatistics(StatisticsReqDto request) {
        Statistics statistics = statisticsMapper.toEntity(request);
        statistics.setGoal(getGoalFromId(request.getGoalId()));
        statistics.setGoalName(getGoalFromId(request.getGoalId()).getGoalName());
        // statistics.setProgress(calculateProgress(statistics));
        updateStatus(statistics); // aktualizacja statusu
        statistics = statisticsRepository.save(statistics);
        return statisticsMapper.toDto(statistics);
    }
    public StatisticsDto getStatistics(Long id) {
        Statistics statistics = statisticsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Statistics not found"));
        updateStatus(statistics); // aktualizacja statusu
        StatisticsDto statisticsDto = statisticsMapper.toDto(statistics);
        Goal goal = statistics.getGoal();
        if (goal != null) {
            statisticsDto.setGoal(goal);
        }

        return statisticsDto;
    }


    public StatisticsDto updateStatistics(Long id, StatisticsReqDto request) {
        Statistics statistics = statisticsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Statistics not found"));
        statistics.setGoal(getGoalFromId(request.getGoalId()));
        //statisticsMapper.updateStatisticsFromDto(request, statistics);
        // statistics.setProgress(calculateProgress(statistics));
        updateStatus(statistics); // aktualizacja statusu
        statistics = statisticsRepository.save(statistics);
        return statisticsMapper.toDto(statistics);
    }

    public void deleteStatistics(Long id) {
        if (!statisticsRepository.existsById(id)) {
            throw new EntityNotFoundException("Statistics not found");
        }
        statisticsRepository.deleteById(id);
    }

    private Goal getGoalFromId(Long goalId) {
        if (goalId == null) {
            throw new IllegalArgumentException("Goal ID cannot be null");
        }
        return goalRepository.findById(goalId)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found with id: " + goalId));
    }

    public void updateStatus(Statistics statistics) {
        if (statistics.getGoal() == null) {
            statistics.setStatus(Status.CANCELLED);
        } else if (LocalDate.now().isBefore(statistics.getGoal().getStartDate())) {
            statistics.setStatus(Status.PLANNED);
        } else if (LocalDate.now().isAfter(statistics.getGoal().getEndDate())) {
            statistics.setStatus(Status.COMPLETED);
        } else {
            statistics.setStatus(Status.IN_PROGRESS);
        }
        statisticsRepository.save(statistics);
    }
}


// public void createStatistics(Goal goal) {
//     Statistics statistics = new Statistics();
//     statistics.setGoal(goal);
//     repository.save(statistics);
// }
