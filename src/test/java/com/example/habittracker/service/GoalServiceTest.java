package com.example.habittracker.service;

import com.example.habittracker.dto.GoalDto;
import com.example.habittracker.dto.GoalReqDto;
import com.example.habittracker.model.Category;
import com.example.habittracker.model.Goal;
import com.example.habittracker.repository.GoalRepository;
import com.example.habittracker.utils.mapper.GoalMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.habittracker.model.Category.EDUCATION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GoalServiceTest {
    @InjectMocks
    private GoalService goalService;  //instancja GoalService
    @Mock
    private GoalRepository goalRepository; // atrapa repo
    @Mock
    private GoalMapper goalMapper; //atrapa mappera

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }
    private static GoalReqDto getGoalReqDto() {
        String goalName = "test";
        String description = "opis testu";
        Category category = Category.EDUCATION;
        LocalDate startDate = LocalDate.of(2024, 4, 6);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        GoalReqDto request = new GoalReqDto(goalName,description,category,startDate,endDate);
        request.setGoalName(goalName);
        request.setDescription(description);
        request.setCategory(category);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        return request;
    }
    @Test
    void addGoal() {
        //given
        GoalReqDto request = getGoalReqDto();
        Goal goal = new Goal();
        // Ustalamy zachowanie mocków
        when(goalMapper.toEntity(any(GoalReqDto.class))).thenReturn(goal);
        when(goalRepository.save(any(Goal.class))).thenReturn(goal);
        when(goalMapper.toDto(any(Goal.class))).thenReturn(new GoalDto());
        // when
        goalService.addGoal(request);
        // then
        verify(goalRepository, times(1)).save(goal);
    }



    @Test
    void getGoal() {
        //given
        Goal goal = new Goal();
        when(goalRepository.findById(any(Long.class))).thenReturn(Optional.of(goal));
        when(goalMapper.toDto(any(Goal.class))).thenReturn(new GoalDto());
        //when
        goalService.getGoal(1L);
        //then
        verify(goalRepository, times(1)).findById(1L);
    }

    @Test
    void updateGoal() {
        //given
        GoalReqDto request = getGoalReqDto();

        Goal goal = new Goal();
        when(goalRepository.findById(any(Long.class))).thenReturn(Optional.of(goal));
        when(goalRepository.save(any(Goal.class))).thenReturn(goal);
        when(goalMapper.toDto(any(Goal.class))).thenReturn(new GoalDto());
        //when
        goalService.updateGoal(1L, request);
        //then
        verify(goalRepository, times(1)).save(goal);
    }

    @Test
    void deleteGoal() {
        //given
        when(goalRepository.existsById(any(Long.class))).thenReturn(true);
        //when
        goalService.deleteGoal(1L);
        //then
        verify(goalRepository, times(1)).deleteById(1L);
    }
}