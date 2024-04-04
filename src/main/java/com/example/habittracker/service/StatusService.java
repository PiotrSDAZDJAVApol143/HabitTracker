package com.example.habittracker.service;

import com.example.habittracker.model.Statistics;
import com.example.habittracker.model.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StatusService {
    public void updateStatus(Statistics statistics) {
        if (statistics.getGoal() == null) {
            statistics.setStatus(Status.CANCELLED);
        } else if (LocalDate.now().isBefore(statistics.getGoal().getStartDate())) {
            statistics.setStatus(Status.PLANNED);
        } else if (LocalDate.now().isAfter(statistics.getGoal().getEndDate())) {
            statistics.setStatus(Status.COMPLETED);
        } else {
            statistics.setStatus(Status.IN_PROGRESS);
        }
    }
}
