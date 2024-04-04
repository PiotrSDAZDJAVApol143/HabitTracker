package com.example.habittracker.utils.mapper;

import com.example.habittracker.dto.StatisticsDto;
import com.example.habittracker.dto.StatisticsReqDto;
import com.example.habittracker.model.Goal;
import com.example.habittracker.model.Statistics;
import org.springframework.stereotype.Component;

@Component
public class StatisticsMapper {
    public StatisticsDto toDto(Statistics statistics){
        StatisticsDto dto = new StatisticsDto();
        dto.setId(statistics.getId());
        dto.setGoal(statistics.getGoal());
        dto.setGoalStatus(statistics.getStatus());
        dto.setHabits(statistics.getHabits());
        return dto;
    }

    public Statistics toEntity(StatisticsReqDto dto){
        Statistics statistics = new Statistics();
        if (dto.getGoalId() != null) {
            Goal goal = new Goal();
            goal.setId(dto.getGoalId());
            statistics.setGoal(goal);
        }
        return statistics;
    }

    public Statistics toEntityFromDto(StatisticsDto dto){
        Statistics statistics = new Statistics();
        if (dto.getGoal() != null) {
            Goal goal = new Goal();
            goal.setId(dto.getGoal().getId());
            statistics.setGoal(goal);
        }
        statistics.setStatus(dto.getGoalStatus());
        return statistics;
    }
}
