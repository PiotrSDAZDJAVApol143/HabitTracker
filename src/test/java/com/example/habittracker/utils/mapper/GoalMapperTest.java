package com.example.habittracker.utils.mapper;

import com.example.habittracker.dto.GoalDto;
import com.example.habittracker.dto.GoalReqDto;
import com.example.habittracker.model.Category;
import com.example.habittracker.model.Goal;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.example.habittracker.model.Category.HEALTH;
import static org.junit.jupiter.api.Assertions.*;

class GoalMapperTest {
    private  final GoalMapper goalMapper = new GoalMapper();

    @Test
    void toEntity() {
        //given
        String goalName = "testGoalToEntity";
        String description = "Testing method";
        Category category = HEALTH;
        LocalDate startDate = LocalDate.of(2024,4,7);
        LocalDate endDate = LocalDate.of(2024,4,15);
        GoalReqDto dto = new GoalReqDto(goalName,description,category,startDate,endDate);
        //when
        Goal goal = goalMapper.toEntity(dto);
        //then
        assertEquals(dto.getGoalName(),goal.getGoalName());
        assertEquals(dto.getDescription(),goal.getDescription());
        assertEquals(dto.getCategory(),goal.getCategory());
        assertEquals(dto.getStartDate(),goal.getStartDate());
        assertEquals(dto.getEndDate(),goal.getEndDate());

    }

    @Test
    void toDto() {
        //given
        Goal goal = new Goal();
        goal.setId(1L);
        goal.setGoalName("Test toDto in Goal Mapper");
        goal.setDescription("Testing method");
        goal.setCategory(HEALTH);
        goal.setStartDate(LocalDate.of(2024,4,5));
        goal.setEndDate(LocalDate.of(2024,4,15));
        //when
        GoalDto dto = goalMapper.toDto(goal);
        //then
        assertEquals(goal.getId(),dto.getId());
        assertEquals(goal.getGoalName(),dto.getGoalName());
        assertEquals(goal.getDescription(),dto.getDescription());
        assertEquals(goal.getStartDate(),dto.getStartDate());
        assertEquals(goal.getEndDate(),dto.getEndDate());
    }

    @Test
    void updateGoalFromDto() {
        //given
        String goalName = "testGoalToEntity";
        String description = "Testing method";
        Category category = HEALTH;
        LocalDate startDate = LocalDate.of(2024,4,7);
        LocalDate endDate = LocalDate.of(2024,4,15);
        GoalReqDto dto = new GoalReqDto(goalName,description,category,startDate,endDate);
        Goal goal = new Goal();
        //when
        goalMapper.updateGoalFromDto(dto,goal);
        //then
        assertEquals(dto.getGoalName(),goal.getGoalName());
        assertEquals(dto.getDescription(),goal.getDescription());
        assertEquals(dto.getCategory(),goal.getCategory());
        assertEquals(dto.getStartDate(),goal.getStartDate());
        assertEquals(dto.getEndDate(),goal.getEndDate());
    }
}