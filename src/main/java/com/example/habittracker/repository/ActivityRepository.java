package com.example.habittracker.repository;

import com.example.habittracker.model.Activity;
import com.example.habittracker.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
