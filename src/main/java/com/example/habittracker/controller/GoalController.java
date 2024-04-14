package com.example.habittracker.controller;

import com.example.habittracker.dto.GoalDto;
import com.example.habittracker.dto.GoalReqDto;
import com.example.habittracker.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goal")
@Validated
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping("/add")
    public ResponseEntity<GoalDto> addGoal(@Valid @RequestBody GoalReqDto request){
        GoalDto goal = goalService.addGoal(request);
        return new ResponseEntity<>(goal, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GoalDto> getGoal(@PathVariable Long id){
        GoalDto goal = goalService.getGoal(id);
        return new ResponseEntity<>(goal, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GoalDto> updateGoal(@PathVariable Long id, @Valid @RequestBody GoalReqDto request){
        GoalDto goal = goalService.updateGoal(id, request);
        return new ResponseEntity<>(goal, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id){
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }

}

