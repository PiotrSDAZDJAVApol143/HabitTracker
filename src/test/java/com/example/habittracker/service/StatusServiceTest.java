package com.example.habittracker.service;

import com.example.habittracker.model.Goal;
import com.example.habittracker.model.Statistics;
import com.example.habittracker.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StatusServiceTest {
        @InjectMocks
        private StatusService statusService;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void updateStatusWhenGoalIsNull() {
            //given
            Statistics statistics = new Statistics();
            //when
            statusService.updateStatus(statistics);
            //then
            assertEquals(Status.CANCELLED, statistics.getStatus());
        }

        @Test
        void updateStatusWhenGoalStartDateIsInFuture() {
            //given
            Statistics statistics = new Statistics();
            Goal goal = new Goal();
            goal.setStartDate(LocalDate.now().plusDays(1)); // Set start date to future
            statistics.setGoal(goal);
            //when
            statusService.updateStatus(statistics);
            //then
            assertEquals(Status.PLANNED, statistics.getStatus());
        }

        @Test
        void updateStatusWhenGoalEndDateIsInPast() {
            //given
            Statistics statistics = new Statistics();
            Goal goal = new Goal();
            goal.setStartDate(LocalDate.now().minusDays(2));
            goal.setEndDate(LocalDate.now().minusDays(1)); // Set end date to past
            statistics.setGoal(goal);
            //when
            statusService.updateStatus(statistics);
            //then
            assertEquals(Status.COMPLETED, statistics.getStatus());
        }

        @Test
        void updateStatusWhenGoalIsInProgress() {
            //given
            Statistics statistics = new Statistics();
            Goal goal = new Goal();
            goal.setStartDate(LocalDate.now().minusDays(1)); // Set start date to past
            goal.setEndDate(LocalDate.now().plusDays(1)); // Set end date to future
            statistics.setGoal(goal);
            //when
            statusService.updateStatus(statistics);
            //then
            assertEquals(Status.IN_PROGRESS, statistics.getStatus());
        }
    }