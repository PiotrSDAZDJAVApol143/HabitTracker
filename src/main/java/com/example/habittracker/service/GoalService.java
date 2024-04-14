package com.example.habittracker.service;

import com.example.habittracker.dto.GoalDto;
import com.example.habittracker.dto.GoalReqDto;
import com.example.habittracker.model.Goal;
import com.example.habittracker.repository.GoalRepository;
import com.example.habittracker.utils.mapper.GoalMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GoalService {
        private final GoalRepository goalRepository;
        private final GoalMapper goalMapper;


    public GoalDto addGoal(GoalReqDto request) {
        Goal goal = goalMapper.toEntity(request);
        goal = goalRepository.save(goal);
        return goalMapper.toDto(goal);
    }

    public GoalDto getGoal(Long id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found"));
        return goalMapper.toDto(goal);
    }

  public GoalDto updateGoal(Long id, GoalReqDto request) {
      Goal goal = goalRepository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException("Goal not found"));
      goalMapper.updateGoalFromDto(request, goal);
      goal = goalRepository.save(goal);
      return goalMapper.toDto(goal);
  }

    public void deleteGoal(Long id) {
        if(!goalRepository.existsById(id)){
            throw new EntityNotFoundException("Goal not found");
        }
        goalRepository.deleteById(id);
    }
}

// public Goal updateGoal(Long id, GoalReqDto request){
//     Goal goal = goalRepository.findById(id)
//             .orElseThrow(()-> new EntityNotFoundException("Goal not found"));
//     if(request.getGoalName() !=null){
//         goal.setGoalName(request.getGoalName());
//     }
//     if(request.getDescription() !=null){
//         goal.setDescription(request.getDescription());
//     }
//     if(request.getCategory() !=null){
//         goal.setCategory(request.getCategory());
//     }
//     if(request.getStartDate() !=null){
//         goal.setStartDate(request.getStartDate());
//     }
//     if(request.getEndDate() !=null){
//         goal.setEndDate(request.getEndDate());
//     }
//     return goalRepository.save(goal);
// }