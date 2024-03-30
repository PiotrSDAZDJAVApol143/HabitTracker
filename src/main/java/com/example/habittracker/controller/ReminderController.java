package com.example.habittracker.controller;

import com.example.habittracker.dto.HabitReminderDto;
import com.example.habittracker.dto.ReminderReqDto;
import com.example.habittracker.model.Reminder;
import com.example.habittracker.service.ReminderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reminder")
@Validated
@RequiredArgsConstructor
public class ReminderController {
    private final ReminderService reminderService;

    @GetMapping("/showAll")
    public ResponseEntity<List<Reminder>> showAllReminders() {
        List<Reminder> reminderList = reminderService.getAllReminders();
        return new ResponseEntity<>(reminderList, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<HabitReminderDto> addReminder(@Valid @RequestBody ReminderReqDto request){
        HabitReminderDto reminderDto = reminderService.addReminder(request);
        return new ResponseEntity<>(reminderDto, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<HabitReminderDto> getReminderById(@PathVariable Long id){
        HabitReminderDto reminderDto = reminderService.getReminderById(id);
        return new ResponseEntity<>(reminderDto, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HabitReminderDto> updateReminderById(@PathVariable Long id, @Valid @RequestBody ReminderReqDto request){
        HabitReminderDto reminderDto = reminderService.updateReminderById(id, request);
        return new ResponseEntity<>(reminderDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReminderById(@PathVariable Long id){
        reminderService.deleteReminderById(id);
        return ResponseEntity.noContent().build();
    }


}
