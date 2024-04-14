package com.example.habittracker.controller;

import com.example.habittracker.dto.HabitDto;
import com.example.habittracker.dto.HabitReqDto;
import com.example.habittracker.dto.HabitReminderDto;
import com.example.habittracker.service.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit")
@Validated
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @PostMapping("/add")
    public ResponseEntity<HabitDto> addHabit(@Valid @RequestBody HabitReqDto request){
        HabitDto habit = habitService.addHabit(request);
        return new ResponseEntity<>(habit, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitDto> getHabit(@PathVariable Long id){
        HabitDto habit = habitService.getHabit(id);
        return new ResponseEntity<>(habit, HttpStatus.OK);
    }

    @PatchMapping ("/update/{id}")
    public ResponseEntity<HabitDto> updateHabit(@PathVariable Long id, @Valid @RequestBody HabitReqDto request){
        HabitDto habit = habitService.updateHabit(id, request);
        return new ResponseEntity<>(habit, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long id){
        habitService.deleteHabit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/allReminders")
    public ResponseEntity<List<HabitReminderDto>> getAllRemindersByHabitId(@PathVariable Long id) {
        HabitDto habit = habitService.getHabit(id);
        return new ResponseEntity<>(habit.getHabitReminders(), HttpStatus.OK);
    }
}
