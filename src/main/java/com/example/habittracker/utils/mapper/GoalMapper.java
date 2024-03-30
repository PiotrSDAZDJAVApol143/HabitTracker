package com.example.habittracker.utils.mapper;

import com.example.habittracker.dto.GoalDto;
import com.example.habittracker.dto.GoalReqDto;
import com.example.habittracker.model.Goal;
import org.springframework.stereotype.Component;


@Component
public class GoalMapper {

    public Goal toEntity(GoalReqDto dto) {
        Goal goal = new Goal();
        goal.setId(goal.getId());
        goal.setGoalName(dto.getGoalName());
        goal.setDescription(dto.getDescription());
        goal.setCategory(dto.getCategory());
        goal.setStartDate(dto.getStartDate());
        goal.setEndDate(dto.getEndDate());
        return goal;
    }

    public GoalDto toDto(Goal goal) {
        GoalDto dto = new GoalDto();
        dto.setId(goal.getId());
        dto.setGoalName(goal.getGoalName());
        dto.setDescription(goal.getDescription());
        dto.setCategory(goal.getCategory());
        dto.setStartDate(goal.getStartDate());
        dto.setEndDate(goal.getEndDate());
        return dto;
    }
    public void updateGoalFromDto(GoalReqDto reqDto, Goal goal){
        goal.setGoalName(reqDto.getGoalName());
        goal.setDescription(reqDto.getDescription());
        goal.setCategory(reqDto.getCategory());
        goal.setStartDate(reqDto.getStartDate());
        goal.setEndDate(reqDto.getEndDate());
    }

}
