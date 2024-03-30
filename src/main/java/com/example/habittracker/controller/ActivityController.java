package com.example.habittracker.controller;

import com.example.habittracker.dto.ActivityDto;
import com.example.habittracker.dto.ActivityReqDto;
import com.example.habittracker.model.Activity;
import com.example.habittracker.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
@Validated
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    @PostMapping("/add")
    public ResponseEntity<ActivityDto> addActivity(@Valid @RequestBody ActivityReqDto request){
        ActivityDto activity = activityService.addActivity(request);
        return new ResponseEntity<>(activity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> getActivity(@PathVariable Long id){
        ActivityDto activity = activityService.getActivity(id);
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<ActivityDto> updateActivity (@PathVariable Long id, @Valid @RequestBody ActivityReqDto activityDto){
        ActivityDto activity = activityService.updateActivity(id,activityDto);
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id){
        activityService.deleteActivity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

