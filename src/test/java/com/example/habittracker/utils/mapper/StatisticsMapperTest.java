package com.example.habittracker.utils.mapper;

import com.example.habittracker.dto.StatisticsDto;
import com.example.habittracker.dto.StatisticsReqDto;
import com.example.habittracker.model.Goal;
import com.example.habittracker.model.Statistics;
import org.junit.jupiter.api.Test;

import static com.example.habittracker.model.Status.IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.*;

public class StatisticsMapperTest {

    private final StatisticsMapper statisticsMapper = new StatisticsMapper();

    @Test
    public void toDtoTest() {
        Statistics statistics = new Statistics();
        statistics.setId(1L);
        Goal goal = new Goal();
        statistics.setGoal(goal);
        statistics.setStatus(IN_PROGRESS);

        StatisticsDto dto = statisticsMapper.toDto(statistics);

        assertEquals(statistics.getId(), dto.getId());
        assertEquals(statistics.getGoal(), dto.getGoal());
        assertEquals(statistics.getStatus(), dto.getGoalStatus());
    }

    @Test
    public void toEntityTest() {
        StatisticsReqDto dto = new StatisticsReqDto();
        dto.setGoalId(1L);

        Statistics statistics = statisticsMapper.toEntity(dto);

        assertEquals(dto.getGoalId(), statistics.getGoal().getId());
    }

    @Test
    public void toEntityFromDtoTest() {
        StatisticsDto dto = new StatisticsDto();
        Goal goal = new Goal();
        goal.setId(1L);
        dto.setGoal(goal);
        dto.setGoalStatus(IN_PROGRESS);

        Statistics statistics = statisticsMapper.toEntityFromDto(dto);

        assertEquals(dto.getGoal().getId(), statistics.getGoal().getId());
        assertEquals(dto.getGoalStatus(), statistics.getStatus());
    }
}