package com.example.habittracker.service;

import com.example.habittracker.dto.StatisticsDto;
import com.example.habittracker.dto.StatisticsReqDto;
import com.example.habittracker.model.Goal;
import com.example.habittracker.model.Statistics;
import com.example.habittracker.repository.GoalRepository;
import com.example.habittracker.repository.StatisticsRepository;
import com.example.habittracker.utils.mapper.StatisticsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StatisticsServiceTest {
    @InjectMocks
    private StatisticsService statisticsService;
    @Mock
    private StatisticsRepository statisticsRepository;
    @Mock
    private StatisticsMapper statisticsMapper;
    @Mock
    private GoalRepository goalRepository;
    @Mock
    private StatusService statusService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private static StatisticsReqDto getStatisticsReqDto() {
        Long goalId = 1L;

        StatisticsReqDto request = new StatisticsReqDto();
        request.setGoalId(goalId);

        return request;
    }

    @Test
    void addStatistics() {
        //given
        StatisticsReqDto request = getStatisticsReqDto();
        Goal goal = new Goal();
        Statistics statistics = new Statistics();
        StatisticsDto expected = new StatisticsDto();
        //when
        when(goalRepository.findById(anyLong())).thenReturn(Optional.of(goal));
        when(statisticsMapper.toEntity(any(StatisticsReqDto.class))).thenReturn(statistics);
        when(statisticsRepository.save(any(Statistics.class))).thenReturn(statistics);
        when(statisticsMapper.toDto(any(Statistics.class))).thenReturn(expected);
        //then
        StatisticsDto result = statisticsService.addStatistics(request);

        assertEquals(expected, result);
        verify(goalRepository).findById(request.getGoalId());
        verify(statisticsMapper).toEntity(request);
        verify(statisticsRepository).save(statistics);
        verify(statisticsMapper).toDto(statistics);
    }

    @Test
    void getStatistics() {
        //given
        Long id = 1L;
        Statistics statistics = new Statistics();
        StatisticsDto expected = new StatisticsDto();
        //when
        when(statisticsRepository.findById(anyLong())).thenReturn(Optional.of(statistics));
        when(statisticsMapper.toDto(any(Statistics.class))).thenReturn(expected);
        //then
        StatisticsDto result = statisticsService.getStatistics(id);

        assertEquals(expected, result);
        verify(statisticsRepository).findById(id);
        verify(statisticsMapper).toDto(statistics);
    }

    @Test
    void updateStatistics() {
        //given
        Long id = 1L;
        StatisticsReqDto request = getStatisticsReqDto();
        Goal goal = new Goal();
        Statistics statistics = new Statistics();
        StatisticsDto expected = new StatisticsDto();
        //when
        when(statisticsRepository.findById(anyLong())).thenReturn(Optional.of(statistics));
        when(goalRepository.findById(anyLong())).thenReturn(Optional.of(goal));
        when(statisticsRepository.save(any(Statistics.class))).thenReturn(statistics);
        when(statisticsMapper.toDto(any(Statistics.class))).thenReturn(expected);
        //then
        StatisticsDto result = statisticsService.updateStatistics(id, request);

        assertEquals(expected, result);
        verify(statisticsRepository).findById(id);
        verify(goalRepository).findById(request.getGoalId());
        verify(statisticsRepository).save(statistics);
        verify(statisticsMapper).toDto(statistics);
    }

    @Test
    void deleteStatistics() {
        //given
        Long id = 1L;
        //when
        when(statisticsRepository.existsById(anyLong())).thenReturn(true);
        //then
        statisticsService.deleteStatistics(id);

        verify(statisticsRepository).existsById(id);
        verify(statisticsRepository).deleteById(id);
    }
}