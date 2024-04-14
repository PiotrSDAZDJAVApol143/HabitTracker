package com.example.habittracker.controller;

import com.example.habittracker.dto.ActivityDto;
import com.example.habittracker.dto.ActivityReqDto;
import com.example.habittracker.service.ActivityService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ActivityControllerTest {

    @InjectMocks
    private ActivityController activityController;

    @Mock
    private ActivityService activityService;

    @Test
    public void addActivityTest() {
        //given
        ActivityReqDto request = new ActivityReqDto(1L,"TestActivity", LocalTime.of(10,0), LocalDate.of(2022,1,1));
        ActivityDto activityDto = new ActivityDto();
        when(activityService.addActivity(request)).thenReturn(activityDto);
        //when
        ResponseEntity<ActivityDto> response = activityController.addActivity(request);
        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(activityDto, response.getBody());
    }

    @Test
    public void getActivityTest() {
        Long id = 1L;
        ActivityDto activityDto = new ActivityDto();
        when(activityService.getActivity(id)).thenReturn(activityDto);

        ResponseEntity<ActivityDto> response = activityController.getActivity(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(activityDto, response.getBody());
    }

    @Test
    public void updateActivityTest() {
        Long id = 1L;
        ActivityReqDto request = new ActivityReqDto(1L,"TestActivity",LocalTime.of(10,0),LocalDate.of(2022,1,1));
        ActivityDto activityDto = new ActivityDto();
        when(activityService.updateActivity(id, request)).thenReturn(activityDto);

        ResponseEntity<ActivityDto> response = activityController.updateActivity(id, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(activityDto, response.getBody());
    }

    @Test
    public void deleteActivityTest() {
        Long id = 1L;

        ResponseEntity<Void> response = activityController.deleteActivity(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}