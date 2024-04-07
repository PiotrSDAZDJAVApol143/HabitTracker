package com.example.habittracker.service;

import com.example.habittracker.model.Goal;
import com.example.habittracker.model.Statistics;
import com.example.habittracker.model.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StatusService {
    public void updateStatus(Statistics statistics) {
        Goal goal = statistics.getGoal();
        if (goal == null) {
            statistics.setStatus(Status.CANCELLED);
        } else {
            LocalDate now = LocalDate.now();
            if (now.isBefore(goal.getStartDate())) {
                statistics.setStatus(Status.PLANNED);
            } else if (now.isAfter(goal.getEndDate())) {
                statistics.setStatus(Status.COMPLETED);
            } else {
                statistics.setStatus(Status.IN_PROGRESS);
            }
        }
    }
}
