package com.example.habittracker.controller;

import com.example.habittracker.dto.HabitReadDto;
import com.example.habittracker.dto.HabitReqDto;
import com.example.habittracker.model.Habit;
import com.example.habittracker.service.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habit")
@Validated
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @PostMapping("/add")
    public ResponseEntity<Habit> addHabit(@Valid @RequestBody HabitReqDto request){
        Habit habit = habitService.addHabit(request);
        return new ResponseEntity<>(habit, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitReadDto> getHabit(@PathVariable Long id){
        HabitReadDto habit = habitService.getHabit(id);
        return new ResponseEntity<>(habit, HttpStatus.OK);
    }

    @PatchMapping ("/update/{id}")
    public ResponseEntity<Habit> updateHabit(@PathVariable Long id, @Valid @RequestBody HabitReqDto request){
        Habit habit = habitService.updateHabit(id, request);
        return new ResponseEntity<>(habit, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long id){
        habitService.deleteHabit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
