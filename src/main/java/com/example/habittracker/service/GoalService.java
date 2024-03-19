package com.example.habittracker.service;

import com.example.habittracker.dto.GoalReqDto;
import com.example.habittracker.model.Goal;
import com.example.habittracker.repository.GoalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepository goalRepository;


    public Goal addGoal(GoalReqDto request) {
        Goal goal = new Goal();
        goal.setGoalName(request.getGoalName());
        goal.setDescription(request.getDescription());
        goal.setCategory(request.getCategory());
        goal.setStartDate(request.getStartDate());
        goal.setEndDate(request.getEndDate());
        goalRepository.save(goal);
        return goal;
    }

    public Goal findOrCreateGoal(Goal goal) {
        Optional<Goal> existingGoal = goalRepository.findById(goal.getId());
        return existingGoal.orElseGet(()-> goalRepository.save(goal));
    }
    public Goal getGoal(Long id) {
        return goalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Goal not found"));
    }

    public Goal updateGoal(Long id, GoalReqDto request){
        Goal goal = goalRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Goal not found"));
        if(request.getGoalName() !=null){
            goal.setGoalName(request.getGoalName());
        }
        if(request.getDescription() !=null){
            goal.setDescription(request.getDescription());
        }
        if(request.getCategory() !=null){
            goal.setCategory(request.getCategory());
        }
        if(request.getStartDate() !=null){
            goal.setStartDate(request.getStartDate());
        }
        if(request.getEndDate() !=null){
            goal.setEndDate(request.getEndDate());
        }
        return goalRepository.save(goal);
    }

    public void deleteGoal(Long id){
        Goal goal = getGoal(id);
        goalRepository.delete(goal);
    }
}
